package com.project.kasirku.data

import com.google.firebase.database.FirebaseDatabase
import com.project.kasirku.model.Orders

fun fetchOrderById(orderId: String, onDataFetched: (Orders?) -> Unit) {
    val database = FirebaseDatabase.getInstance().getReference("orders").child(orderId)

    database.get().addOnSuccessListener { snapshot ->
        val order = snapshot.getValue(Orders::class.java)
        onDataFetched(order)
    }.addOnFailureListener {
        onDataFetched(null)
    }
}
