package com.example.demoapp

import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.View
import android.widget.EditText

class GenericKeyEvent internal constructor(
    private val currentView: EditText,
    private val previousView: EditText?
) : View.OnKeyListener {
    override fun onKey(p0: View?, keyCode: Int, event: KeyEvent?): Boolean {
        if (event?.action == KeyEvent.ACTION_DOWN &&
            keyCode == KeyEvent.KEYCODE_DEL &&
            currentView.id != R.id.date_et &&
            currentView.text.isEmpty()
        ) {
            previousView?.requestFocus()
            return true
        }
        return false
    }
}

class GenericTextWatcher internal constructor(
    private val currentView: View,
    private val nextView: View?
) :
    TextWatcher {
    override fun afterTextChanged(editable: Editable) {
        val text = editable.toString()
        when (currentView.id) {
            R.id.date_et -> if (text.length == 2 && (text.toInt() in 1..31)) nextView?.requestFocus()
            R.id.month_et -> if (text.length == 2 && (text.toInt() in 1..12)) nextView?.requestFocus()
            R.id.year_et -> if (text.length == 4 && (text.toInt() in 1900..2022)) {
                currentView.context?.hideKeyboard(currentView.windowToken)
            }
        }
    }

    override fun beforeTextChanged(
        arg0: CharSequence,
        arg1: Int,
        arg2: Int,
        arg3: Int
    ) = Unit

    override fun onTextChanged(
        arg0: CharSequence,
        arg1: Int,
        arg2: Int,
        arg3: Int
    ) = Unit
}