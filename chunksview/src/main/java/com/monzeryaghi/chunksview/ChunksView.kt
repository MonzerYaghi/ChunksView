package com.monzeryaghi.chunksview

import android.content.Context
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText


/**
 * Created by Monzer Yaghi on 23-Jun-21.
 */
class ChunksView : AppCompatEditText {

    //Supported separators
    private val SEPARATOR_SPACE = " "
    private val SEPARATOR_DASH = "-"
    private val SEPARATOR_SLASH = "/"
    private val SEPARATOR_DOT = "."

    //Default values
    private val DEFAULT_CHUNK_LENGTH = 4
    private val DEFAULT_SEPARATOR_INDEX = 0

    private var currentInput = ""
    private var disableFormatting = false
    private val chunksArrayList: ArrayList<String> = ArrayList()

    constructor(context: Context) : super(context) {
        initView(context, null)
    }

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        initView(context, attributeSet)
    }

    private fun initView(context: Context, attributeSet: AttributeSet?) {

        if (attributeSet != null) {
            val typedArray =
                context.obtainStyledAttributes(attributeSet, R.styleable.ChunksView)

            val chunksSeparator = typedArray.getInt(
                R.styleable.ChunksView_chunkSeparator,
                DEFAULT_SEPARATOR_INDEX
            )

            var chunkLength = typedArray.getInt(
                R.styleable.ChunksView_chunkLength,
                DEFAULT_CHUNK_LENGTH
            )
            if (chunkLength == 0) chunkLength = DEFAULT_CHUNK_LENGTH

            drawView(chunkLength, chunksSeparator)

            typedArray.recycle()
        }
    }

    private fun drawView(chunkLength: Int,
                         chunksSeparatorIndex: Int) {

        //Getting the separator
        val separator = getSeparator(chunksSeparatorIndex)

        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {

                //Removing all separators from string
                currentInput = removeSeparator(separator)

                if (disableFormatting) {
                    disableFormatting = false
                } else {
                    formatInput(chunkLength, separator)
                }

            }

        })
    }

    private fun formatInput(chunkLength: Int,
                            separator: String) {
        if (currentInput.length > chunkLength) {
            var startIndex = 0
            var endIndex = chunkLength
            chunksArrayList.clear()
            var textToSet = ""

            //Dividing input into chunks and adding them to arrayList
            while (endIndex < currentInput.length) {
                chunksArrayList.add(
                    currentInput.substring(
                        startIndex,
                        endIndex
                    )
                )
                startIndex += chunkLength
                endIndex += chunkLength
            }
            chunksArrayList.add(
                currentInput.substring(
                    startIndex,
                    currentInput.length
                )
            )


            //Fetching chunks and adding the separator between them
            for (chunk: String in chunksArrayList) {
                if (chunk.isNotEmpty()) {
                    textToSet = textToSet + separator + chunk
                }
            }
            textToSet = textToSet.substring(1)

            disableFormatting = true
            setText(textToSet)
            moveSelectionToEnd()
        }
    }

    //Getting the text without the separator
    fun getContentWithoutSeparators(): String {
        return currentInput
    }

    //Getting the separator based on the index
    private fun getSeparator(chunksSeparator: Int): String {
        return when (chunksSeparator) {
            0 -> {
                SEPARATOR_SPACE
            }
            1 -> {
                SEPARATOR_DASH
            }
            2 -> {
                SEPARATOR_SLASH
            }
            else -> {
                SEPARATOR_DOT
            }
        }
    }

    //Removing the separator from the text string
    private fun removeSeparator(separator: String): String {
        return when {
            separator.equals(SEPARATOR_SPACE, true) -> {
                text.toString().replace("\\s+".toRegex(), "")
            }
            separator.equals(SEPARATOR_SLASH, true) -> {
                text.toString().replace(SEPARATOR_SLASH, "")
            }
            separator.equals(SEPARATOR_DOT, true) -> {
                text.toString().replace("\\.".toRegex(), "")
            }
            else -> {
                text.toString().replace(SEPARATOR_DASH, "")
            }
        }
    }


    private fun setMaxLength(maxLength: Int) {
        if (maxLength > 0) {
            filters = arrayOf<InputFilter>(InputFilter.LengthFilter(maxLength))
        }
    }

    private fun moveSelectionToEnd() {
        if (!text.isNullOrEmpty()) {
            setSelection(text.toString().length)
        }
    }

}

