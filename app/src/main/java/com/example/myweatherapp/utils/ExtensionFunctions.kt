package com.example.myweatherapp.utils

import android.app.Dialog
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.example.myweatherapp.R


private var dialog: Dialog? = null


fun Context.showProgressExtension() {
    try {
        if (dialog != null && dialog!!.isShowing) {
            dialog!!.dismiss()
            dialog = null
        }
        dialog = Dialog(this)
        if (!dialog!!.isShowing) {
            dialog!!.setContentView(R.layout.layout_progess_bar)
            dialog!!.window!!.setBackgroundDrawableResource(android.R.color.transparent)
            dialog!!.setCancelable(false)
            dialog!!.show()
        }
    } catch (_: Exception) {
    }
}

fun hideProgress() {
    if (dialog != null && dialog!!.isShowing) {
        dialog!!.dismiss()
        dialog = null
    }
}

fun Context.showToastMessage(message : String){
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

fun Context.hideKeyboard(view: View) {
    val imm: InputMethodManager? = getSystemService(Context.INPUT_METHOD_SERVICE)
            as InputMethodManager?
    imm?.hideSoftInputFromWindow(view.windowToken, 0)
}