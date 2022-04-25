package ru.netology.nmedia.util

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.view.inputmethod.InputMethodManager.SHOW_IMPLICIT
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.post_card_layout.view.*

internal fun View.hideKeyboard() {
    val imm = context.getSystemService(
        Context.INPUT_METHOD_SERVICE
    ) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, /*flags =*/0)
}
internal fun View.showKeyboard() {
    val imm = context.getSystemService(
        Context.INPUT_METHOD_SERVICE
    ) as InputMethodManager
    imm.showSoftInput(contentEditText, 0)
}