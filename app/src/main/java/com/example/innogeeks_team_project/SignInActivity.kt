package com.example.innogeeks_team_project

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.innogeeks_team_project.databinding.ActivitySignInBinding
import com.google.firebase.auth.FirebaseAuth

class SignInActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignInBinding

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var progressBar: ProgressBar
    val loading = customDialog(this)


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
        val sharedPref = getSharedPreferences("myPref", Context.MODE_PRIVATE)
        val useremail = sharedPref.getString("Email", "")
        val userpass = sharedPref.getString("Password", "")

        if (useremail != "" && userpass != "") {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        binding.button.setOnClickListener {
            val email = binding.emailEt.text.toString()
            val pass = binding.passET.text.toString()

            if (email.isNotEmpty() && pass.isNotEmpty()) {
//                progressBar.visibility = View.VISIBLE // Show the progress bar
                loading.dialogRunning()
                val editor = sharedPref.edit()
                editor.putString("Email", email)
                editor.putString("Password", pass)
                editor.apply()

                firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener { task ->
//                    progressBar.visibility = View.GONE // Hide the progress bar
                    loading.dialogClose()
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
}