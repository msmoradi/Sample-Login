package com.msmoradi.samplelogin.ui.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import com.msmoradi.samplelogin.databinding.FragmentSignUpBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment : Fragment() {

    private val signUpViewModel: SignUpViewModel by viewModels()

    private var _binding: FragmentSignUpBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.signUpButton.setOnClickListener {
            signUpViewModel.signUp(
                binding.fullNameTextField.editText?.text.toString(),
                binding.userNameTextField.editText?.text.toString(),
                binding.passwordTextField.editText?.text.toString(),
                binding.confirmPasswordTextField.editText?.text.toString()
            )
        }

        signUpViewModel.loginNavigationObservable.observe(viewLifecycleOwner) {
            navigateToLogin()
        }
    }

    private fun navigateToLogin() {
        findNavController().navigate(
            SignUpFragmentDirections.actionSignUpFragmentToLoginFragment()
        )
    }
}