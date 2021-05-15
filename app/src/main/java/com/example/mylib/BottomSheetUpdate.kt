package com.example.mylib

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mylib.databinding.InputBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class BottomSheetUpdate(var itemData: ItemData, var id: String, var path: String) : BottomSheetDialogFragment() {

    private var _binding: InputBottomSheetBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        _binding = InputBottomSheetBinding.inflate(inflater, container, false)

        binding.titleInput.setText(itemData.title)
        binding.subtitleInput.setText(itemData.subtitle)
        binding.rateInput.setText(itemData.rate.toString())

        binding.button.setOnClickListener {
            val title = binding.titleInput.text.toString()
            val subtitle = binding.subtitleInput.text.toString()
            val rate = binding.rateInput.text.toString().toDouble()

            val childUpdates = mapOf<String, Any>(
                "$path/${id}/title" to title,
                "$path/${id}/subtitle" to subtitle,
                "$path/${id}/rate" to rate,
            )

            Firebase.database.reference.updateChildren(childUpdates)

            dismiss()
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}