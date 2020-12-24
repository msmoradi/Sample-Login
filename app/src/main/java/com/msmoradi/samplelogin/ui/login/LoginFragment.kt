package com.msmoradi.samplelogin.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.msmoradi.samplelogin.databinding.FragmentLoginBinding
import com.msmoradi.samplelogin.model.User
import com.msmoradi.samplelogin.utils.disableErrorAfterTextChanged
import com.msmoradi.samplelogin.utils.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private val loginViewModel: LoginViewModel by viewModels()

    private var _binding: FragmentLoginBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loginViewModel.apply {
            profileNavigationObservable.observe(viewLifecycleOwner, ::navigateToProfile)
            userListNavigationObservable.observe(viewLifecycleOwner) { navigateToUserList() }
            snackBarObservable.observe(viewLifecycleOwner, ::showSnackBar)
            userNameErrorObservable.observe(viewLifecycleOwner, ::showUserNameError)
            passwordErrorObservable.observe(viewLifecycleOwner, ::showPasswordError)
        }
        binding.apply {
            loginButton.setOnClickListener {
                hideKeyboard()
                onLoginButtonClicked()
            }
            signUpButton.setOnClickListener {
                navigateToSignUp()
            }
            userNameEditText.disableErrorAfterTextChanged()
            passwordEditText.disableErrorAfterTextChanged()
        }
    }

    private fun onLoginButtonClicked() {
        loginViewModel.loginClicked(
            binding.userNameEditText.editText?.text.toString(),
            binding.passwordEditText.editText?.text.toString()
        )
    }

    private fun showPasswordError(@StringRes id: Int) {
        binding.passwordEditText.error = getString(id)
    }

    private fun showUserNameError(@StringRes id: Int) {
        binding.userNameEditText.error = getString(id)
    }

    private fun navigateToProfile(user: User) {
        findNavController().navigate(
            LoginFragmentDirections.actionLoginFragmentToProfileFragment(
                user = user,
                editable = true
            )
        )
    }

    private fun navigateToSignUp() {
        findNavController().navigate(
            LoginFragmentDirections.actionLoginFragmentToSignUpFragment()
        )
    }

    private fun navigateToUserList() {
        findNavController().navigate(
            LoginFragmentDirections.actionLoginFragmentToUserListFragment()
        )
    }

    private fun showSnackBar(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).show()
    }

}