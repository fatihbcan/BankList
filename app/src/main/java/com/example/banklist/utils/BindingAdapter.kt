package com.example.banklist.utils

import android.annotation.SuppressLint
import android.widget.TextView
import androidx.databinding.BindingAdapter

@SuppressLint("SetTextI18n")
@BindingAdapter("setBranchText")
fun setBranchText(view: TextView, branchName: String) {
    if (branchName.isNullOrEmpty()) {
        view.text = "Bilinmeyen Şube"
    } else {
        view.text = branchName
    }
}