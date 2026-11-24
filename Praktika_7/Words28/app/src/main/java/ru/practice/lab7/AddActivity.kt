package ru.practice.lab7

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import ru.practice.lab7.databinding.ActivityAddBinding

class AddActivity : AppCompatActivity() {
    private val model: WordsModel by viewModels()

    override fun onCreate(state: Bundle?) {
        super.onCreate(state)
        val binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val set = intent.getIntExtra("set", 0)
        with(binding) {
            fun validate(): Boolean {
                return listOf(word, translation).map {
                    val valid = !it.text.isNullOrBlank()
                    it.error = if (valid) null else getString(R.string.empty)
                    valid
                }.all { it }
            }
            word.doAfterTextChanged { if (word.hasFocus()) word.error = if (it.isNullOrBlank()) getString(R.string.empty) else null }
            translation.doAfterTextChanged { save.isEnabled = !it.isNullOrBlank() }
            save.setOnClickListener {
                if (validate()) {
                    save.isEnabled = false
                    model.add(listOf(Card(set, word.text.toString().trim(), translation.text.toString().trim())))
                }
            }
        }
        model.result.observe(this) {
            if (it < 0) {
                Toast.makeText(this, R.string.failed, Toast.LENGTH_SHORT).show()
                binding.save.isEnabled = true
            } else finish()
        }
    }
}
