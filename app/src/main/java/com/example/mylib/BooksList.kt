package com.example.mylib

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mylib.databinding.FragmentBooksListBinding

class BooksList : Fragment() {

    private var _binding: FragmentBooksListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBooksListBinding.inflate(inflater, container, false)



        return binding.root
    }

}