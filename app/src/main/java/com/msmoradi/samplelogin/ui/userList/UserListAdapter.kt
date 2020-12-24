package com.msmoradi.samplelogin.ui.userList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.msmoradi.samplelogin.databinding.ItemUserViewBinding
import com.msmoradi.samplelogin.model.User

class UserListAdapter(private val userList: ArrayList<User>) :
    RecyclerView.Adapter<UserListAdapter.UserListViewHolder>() {

    var itemClickCallBack: ((User) -> Unit)? = null
    var removeClickCallBack: ((User) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        UserListViewHolder(
            ItemUserViewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun getItemCount() = userList.size

    override fun onBindViewHolder(holder: UserListViewHolder, position: Int) {
        val user = userList[holder.adapterPosition]
        holder.apply {
            bind(user)
            binding.userNameTextView.setOnClickListener {
                itemClickCallBack?.invoke(user)
            }
            binding.removeIcon.setOnClickListener {
                removeClickCallBack?.invoke(user)
            }
        }
    }

    fun remove(user: User) {
        userList.indexOf(user).let {
            userList.removeAt(it)
            notifyItemRemoved(it)
        }
    }

    class UserListViewHolder(val binding: ItemUserViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User) {
            binding.userNameTextView.text = user.username
        }
    }

}