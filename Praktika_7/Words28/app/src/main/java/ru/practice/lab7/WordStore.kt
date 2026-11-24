package ru.practice.lab7

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import org.json.JSONArray
import org.json.JSONObject

data class Card(val set: Int, val word: String, val translation: String, val learned: Boolean = false)

class WordStore(app: Application) {
    private val prefs = app.getSharedPreferences("words", 0)
    companion object { private val lock = Mutex() }

    private fun read(): List<Card> {
        val array = JSONArray(prefs.getString("cards", "[]"))
        return List(array.length()) { i ->
            val row = array.getJSONObject(i)
            Card(row.getInt("set"), row.getString("word"), row.getString("translation"), row.getBoolean("learned"))
        }
    }

    suspend fun cards() = withContext(Dispatchers.IO) { lock.withLock { read() } }

    suspend fun change(transform: (List<Card>) -> List<Card>) = withContext(Dispatchers.IO) {
        lock.withLock {
            val array = JSONArray()
            transform(read()).forEach { card ->
                array.put(JSONObject().put("set", card.set).put("word", card.word)
                    .put("translation", card.translation).put("learned", card.learned))
            }
            check(prefs.edit().putString("cards", array.toString()).commit())
        }
    }
}

class WordsModel(app: Application) : AndroidViewModel(app) {
    private val store = WordStore(app)
    val card = MutableLiveData<Card?>()
    val result = MutableLiveData<Int>()
    private var queue = emptyList<Card>()
    private var initialized = false

    fun add(cards: List<Card>) = viewModelScope.launch {
        runCatching { store.change { old -> old + cards } }
            .onSuccess { result.value = cards.size }.onFailure { result.value = -1 }
    }

    fun start(set: Int) = viewModelScope.launch {
        if (initialized) return@launch
        initialized = true
        runCatching { store.cards().filter { it.set == set && !it.learned }.shuffled() }
            .onSuccess { queue = it; next() }.onFailure { result.value = -1 }
    }

    fun next() {
        card.value = queue.firstOrNull()
        queue = queue.drop(1)
    }

    fun learned() = viewModelScope.launch {
        val current = card.value ?: return@launch
        runCatching { store.change { rows -> rows.map { if (it == current) it.copy(learned = true) else it } } }
            .onSuccess { next() }.onFailure { result.value = -1 }
    }
}
