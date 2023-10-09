package com.kalpeet.firebase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.kalpeet.firebase.databinding.ActivityAddUserBinding
import com.kalpeet.firebase.databinding.ActivityUpdateUserBinding

class updateUserActivity : AppCompatActivity() {
    lateinit var binding: ActivityUpdateUserBinding
    val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    val myReference: DatabaseReference = database.reference.child("MyUsers")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        title = "Update User"
        getAndSetData()
        binding.buttonUpdateUser.setOnClickListener {
            updateDta()
        }
    }

    private fun updateDta() {
        val updatedName = binding.editTextName.text.toString()
        val updatedAge = binding.editTextAge.text.toString().toInt()
        val updatedEmail = binding.editTextEmail.text.toString()
        val userId = intent.getStringExtra("id").toString()
        val userMap = mutableMapOf<String, Any>()
        userMap["userId"] = userId
        userMap["userName"] = updatedName
        userMap["userAge"] = updatedAge
        userMap["UserEmail"] = updatedEmail
        myReference.child(userId).updateChildren(userMap).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(applicationContext, "The user has been updated.", Toast.LENGTH_SHORT)
                    .show()
                finish()
            }

        }
    }

    private fun getAndSetData() {
        val name = intent.getStringExtra("name")
        val age = intent.getIntExtra("age", 0).toString()
        val email = intent.getStringExtra("email")

        binding.editTextName.setText(name)
        binding.editTextAge.setText(age)
        binding.editTextEmail.setText(email)
    }

}