package com.example.banklist.widgets

import android.content.Context
import android.content.res.TypedArray
import android.media.Image
import android.util.AttributeSet
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.AttrRes
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.banklist.R

class CustomSearchBar : ConstraintLayout {

    private lateinit var  searchBarEditText: EditText
    private lateinit var  headerImage : ImageView

    private fun setHint(value: String?) {
        searchBarEditText.hint = value
    }

    private fun setHeaderImage(headerImg: Int) {
        headerImage.setImageResource(headerImg)
    }

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        inflate(context, R.layout.search_bar_item, this)
        init(context, attrs)
    }

    constructor(
        context: Context, attrs: AttributeSet?,
        @AttrRes defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr)


    private fun init(context: Context, attrs: AttributeSet?) {
        headerImage = findViewById(R.id.header_image)
        searchBarEditText = findViewById(R.id.search_bar_edit_text)

        val typedArray: TypedArray =
            context.obtainStyledAttributes(attrs, R.styleable.CustomSearchBar, 0, 0)
        setHint(typedArray.getString(R.styleable.CustomSearchBar_hint))
        setHeaderImage(typedArray.getResourceId(R.styleable.CustomSearchBar_header_image, 0))
    }

    fun setOnEditorActionListener(setOnEditorActionListener: TextView.OnEditorActionListener) {
        searchBarEditText.setOnEditorActionListener(setOnEditorActionListener)
    }

    fun getSearchedText(): String {
        return searchBarEditText.text.toString()
    }
}