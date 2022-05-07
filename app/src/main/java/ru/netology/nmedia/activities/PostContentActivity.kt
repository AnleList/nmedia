package ru.netology.nmedia.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContract
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.databinding.PostContentActivityBinding
import ru.netology.nmedia.util.showKeyboard

class PostContentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = PostContentActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val textToEdit: String? =
            intent.getStringExtra("ru.netology.nmedia.PostContentActivity.TEXT_TO_EDIT")
        with(binding.edit) {
            setText(textToEdit)
            requestFocus()
            setSelection(binding.edit.text.length)
            showKeyboard()
        }

        binding.ok.setOnClickListener {
            val answerIntent = Intent()
            val textToSave = binding.edit.text
            if (textToSave.isNullOrBlank()) {
                setResult(RESULT_CANCELED, answerIntent)
            } else {
                val content = textToSave.toString()
                answerIntent.putExtra(
                    RESULT_KEY,
                    content
                )
                setResult(RESULT_OK, answerIntent)
            }
            finish()
        }
    }

    object ResultContract : ActivityResultContract<String?, String?>() {

        override fun createIntent(context: Context, input: String?): Intent =
            Intent(context, PostContentActivity::class.java).putExtra(
                "ru.netology.nmedia.PostContentActivity.TEXT_TO_EDIT",
            input)

        override fun parseResult(resultCode: Int, intent: Intent?): String? =
            if (resultCode == Activity.RESULT_OK) {
                intent?.getStringExtra(RESULT_KEY)
            } else null
    }
    private companion object {
        private const val RESULT_KEY = "postNewContent"
    }
}