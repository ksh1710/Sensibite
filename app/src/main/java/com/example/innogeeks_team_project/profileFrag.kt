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


import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.example.innogeeks_team_project.databinding.FragmentProfileBinding
import com.example.innogeeks_team_project.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.DatabaseRegistrar
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class profileFrag : Fragment() {
    private var _binding: FragmentProfileBinding? = null

    // This property is only valid between onCreateView and
// onDestroyView.
    private val binding get() = _binding!!

    private lateinit var logoutButton: Button
    private lateinit var dbref: DatabaseReference
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var uid: String
    private lateinit var user: User

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val view = binding.root

//        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        // Initialize Firebase Authentication
        firebaseAuth = FirebaseAuth.getInstance()
        uid = firebaseAuth.currentUser?.uid.toString()
        // Find the logout button in the layout
        logoutButton = view.findViewById(R.id.logoutButton)
        dbref = FirebaseDatabase.getInstance().getReference("User_Details")

        if (uid.isNotEmpty()) {
            fetchUserDetails()
        }
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

    private fun fetchUserDetails() {
        dbref.child(uid).addValueEventListener(object : ValueEventListener {
            @SuppressLint("SetTextI18n")
            override fun onDataChange(snapshot: DataSnapshot) {
                user = snapshot.getValue(User::class.java)!!
                binding.nameField.text = user.name
                binding.EmailField.text = user.email
                binding.ageField.text = "${user.age} years"
                binding.weightField.text = "${user.weight} kg"
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "error ", Toast.LENGTH_SHORT).show()
            }
        })
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}
