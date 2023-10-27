package com.example.innogeeks_team_project

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.innogeeks_team_project.databinding.ActivitySignInBinding
import com.example.innogeeks_team_project.databinding.ActivityUserDetailsBinding
import com.example.innogeeks_team_project.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class UserDetails : AppCompatActivity() {

     lateinit var binding: ActivityUserDetailsBinding
     lateinit var auth: FirebaseAuth
     lateinit var dbref: DatabaseReference
     lateinit var storageref: StorageReference
     lateinit var imgUri: Uri
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()
        val uid = auth.currentUser?.uid
        dbref = FirebaseDatabase.getInstance().getReference("User_Details")
//
        binding.saveBtn.setOnClickListener {
            val name = binding.nameTV.text.toString()
            val age = binding.ageTV.text.toString()
            val email = auth.currentUser?.email
            val weight = binding.weightTV.text.toString()
            val user = User(name,email, age,weight)
//
            if (uid != null ) {
                dbref.child(uid).setValue(user).addOnCompleteListener {
                    if (it.isSuccessful) {
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
//                    imageUpload()
                    } else {
                        Toast.makeText(this, "failed to update profile", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
//
//    private fun imageUpload() {
//        imgUri =Uri.parse("android.reource//$packageName/${R.drawable.item1}")
//        storageref = FirebaseStorage.getInstance().getReference("users/"+auth.currentUser?.uid)
//        storageref.putFile(imgUri).addOnSuccessListener {
//            Toast.makeText(this, "successful", Toast.LENGTH_SHORT).show()
//            val intent = Intent(this,MainActivity::class.java)
//            startActivity(intent)
//        }.addOnFailureListener{
//            Toast.makeText(this, "failed", Toast.LENGTH_SHORT).show()
//        }
//    }
}