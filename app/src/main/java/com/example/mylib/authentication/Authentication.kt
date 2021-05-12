package com.example.mylib.authentication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.mylib.R
import com.example.mylib.databinding.FragmentAuthenticationBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class Authentication : Fragment() {

    private lateinit var binding: FragmentAuthenticationBinding
    private lateinit var viewModel: AuthenticationViewModel
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this).get(AuthenticationViewModel::class.java)

        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_authentication,container,false)
        binding.lifecycleOwner = this
        binding.viewmodel = viewModel

        binding.loginButton.setOnClickListener { onLogin() }
        binding.createAccountButton.setOnClickListener { onCreateAccount() }

        return binding.root
    }

    private fun onCreateAccount() {
        auth.createUserWithEmailAndPassword(viewModel.email.value!!, viewModel.password.value!!)
            .addOnCompleteListener(requireActivity()) { task ->
                if (!task.isSuccessful) {
                    Toast.makeText(context, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun onLogin() {
        auth.signInWithEmailAndPassword(viewModel.email.value!!, viewModel.password.value!!)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    //TODO: Navigate to next fragment
                } else {
                    Toast.makeText(context, "Authentication failed.", Toast.LENGTH_SHORT).show()
                }
            }
    }

}