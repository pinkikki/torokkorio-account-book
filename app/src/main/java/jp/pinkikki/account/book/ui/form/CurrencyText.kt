package jp.pinkikki.account.book.ui.form

import android.content.Context
import androidx.appcompat.widget.AppCompatEditText
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet

class CurrencyText : AppCompatEditText {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private var prevText = text.toString()

    private val watcher: TextWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            prevText = s.toString()
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            val text = s?.toString() ?: return
            if (prevText == text) return
            val number = text.replace(",", "").toLongOrNull() ?: return
            val textWithComma = number.toStringWithSeparator()
            if (textWithComma == text) return

            val countRightFromCursor = text.length - selectionEnd
            setTextKeepState(textWithComma)
            setSelection((textWithComma.length - countRightFromCursor).coerceAtLeast(0))
        }
    }

    init {
        addTextChangedListener(watcher)
    }
}

fun Number.toStringWithSeparator() = "%,d".format(this)