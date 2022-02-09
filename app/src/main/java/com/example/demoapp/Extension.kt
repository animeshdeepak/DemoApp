package com.example.demoapp

import android.app.Activity
import android.content.Context
import android.os.IBinder
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import java.util.regex.Matcher
import java.util.regex.Pattern

fun Context.toast(message: String?) {
    Toast.makeText(this, message ?: "no message to show :(", Toast.LENGTH_SHORT).show()
}

fun String.validatePanCard(): Boolean {
    // Regex to check valid PAN Card number.
    val regex = "[A-Z]{5}[0-9]{4}[A-Z]"
    // Compile the ReGex
    val p: Pattern = Pattern.compile(regex)
    val m: Matcher = p.matcher(this)

    return m.matches()
}

fun String.validateDoB(): Boolean {
    val regex = "^(?:31([/\\-.])(?:0?[13578]|1[02])\\1|(?:29|30)([/\\-.])(?:0?[13-9]|1[0-2])\\2)(?:1[6-9]|[2-9]\\d)?\\d{2}\$|^29([/\\-.])0?2\\3(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:16|[2468][048]|[3579][26])00)\$|^(?:0?[1-9]|1\\d|2[0-8])([/\\-.])(?:0?[1-9]|1[0-2])\\4(?:1[6-9]|[2-9]\\d)?\\d{2}\$"
    return Pattern.compile(regex).matcher(this).matches()
}

fun TextView.makeLink(pair: Pair<String, ClickableSpan>) {
    val spannableString = SpannableString(text)
    val start = text.toString().indexOf(pair.first)
    if (start < 0) throw IllegalArgumentException("Link '${pair.first}' not found")

    spannableString.setSpan(
        pair.second, start, start + pair.first.length,
        Spanned.SPAN_INCLUSIVE_EXCLUSIVE
    )
    movementMethod = LinkMovementMethod.getInstance()
    setText(spannableString, TextView.BufferType.SPANNABLE)
}

fun makeClickableSpanWithoutUnderline(
    color: Int,
    action: () -> Unit
): ClickableSpan {
    return object : ClickableSpan() {
        override fun onClick(view: View) {
            action.invoke()
        }

        override fun updateDrawState(ds: TextPaint) {
            super.updateDrawState(ds)
            ds.color = color
            ds.isUnderlineText = false
        }
    }
}

/**
 * @param windowToken `is` view.windowToken
 */
fun Context.hideKeyboard(windowToken: IBinder?) {
    if (windowToken != null)
        (getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager)
            .hideSoftInputFromWindow(windowToken, 0)
}