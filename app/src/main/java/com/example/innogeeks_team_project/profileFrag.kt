package com.example.innogeeks_team_project//package com.example.innogeeks_team_project
//
//import android.os.Bundle
//import androidx.fragment.app.Fragment
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//class com.example.innogeeks_team_project.profileFrag : Fragment() {
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        return inflater.inflate(R.layout.fragment_profile, container, false)
//    }
//}


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.google.firebase.auth.FirebaseAuth

class profileFrag : Fragment() {

    private lateinit var logoutButton: Button
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate( R.layout.fragment_profile, container, false)

        // Initialize Firebase Authentication
        firebaseAuth = FirebaseAuth.getInstance()

        // Find the logout button in the layout
        logoutButton = view.findViewById(R.id.logoutButton)

        // Handle the logout button click
        logoutButton.setOnClickListener {
            // Sign out the user
            firebaseAuth.signOut()
            // Redirect to the login or sign-in activity
            // You can replace SignInActivity::class.java with your desired destination
            val intent = Intent(activity, SignInActivity::class.java)
            startActivity(intent)
        }



        return view
    }
}
