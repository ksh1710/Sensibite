//package com.example.innogeeks_team_project
//
//import android.content.Intent
//import android.os.Bundle
//import android.widget.Toast
//import androidx.appcompat.app.AppCompatActivity
// import com.example.innogeeks_team_project.databinding.ActivitySignInBinding
//import com.google.firebase.auth.FirebaseAuth
//
//class SignInActivity : AppCompatActivity() {
//
//    private lateinit var binding: ActivitySignInBinding
//    private lateinit var firebaseAuth: FirebaseAuth
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = ActivitySignInBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//
//        firebaseAuth = FirebaseAuth.getInstance()
//        binding.textView.setOnClickListener {
//            val intent = Intent(this, SignUpActivity::class.java)
//            startActivity(intent)
//        }
//
//        binding.button.setOnClickListener {
//            val email = binding.emailEt.text.toString()
//            val pass = binding.passET.text.toString()
//
//            if (email.isNotEmpty() && pass.isNotEmpty()) {
//
//                firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener {
//                    if (it.isSuccessful) {
//                        val intent = Intent(this, MainActivity::class.java)
//                        startActivity(intent)
//                    } else {
//                        Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
//
//                    }
//                }
//            } else {
//                Toast.makeText(this, "Empty Fields Are not Allowed !!", Toast.LENGTH_SHORT).show()
//
//            }
//        }
//    }
//
//    override fun onStart() {
//        super.onStart()
//
//        if(firebaseAuth.currentUser != null){
//            val intent = Intent(this, MainActivity::class.java)
//            startActivity(intent)
//        }
//    }
//}

package com.example.innogeeks_team_project

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.innogeeks_team_project.databinding.ActivitySignInBinding
import com.google.firebase.auth.FirebaseAuth

class SignInActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignInBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

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

    override fun onStart() {
        super.onStart()

        if (firebaseAuth.currentUser != null) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}