package com.example.letstalk.Uitil

import android.app.ProgressDialog
import android.content.Context
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.example.letstalk.model.Users
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class DataClass {
    companion object {
        inline fun userDetails(crossinline onDataChanged: (value: Users) -> Unit) {
            val database = FirebaseDatabase.getInstance()
            val auth: FirebaseAuth = FirebaseAuth.getInstance()
            val ref = database.getReference("users")
            ref.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (snap in snapshot.children) {
                        val users: Users? = snap.getValue(Users::class.java)
                        onDataChanged(users!!)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    AppLog.logger("Databse Error ${error.message}")
                }
            })
        }
    }
}

fun progresDialog(context: Context, msg: String): ProgressDialog {
    val dialog = ProgressDialog(context)
    dialog.setMessage(msg)
    dialog.setCancelable(false)
    dialog.show()
    return dialog
}

fun placeHolder(cont: Context): CircularProgressDrawable {
    val circularProgressDrawable = CircularProgressDrawable(cont)
    circularProgressDrawable.strokeWidth = 12f
    circularProgressDrawable.centerRadius = 50f
    circularProgressDrawable.start()
    return circularProgressDrawable
}


