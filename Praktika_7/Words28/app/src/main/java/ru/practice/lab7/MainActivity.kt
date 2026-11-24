package ru.practice.lab7

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.*
import ru.practice.lab7.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val model: WordsModel by viewModels()
    private lateinit var binding: ActivityMainBinding
    private var importSet = 0
    private val picker = registerForActivityResult(ActivityResultContracts.OpenDocument()) { uri ->
        if (uri != null) lifecycleScope.launch {
            runCatching {
                withContext(Dispatchers.IO) {
                    requireNotNull(contentResolver.openInputStream(uri)).bufferedReader().useLines { lines ->
                        lines.filter { it.isNotBlank() }.map { line ->
                            val pair = line.split("=", limit = 2).map(String::trim)
                            require(pair.size == 2 && pair.all { it.isNotBlank() })
                            Card(importSet, pair[0], pair[1])
                        }.toList().also { require(it.isNotEmpty()) }
                    }
                }
            }.onSuccess { model.add(it) }.onFailure {
                Toast.makeText(this@MainActivity, if (it is IllegalArgumentException) R.string.format else R.string.failed, Toast.LENGTH_LONG).show()
            }
        }
    }
    override fun onCreate(state: Bundle?) {
        super.onCreate(state)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        importSet = state?.getInt("importSet") ?: 0
        binding.sets.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item,
            listOf(getString(R.string.general), getString(R.string.travel)))
        binding.add.setOnClickListener { open(AddActivity::class.java) }
        binding.train.setOnClickListener { open(TrainActivity::class.java) }
        binding.importWords.setOnClickListener {
            importSet = binding.sets.selectedItemPosition
            try { picker.launch(arrayOf("text/*")) }
            catch (e: android.content.ActivityNotFoundException) { Toast.makeText(this, R.string.unavailable, Toast.LENGTH_SHORT).show() }
        }
        model.result.observe(this) {
            Toast.makeText(this, if (it < 0) getString(R.string.failed) else getString(R.string.imported, it), Toast.LENGTH_SHORT).show()
        }
    }
    private fun open(type: Class<*>) = startActivity(Intent(this, type).putExtra("set", binding.sets.selectedItemPosition))
    override fun onSaveInstanceState(out: Bundle) { super.onSaveInstanceState(out); out.putInt("importSet", importSet) }
}
