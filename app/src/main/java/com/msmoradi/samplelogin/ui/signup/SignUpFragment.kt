package com.msmoradi.samplelogin.ui.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import com.msmoradi.samplelogin.databinding.FragmentSignUpBinding
import com.msmoradi.samplelogin.utils.disableErrorAfterTextChanged
import com.msmoradi.samplelogin.utils.hideKeyboard
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
        binding.apply {
            signUpButton.setOnClickListener {
                hideKeyboard()
                onSignUpButtonClicked()
            }
            fullNameTextField.disableErrorAfterTextChanged()
            userNameTextField.disableErrorAfterTextChanged()
            emailTextField.disableErrorAfterTextChanged()
            passwordTextField.disableErrorAfterTextChanged()
            confirmPasswordTextField.disableErrorAfterTextChanged()
        }

        signUpViewModel.apply {
            loginNavigationObservable.observe(viewLifecycleOwner) {
                navigateToLogin()
            }
            fullNameErrorObservable.observe(viewLifecycleOwner, ::showFullNameError)
            emailErrorObservable.observe(viewLifecycleOwner, ::showEmailError)
            passwordErrorObservable.observe(viewLifecycleOwner, ::showPasswordNameError)
            confirmPasswordErrorObservable.observe(viewLifecycleOwner, ::showConfirmPasswordError)
            userNameErrorObservable.observe(viewLifecycleOwner, ::showUserNameError)
        }
    }

    private fun onSignUpButtonClicked() {
        binding.let {
            signUpViewModel.signUp(
                it.fullNameTextField.editText?.text.toString(),
                it.userNameTextField.editText?.text.toString(),
                it.emailTextField.editText?.text.toString(),
                it.passwordTextField.editText?.text.toString(),
                it.confirmPasswordTextField.editText?.text.toString()
            )
        }
    }

    private fun navigateToLogin() {
        findNavController().navigate(
            SignUpFragmentDirections.actionSignUpFragmentToLoginFragment()
        )
    }

    private fun showFullNameError(@StringRes id: Int) {
        binding.fullNameTextField.error = getString(id)
    }

    private fun showEmailError(@StringRes id: Int) {
        binding.emailTextField.error = getString(id)
    }

    private fun showPasswordNameError(@StringRes id: Int) {
        binding.passwordTextField.error = getString(id)
    }

    private fun showConfirmPasswordError(@StringRes id: Int) {
        binding.confirmPasswordTextField.error = getString(id)
    }

    private fun showUserNameError(@StringRes id: Int) {
        binding.userNameTextField.error = getString(id)
    }
}