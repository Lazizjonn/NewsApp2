package uz.gita.mynewsapp.utils

import android.widget.EditText
import androidx.core.widget.addTextChangedListener


fun EditText.values() : String {
    return this.text.toString()
}

fun EditText.addListener(block : (String) -> Unit) {
    this.addTextChangedListener {
        it?.let {
            block.invoke(it.toString())
        }
    }
}