package com.example.chatassignment.Activities

import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chatassignment.Adapters.MessageAdapter
import com.example.chatassignment.Models.Message
import com.example.chatassignment.Models.User
import com.example.chatassignment.R
import com.example.chatassignment.Room.ViewModels.MessageViewModel
import com.example.chatassignment.Room.ViewModels.UserViewModel
import kotlinx.android.synthetic.main.activity_message.*
import java.util.*

class MessageActivity : AppCompatActivity() {

    //Set limit to edittext size
    private val DEFAULT_MSG_LENGTH_LIMIT = 400

    private var messageViewModel: MessageViewModel? = null
    private var userViewModel: UserViewModel? = null

    lateinit var adapter: MessageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_message)

        supportActionBar?.hide()
        //Retrieve the values sent by the intent from MainActivity
        val messageAccordingToUserId = intent.getIntExtra("userId", 0)
        val nameAccordingToUserId = intent.getStringExtra("userName")
        val userImage = intent.getIntExtra("userImage", 1)

        resultingNameTextView.text = nameAccordingToUserId

        //Instantiate viewmodels
        messageViewModel = ViewModelProviders.of(this).get(MessageViewModel::class.java)
        userViewModel = ViewModelProviders.of(this).get(UserViewModel::class.java)

        //Retrieve messages from database and populate recyclerview
        messageViewModel?.getMessages(messageAccordingToUserId)
            ?.observe(this, Observer<List<Message>> {
                this.renderMessages(it)
            })

        // Enable Send button when there's text to send
        messageEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                sendButton.isEnabled = charSequence.toString().trim { it <= ' ' }.isNotEmpty()
            }

            override fun afterTextChanged(editable: Editable) {

            }
        })
        //Set limit to edittext characters
        messageEditText.filters =
            arrayOf<InputFilter>(InputFilter.LengthFilter(DEFAULT_MSG_LENGTH_LIMIT))

        //Send message
        sendButton.setOnClickListener {

            val currentDateAsDate = Calendar.getInstance().time

            val message = Message(
                0,
                messageAccordingToUserId,
                messageEditText.text.toString(),
                currentDateAsDate
            )

            messageViewModel?.setMessage(message)

            //Get reply after 0.5 seconds:
            Handler().postDelayed({ messageViewModel?.setMessage(message) }, 500)
            Handler().postDelayed({ messageViewModel?.setMessage(message) }, 500)

            //Update the user from outside this activity with the latest message sent and its time
            userViewModel?.updateUser(
                User(
                    messageAccordingToUserId,
                    nameAccordingToUserId,
                    message.message,
                    message.date!!,
                    userImage
                )
            )

            // Clear input box
            messageEditText.setText("")
        }

        //Navigate back
        backArrow.setOnClickListener {
            super.onBackPressed()
        }

        resultingUserIcon.setImageResource(userImage)

        //Clear chat with a popup dialog
        trashBinImage.setOnClickListener {
            this.let {
                val builder = AlertDialog.Builder(this)
                val alertDialog: AlertDialog.Builder = AlertDialog.Builder(this@MessageActivity)
                alertDialog.setTitle("AlertDialog")
                alertDialog.setMessage("Do you want to clear this chat?")
                alertDialog.setPositiveButton(
                    "yes"
                ) { _, _ ->
//                    Toast.makeText(this@MainActivity, "Alert dialog closed.", Toast.LENGTH_LONG).show()
                    messageViewModel?.deleteAll(messageAccordingToUserId)
                    userViewModel?.updateUser(
                        User(
                            messageAccordingToUserId,
                            nameAccordingToUserId,
                            "",
                            null,
                            userImage
                        )
                    )
                }
                alertDialog.setNegativeButton(
                    "No"
                ) { _, _ -> }
                val alert: AlertDialog = alertDialog.create()
                alert.setCanceledOnTouchOutside(false)
                alert.show()
            }
        }

    }

    //Setup messages adapter
    private fun renderMessages(messages: List<Message>?) {
        adapter = MessageAdapter(messages)
        val layoutManager = LinearLayoutManager(this)
        layoutManager.stackFromEnd = true
        messageRecyclerView.layoutManager = layoutManager
        messageRecyclerView.adapter = adapter
    }

}
