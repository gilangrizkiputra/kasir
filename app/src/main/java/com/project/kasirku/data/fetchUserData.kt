package com.project.kasirku.data

import android.service.autofill.UserData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.project.kasirku.model.User

fun fetchUserData(userId: String, onUserDataFetched: (User?) -> Unit) {
    val database = FirebaseDatabase.getInstance().reference.child("user").child(userId)

    database.addValueEventListener(object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            val user = snapshot.getValue(User::class.java)
            onUserDataFetched(user)
        }

        override fun onCancelled(error: DatabaseError) {
            onUserDataFetched(null)
        }
    })
}