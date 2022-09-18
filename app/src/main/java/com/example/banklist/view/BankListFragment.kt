package com.example.banklist.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.banklist.R
import com.example.banklist.viewModel.BankListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BankListFragment : Fragment(R.layout.fragment_bank_list) {

    private val viewModel : BankListViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.deneme()
    }
}