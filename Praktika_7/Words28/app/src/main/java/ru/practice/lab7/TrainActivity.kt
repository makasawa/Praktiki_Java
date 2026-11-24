package ru.practice.lab7

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import ru.practice.lab7.databinding.ActivityTrainBinding

class TrainActivity : AppCompatActivity() {
    private val model: WordsModel by viewModels()

    override fun onCreate(state: Bundle?) {
        super.onCreate(state)
        val binding = ActivityTrainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val set = intent.getIntExtra("set", 0)
        with(binding) {
            model.card.observe(this@TrainActivity) { current ->
                card.text = current?.word ?: getString(R.string.no_cards)
                if (state == null || translation.tag != null) translation.text?.clear()
                translation.tag = true
                feedback.text = ""
                learned.isEnabled = current != null
                next.isEnabled = current != null
            }
            translation.doAfterTextChanged {
                val current = model.card.value
                val matches = it.toString().trim().equals(current?.translation, ignoreCase = true)
                feedback.text = if (it.isNullOrBlank() || current == null) "" else getString(if (matches) R.string.correct else R.string.wrong)
                feedback.setTextColor(getColor(if (matches) R.color.match else R.color.miss))
            }
            next.setOnClickListener { model.next() }
            learned.setOnClickListener { model.learned() }
            model.start(set)
        }
        model.result.observe(this) {
            if (it < 0) {
                Toast.makeText(this, R.string.failed, Toast.LENGTH_SHORT).show()
                
            } 
        }
    }
}
