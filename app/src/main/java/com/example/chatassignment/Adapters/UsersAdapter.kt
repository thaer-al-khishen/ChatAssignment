package com.example.chatassignment.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.chatassignment.Models.User
import com.example.chatassignment.R
import com.example.chatassignment.databinding.UserRowBinding
import java.text.SimpleDateFormat
import java.util.*

class UsersAdapter(private val users: List<User>?) :
    RecyclerView.Adapter<UsersAdapter.InboxViewHolder>(), Filterable {

    private var listener: IUserSelected? = null

    //Filtered list for searchview filtering
    var usersFilteredList: List<User> = this.users!!

    init {
        if (users != null) {
            usersFilteredList = users
        }
    }

    fun setOnUserSelectedListener(listener: IUserSelected) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InboxViewHolder {
        return InboxViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.row_user,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        //Recyclerview size
        return usersFilteredList.size
    }

    override fun onBindViewHolder(holder: InboxViewHolder, position: Int) {

        //Populate userIcon with the actual icon provided in ApplicationDatabase class
        holder.binding.userIcon.setImageResource(usersFilteredList.get(position).userImage)

        //Populate the last message sent/received
        holder.binding.userMessageTextView.text = usersFilteredList.get(position).lastMessage
        val lastMessageReference: String = usersFilteredList.get(position).lastMessage
        if (lastMessageReference.toString().equals("")) {
            holder.binding.userDateTextView.text = ""
        } else {
            //Format Date
            val sdf = SimpleDateFormat("hh:mm")
            val date: Date? = usersFilteredList[position].lastMessageDate
            val newDate: String = sdf.format(date)

            //Populate DateTextView
            holder.binding.userDateTextView.text = newDate
        }

        //Populate usernameTextView
        holder.binding.userNameTextView.text = usersFilteredList[position].name

        holder.binding.userLayout.setOnClickListener {
            listener?.onItemClicked(usersFilteredList[position])
        }
    }

    class InboxViewHolder(val binding: UserRowBinding) : RecyclerView.ViewHolder(binding.root)

    interface IUserSelected {
        fun onItemClicked(user: User)
    }

    //Searchview filtering
    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    if (users != null) {
                        usersFilteredList = users
                    }
                } else {
                    val resultList = ArrayList<User>()
                    if (users != null) {
                        for (row in users) {
                            if (row.name.toLowerCase(Locale.ROOT)
                                    .contains(charSearch.toLowerCase(Locale.ROOT))
                            ) {
                                resultList.add(row)
                            }
                        }
                    }
                    usersFilteredList = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = usersFilteredList
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                usersFilteredList = results?.values as ArrayList<User>
                notifyDataSetChanged()
            }

        }
    }
}
