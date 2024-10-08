package com.example.letstalk.Uitil

import android.app.Application
import android.content.Context
import android.util.Log
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.letstalk.model.Users
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

object AppStatic {
    fun checkUserExist(context: Context, onDataChange : UnitFun, onCancelled : FunDBError) {
        val database = FirebaseDatabase.getInstance()
        val auth = FirebaseAuth.getInstance()
        val ref = database.getReference("users").child(auth.uid!!)
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val user: Users? = snapshot.getValue(Users::class.java)
                user?.let(onDataChange)
            }
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "User not found", Toast.LENGTH_SHORT).show()
                onCancelled(error)
            }
        })
    }
}

typealias UnitFun = (value : Users)->Unit
typealias FunDBError = (error : DatabaseError)->Unit