package com.example.chatassignment.Activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.lifecycle.Observer
import com.example.chatassignment.Adapters.UsersAdapter
import com.example.chatassignment.Models.User
import com.example.chatassignment.R
import com.example.chatassignment.Room.ViewModels.UserViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var usersAdapter: UsersAdapter
    private var userViewModel: UserViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        //Instantiate search for easier user lookup
        setupSeachView()

        //Instantiate viewmodel
        userViewModel = ViewModelProviders.of(this).get(UserViewModel::class.java)

        //Populate recyclerview with users retrieved from the database
        userViewModel?.getUsers()?.observe(this, Observer<List<User>> { this.setRecyclerViews(it) })

    }

    private fun setRecyclerViews(users: List<User>?) {
        usersAdapter = UsersAdapter(users)
        usersRecyclerView?.layoutManager = LinearLayoutManager(this)
        usersRecyclerView?.adapter = usersAdapter

        usersAdapter.setOnUserSelectedListener(object : UsersAdapter.IUserSelected {
            override fun onItemClicked(user: User) {
                val intent = Intent(this@MainActivity, MessageActivity::class.java).apply {
                    //Data to be sent to the Messages activity
                    putExtra("userId", user.userId)
                    putExtra("userName", user.name)
                    putExtra("userImage", user.userImage)
                }
                startActivity(intent)
            }
        })
    }

    private fun setupSeachView() {
        allChatsSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
//                this@MainActivity.hideKeyboard()
                return false
            }

            override fun onQueryTextChange(query: String?): Boolean {
                //Enable search on recyclerview
                usersAdapter.getFilter().filter(query)
                return true
            }
        })
    }

}
