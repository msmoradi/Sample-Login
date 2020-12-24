package com.msmoradi.samplelogin.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.msmoradi.samplelogin.databinding.FragmentProfileBinding
import com.msmoradi.samplelogin.utils.disableErrorAfterTextChanged
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private val args: ProfileFragmentArgs by navArgs()

    private val editable by lazy {
        args.editable
    }

    private val user by lazy {
        args.user
    }

    private val profileViewModel: ProfileViewModel by viewModels()

    private var _binding: FragmentProfileBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            fullNameTextView.text = user.fullName
            userNameTextField.apply {
                disableErrorAfterTextChanged()
                editText?.setText(user.username)
            }
            emailTextField.apply {
                disableErrorAfterTextChanged()
                editText?.setText(user.email)
            }
            if (!editable) {
                buttonGroup.visibility = View.GONE
                userNameTextField.editText?.isEnabled = false
                emailTextField.editText?.isEnabled = false
            }

            updateButton.setOnClickListener {
                profileViewModel.updateUser(
                    user, username = userNameTextField.editText?.text.toString(),
                    email = emailTextField.editText?.text.toString()
                )
            }

            deleteButton.setOnClickListener { profileViewModel.removeUser(user) }
        }

        profileViewModel.apply {
            loginNavigationObservable.observe(viewLifecycleOwner) { navigateToLogin() }
            userNameErrorObservable.observe(viewLifecycleOwner, ::showUserNameError)
            emailErrorObservable.observe(viewLifecycleOwner, ::showEmailError)
        }
    }

    private fun showEmailError(@StringRes id: Int) {
        binding.emailTextField.error = getString(id)
    }

    private fun showUserNameError(@StringRes id: Int) {
        binding.userNameTextField.error = getString(id)
    }

    private fun navigateToLogin() {
        findNavController().navigate(
            ProfileFragmentDirections.actionProfileFragmentToLoginFragment()
        )
    }
}