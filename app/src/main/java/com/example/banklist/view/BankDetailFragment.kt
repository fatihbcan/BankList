package com.example.banklist.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.example.banklist.R
import com.example.banklist.databinding.FragmentBankDetailBinding
import com.example.banklist.databinding.FragmentBankListBinding


class BankDetailFragment : Fragment(R.layout.fragment_bank_detail) {

    private var _binding : FragmentBankDetailBinding? = null
    private val binding get() = _binding!!
    private val args : BankDetailFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentBankDetailBinding.bind(view)

        binding.bankName.text = if(args.bankItem.bankBranch.isEmpty()){
            "Bilinmeyen Şube"
        } else {
            args.bankItem.bankBranch
        }
        binding.address.text = "Address : " + args.bankItem.address
        binding.bankType.text = "Bank Type : " + args.bankItem.bankType
        binding.city.text = "City : " + args.bankItem.city
        binding.district.text = "District : " + args.bankItem.district
        binding.onOffLine.text = "On-Off Line : " + args.bankItem.on_off_line
        binding.onOffSite.text = "On-Off Site : " + args.bankItem.on_off_site
        binding.postCode.text = "Post Code : " + args.bankItem.postCode
        binding.regionalCoordinator.text = "Regional Coordinator : " + args.bankItem.regionalCoordinator

        binding.directionButton.setOnClickListener(View.OnClickListener {
            val gmmIntentUri =
                Uri.parse("http://maps.google.co.in/maps?q={${args.bankItem.closestATM}}")
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")
            startActivity(mapIntent)
        })

    }

}