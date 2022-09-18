package com.example.banklist.widgets

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.widget.TextView
import androidx.annotation.AttrRes
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.banklist.R

class CustomSearchBar: ConstraintLayout {

    fun setHint(value : String?){
      //  this.search_bar_edit_text.hint = value
    }

    fun setHeaderImage(headerImage : Int){
       // this.header_image.setImageResource(headerImage)
    }

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs){
        inflate(context, R.layout.search_bar_item, this)
        init(context,attrs)
    }
    constructor(context: Context, attrs: AttributeSet?,
                @AttrRes defStyleAttr: Int) : super(context, attrs, defStyleAttr)


    private fun init(context: Context, attrs: AttributeSet?) {
       val typedArray : TypedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomSearchBar,0 ,0)
        setHint(typedArray.getString(R.styleable.CustomSearchBar_hint))
        setHeaderImage(typedArray.getResourceId(R.styleable.CustomSearchBar_header_image,0))
    }

    fun setOnEditorActionListener(setOnEditorActionListener: TextView.OnEditorActionListener){
        //this.search_bar_edit_text.setOnEditorActionListener(setOnEditorActionListener)
    }

    fun getSearchedText(): String{
        return ""
       // return this.search_bar_edit_text.text.toString()
    }
}