package com.example.banklist.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.banklist.R
import com.example.banklist.databinding.RecyclerListItemBinding
import com.example.banklist.models.BankModel

class BankListAdapter (private val bankList: List<BankModel>,
                       private val clickListener: BankListClickListener) :
    RecyclerView.Adapter<BankListAdapter.BankListViewHolder>() {

    inner class BankListViewHolder(private val bankListItemBinding: RecyclerListItemBinding,
                                   private val clickListener: BankListClickListener) :
        RecyclerView.ViewHolder(bankListItemBinding.root) {

        fun bind(episode: BankModel){
            bankListItemBinding.item = episode;
            bankListItemBinding.clickListener = clickListener
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = BankListViewHolder(
        DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.recycler_list_item,
            parent,
            false
        ), clickListener
    )

    override fun onBindViewHolder(holder: BankListViewHolder, position: Int) {
        holder.bind(bankList[position])
    }

    override fun getItemCount(): Int = bankList.size
}