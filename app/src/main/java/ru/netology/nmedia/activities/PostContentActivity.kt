package ru.netology.nmedia.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Selection.setSelection
import android.view.WindowManager
import androidx.activity.result.contract.ActivityResultContract
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.databinding.PostContentActivityBinding
import ru.netology.nmedia.util.showKeyboard

class PostContentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = PostContentActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val text: String? =
            intent.getSerializableExtra("TEXT") as String?
        with(binding.edit) {
            setText(text)
            requestFocus()
            setSelection(binding.edit.text.length)
//            window.setSoftInputMode(
//                        WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE
//            )
            showKeyboard()
        }
//
//        binding.ok.setOnClickListener {
//            val intent = Intent()
//            val text = binding.edit.text
//            if (text.isNullOrBlank()) {
//                setResult(Activity.RESULT_CANCELED, intent)
//            } else {
//                val content = text.toString()
//                intent.putExtra(RESULT_KEY, content)
//                setResult(Activity.RESULT_OK, intent)
//            }
//            finish()
//        }
        binding.ok.setOnClickListener {
            val answerIntent = Intent()
            val text = binding.edit.text
            if (text.isNullOrBlank()) {
                setResult(RESULT_CANCELED, answerIntent)
            } else {
                val content = text.toString()
                answerIntent.putExtra(
                    "ru.netology.nmedia.PostContentActivity.THIEF",
                    content
                )
                setResult(RESULT_OK, answerIntent)
            }
            finish()
        }
    }

    object ResultContract : ActivityResultContract<Unit, String?>() {

        override fun createIntent(context: Context, input: Unit): Intent =
            Intent(context, PostContentActivity::class.java)

        override fun parseResult(resultCode: Int, intent: Intent?): String? =
            if (resultCode == Activity.RESULT_OK) {
                intent?.getStringExtra(RESULT_KEY)
            } else null
    }
    private companion object {
        private const val RESULT_KEY = "postNewContent"
    }
}