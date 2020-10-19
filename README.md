# ChatAssignment
Chat assignment

Android Kotlin Room MVVM Coroutines

This is an android mock messaging chat application. Sending a message to a user echoes the message twice after 0.5 seconds. All data is persisted locally. 

The home page consists of a recyclerview containing 200 users with a whatsapp-like design. Clicking on any user takes you to the messages activity.
In this message activity to the top left you can press the back icon to go back, and in the top right you can press the bin icon which will prompt a dialog asking you if you want to clear this chat.

I've implemented the MVVM architecture along with room database which handles my local database
Data binding with adapter.

Coroutines for concurrent programming so that the ui thread doesn't get overloaded.
