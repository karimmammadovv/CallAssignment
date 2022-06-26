package com.karimmammadov.callingproject.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.karimmammadov.callingproject.R
import com.karimmammadov.callingproject.adapter.FeedRecyclerAdapter
import com.karimmammadov.callingproject.databinding.ActivityFeedBinding
import com.karimmammadov.callingproject.model.User

class FeedActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFeedBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var database : FirebaseFirestore
    private lateinit var userArrayList: ArrayList<User>
    private lateinit var feedAdapter : FeedRecyclerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFeedBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        auth = Firebase.auth
        database = Firebase.firestore
        userArrayList = ArrayList<User>()
        getData()
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        feedAdapter = FeedRecyclerAdapter(userArrayList)
        binding.recyclerView.adapter = feedAdapter
    }

    private fun getData(){
        database.collection("Users").addSnapshotListener { value, error ->
            if(error != null){
                Toast.makeText(this,error.localizedMessage,Toast.LENGTH_LONG).show()
            }else{
                if(value!=null){
                    if(!value.isEmpty){
                        val documents = value.documents
                        userArrayList.clear()
                        for(document in documents){
                            val userEmail = document.get("userEmail") as String
                            println(userEmail)
                            val user = User(userEmail)
                            userArrayList.add(user)
                        }
                        feedAdapter.notifyDataSetChanged()
                    }
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.setting_menu,menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
       if( item.itemId == R.id.logout){
           auth.signOut()
        val intent = Intent(this, MainActivity::class.java)
           startActivity(intent)
           finish()
       }

        return super.onOptionsItemSelected(item)
    }
}