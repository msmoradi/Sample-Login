package com.msmoradi.samplelogin.ui.userList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import com.msmoradi.samplelogin.databinding.FragmentUserListBinding
import com.msmoradi.samplelogin.model.User
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserListFragment : Fragment() {

    private val userListViewModel: UserListViewModel by viewModels()

    private var _binding: FragmentUserListBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUserListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userListViewModel.apply {
            userObservable.observe(viewLifecycleOwner, ::observeUserList)
            removeUserObservable.observe(viewLifecycleOwner, ::removeUserObserver)
        }

    }

    private fun removeUserObserver(user: User) {
        (binding.userListRecyclerView.adapter as? UserListAdapter)?.remove(user)
    }

    private fun observeUserList(list: List<User>) {
        binding.userListRecyclerView.adapter = UserListAdapter(list as ArrayList<User>).apply {
            itemClickCallBack = {
                navigateToProfile(it)
            }
            removeClickCallBack = {
                userListViewModel.removeUser(it)
            }
        }
    }

    private fun navigateToProfile(user: User) {
        findNavController().navigate(
            UserListFragmentDirections.actionUserListFragmentToProfileFragment(user)
        )

    }
}