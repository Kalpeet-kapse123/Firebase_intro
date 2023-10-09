package com.kalpeet.firebase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.kalpeet.firebase.databinding.ActivityAddUserBinding

class addUserActivity : AppCompatActivity() {
    lateinit var binding: ActivityAddUserBinding
    val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    val myReference: DatabaseReference = database.reference.child("MyUsers")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.buttonAddUser.setOnClickListener {
            addUserToDatabase()
        }
        title="Add User"
    }

    fun addUserToDatabase() {
        val name: String = binding.editTextName.text.toString()
        val age: Int = binding.editTextAge.text.toString().toInt()
        val email: String = binding.editTextEmail.text.toString()
        val id: String = myReference.push().key.toString()
        val user = Users(id, name, age, email)
        myReference.child(id).setValue(user).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(
                    applicationContext,
                    "The new user has been added to the database",
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            } else {
                Toast.makeText(applicationContext, task.exception.toString(), Toast.LENGTH_SHORT)
                    .show()

            }
        }
    }

}