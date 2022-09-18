package com.example.banklist.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.banklist.R
import com.example.banklist.databinding.FragmentBankListBinding
import com.example.banklist.models.BankModel
import com.example.banklist.view.adapter.BankListAdapter
import com.example.banklist.view.adapter.BankListClickListener
import com.example.banklist.viewModel.BankListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BankListFragment : Fragment(R.layout.fragment_bank_list), BankListClickListener {

    private var _binding : FragmentBankListBinding? = null
    private val binding get() = _binding!!

    private val viewModel : BankListViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentBankListBinding.bind(view)
        viewModel.loadData()
        viewModel.observeConnection()

        binding.customSearchBar.setOnEditorActionListener { _, actionId, _ ->
            when(actionId){
                EditorInfo.IME_ACTION_SEARCH -> {
                    if(binding.customSearchBar.getSearchedText().length >= 2){
                        viewModel.searchItems(binding.customSearchBar.getSearchedText())
                    } else {
                        viewModel.searchItems("")
                    }
                    true
                }
                else -> false
            }
        }

        viewModel.shownBankList.observe(viewLifecycleOwner){ bankList ->
            Log.d("Fatih","bank list size : "+bankList.size)

            binding.bankListRecyclerView.also { recyclerView ->
                recyclerView.layoutManager = LinearLayoutManager(requireContext())
                recyclerView.adapter = BankListAdapter(bankList, this)
            }
        }

        viewModel.errorState.observe(viewLifecycleOwner) { errorExist ->
            if(errorExist){
                binding.bankListRecyclerView.visibility = View.GONE
                binding.errorText.visibility = View.VISIBLE
            } else {
                binding.bankListRecyclerView.visibility = View.VISIBLE
                binding.errorText.visibility = View.GONE
            }
        }

        viewModel.errorText.observe(viewLifecycleOwner){
            binding.errorText.text = it
        }
    }

    override fun onRecyclerViewItemClick(bankModel: BankModel) {
        val action = BankListFragmentDirections.actionBankListFragmentToBankDetailFragment(bankModel)
        findNavController().navigate(action)
    }



}