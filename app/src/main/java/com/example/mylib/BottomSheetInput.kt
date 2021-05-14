package com.example.mylib

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mylib.databinding.InputBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class BottomSheetInput(var path: String, var position: Int): BottomSheetDialogFragment() {

    private var _binding: InputBottomSheetBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = InputBottomSheetBinding.inflate(inflater, container, false)

        binding.button.setOnClickListener {
            val title = binding.titleInput.text.toString()
            val subtitle = binding.subtitleInput.text.toString()
            val rate = binding.rateInput.text.toString().toDouble()

            var database = Firebase.database.reference
            database = database.child(path)
            database = database.push()
            database.setValue(ItemData(title, subtitle, rate, position))

            dismiss()
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}