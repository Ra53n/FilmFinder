package com.example.filmfinder.presentation.view

import android.view.View
import com.google.android.material.snackbar.Snackbar

fun View.snackBarWithoutAction(text: String, length: Int) {
    Snackbar.make(this, text, length).show()
}

fun View.snackBarWithoutAction(stringId: Int, length: Int) {
    Snackbar.make(this, stringId, length).show()
}

fun View.snackBarWithAction(
    text: String,
    length: Int,
    actionText: String,
    action: () -> Unit
) {
    Snackbar.make(this, text, length).setAction(actionText) { action.invoke() }.show()
}

fun View.snackBarWithAction(
    stringId: Int,
    length: Int,
    actionText: String,
    action: () -> Unit
) {
    Snackbar.make(this, stringId, length).setAction(actionText) { action.invoke() }.show()
}