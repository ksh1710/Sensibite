package com.example.innogeeks_team_project

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.innogeeks_team_project.databinding.ActivitySignInBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.values

class SignInActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignInBinding

//    lateinit var dbref: DatabaseReference
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        val uid = firebaseAuth.currentUser?.uid
//        dbref = FirebaseDatabase.getInstance().getReference("User_Details")
//

        firebaseAuth = FirebaseAuth.getInstance()
        progressBar = binding.signinProgressBar

        binding.textView.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

        binding.button.setOnClickListener {
            val email = binding.emailEt.text.toString()
            val pass = binding.passET.text.toString()

            if (email.isNotEmpty() && pass.isNotEmpty()) {
                progressBar.visibility = View.VISIBLE // Show the progress bar

                firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener { task ->
                    progressBar.visibility = View.GONE // Hide the progress bar

                    if (task.isSuccessful) {
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this, task.exception?.message, Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(this, "Empty Fields Are not Allowed !!", Toast.LENGTH_SHORT).show()

            }
        }
    }

//    override fun onStart() {
//
//        firebaseAuth = FirebaseAuth.getInstance()
//        val currentUser = firebaseAuth.currentUser
//        if(currentUser != null){
//            val i = Intent(this,UserDetails::class.java)
////            startActivity(i)
//        }
//        super.onStart()
//    }

//    override fun onStart() {
//        super.onStart()
//
//        if(firebaseAuth.currentUser != null){
//            val intent = Intent(this, MainActivity::class.java)
//            startActivity(intent)
//        }
//    }
}