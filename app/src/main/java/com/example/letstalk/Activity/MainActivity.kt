package com.example.letstalk.Activity

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.letstalk.R
import com.example.letstalk.Uitil.AppLog
import com.example.letstalk.adapter.UserAdapter
import com.example.letstalk.databinding.ActivityMainBinding
import com.example.letstalk.model.Users
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.storage.FirebaseStorage

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var databaseReference: DatabaseReference
    lateinit var auth: FirebaseAuth
    lateinit var userAdapter: UserAdapter
    lateinit var userList: ArrayList<Users>
    lateinit var dialoge: ProgressDialog
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        userList = ArrayList()
        auth = FirebaseAuth.getInstance()

        dialoge = ProgressDialog(this)
        dialoge.setMessage("Loading User Profiles")
        dialoge.setCancelable(false)

        getAllUsers()


    /*    FirebaseInstanceId.getInstance().instanceId.addOnCompleteListener {
            if (it.isSuccessful) {
                AppLog.logger(it.result?.token!!)
            }
        }*/
    }

    private fun getAllUsers() {
        dialoge.show()
        databaseReference = FirebaseDatabase.getInstance().getReference("users")
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (snap: DataSnapshot in snapshot.children) {
                    val user: Users? = snap.getValue(Users::class.java)
                    if (!user!!.uid.equals(FirebaseAuth.getInstance().uid))
                        userList.add(user)
                    userAdapter = UserAdapter(this@MainActivity, userList)
                    binding.recyclerView.let {
                        it.layoutManager = LinearLayoutManager(this@MainActivity)
                        it.adapter = userAdapter
                    }
                    dialoge.dismiss()
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.logout -> {
                auth = FirebaseAuth.getInstance()
                auth.signOut()
                startActivity(Intent(this, PhoneAuthActivity::class.java))
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.top_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }
}