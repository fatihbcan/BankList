package com.example.banklist.view.adapter

import com.example.banklist.models.BankModel

interface BankListClickListener {
    fun onRecyclerViewItemClick(bank : BankModel)
}