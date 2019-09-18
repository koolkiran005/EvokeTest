package com.kiran.evoketest.android

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.kiran.evoketest.android.databinding.ItemListBinding
import com.kiran.evoketest.android.model.User
import kotlin.collections.ArrayList

class UserAdapter(
    private val parentActivity: MainActivity,
    private val allUsers: ArrayList<User>
) : RecyclerView.Adapter<UserAdapter.ViewHolder>(), Filterable {
    var filterUsers = allUsers
    private val itemSizeCallBack: ItemSizeCallBack = parentActivity
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemListBinding.bind(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_list,
                parent,
                false
            )
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = filterUsers[position]
        holder.bind(user)
        with(holder.itemView) {
            tag = user
        }
    }

    override fun getItemCount() = filterUsers.size

    inner class ViewHolder(val binding: ItemListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User) {
            binding.user = user
            binding.executePendingBindings()
        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(sequence: CharSequence?): FilterResults {
                val cs = sequence.toString()
                var filterUsersList = ArrayList<User>()
                if (cs.isEmpty())
                    filterUsersList = allUsers
                else {
                    for (user in allUsers) {
                        if (user.name.toString().toLowerCase().contains(cs.toLowerCase()) ||
                            user.email.toString().toLowerCase().contains(cs.toLowerCase())
                        ) {
                            filterUsersList.add(user)
                        }
                    }
                }
                val filterResults = FilterResults()
                filterResults.values = filterUsersList
                return filterResults
            }

            override fun publishResults(sequence: CharSequence?, results: FilterResults?) {
                filterUsers = results?.values as ArrayList<User>
                itemSizeCallBack.onItemSizeChanged(filterUsers.size)
                notifyDataSetChanged()
            }
        }
    }
}