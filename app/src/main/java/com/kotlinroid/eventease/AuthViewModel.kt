package com.kotlinroid.eventease

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.auth.FirebaseAuth


class AuthViewModel() : ViewModel() {

    private val auth : FirebaseAuth = FirebaseAuth.getInstance()

    private val _authState = MutableLiveData<AuthState>()
    val authState: LiveData<AuthState> = _authState

    init {
        checkAuthState()
    }

    private fun checkAuthState(){
        if (auth.currentUser == null){
            _authState.value = AuthState.Unauthenticated
        }else{
            _authState.value = AuthState.Authenticated
        }
    }

    fun login(auth : FirebaseAuth?, email : String, password : String){
        if (auth == null){
            return
        }


        if (email.isEmpty() || password.isEmpty()){
            _authState.value = AuthState.Error("Email and password cannot be empty")
            return
        }

        _authState.value = AuthState.Loading
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener{task ->
                if (task.isSuccessful){
                    _authState.value = AuthState.Authenticated
                }else{
                    _authState.value = AuthState.Error(task.exception?.message ?: "Unknown error")
                }

            }
    }

    fun register(auth : FirebaseAuth?, context: Context, email : String, password : String, phoneNumber : Long, firstName : String, lastName : String) {
        if (auth == null) {
            return
        }

        if (email.isEmpty() || password.isEmpty() || phoneNumber.toString()
                .isEmpty() || firstName.isEmpty() || lastName.isEmpty()
        ) {
            _authState.value = AuthState.Error("Fill all the Fields.")
            return
        }

        val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
        _authState.value = AuthState.Loading
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {

                    val user = auth.currentUser
                    val userId = user?.uid ?: ""
                    val userData = hashMapOf(
                        "Phone Number" to phoneNumber,
                        "Email" to email,
                        "First Name" to firstName,
                        "Last Name" to lastName
                    )
                    firestore.collection("users").document(userId)
                        .set(userData)
                        .addOnSuccessListener {
                            Toast.makeText(context, "Registered Successfully", Toast.LENGTH_SHORT)
                                .show()
                            _authState.value = AuthState.Authenticated
                        }
                        .addOnFailureListener {
                            _authState.value =
                                AuthState.Error(task.exception?.message ?: "Unknown error")
                        }
                } else {
                    _authState.value = AuthState.Error(task.exception?.message ?: "Unknown error")
                }

            }

    }


    fun forgotPassword(auth : FirebaseAuth?, email : String, context: Context){
        if (auth == null){
            return
        }


        _authState.value = AuthState.Loading
        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener{task ->
                if (task.isSuccessful){
                    Toast.makeText(context, "Password reset link successfully sent to your email.", Toast.LENGTH_LONG).show()
                }else{
                    _authState.value = AuthState.Error(task.exception?.message ?: "Unknown error")
                }

            }
    }



    fun logout(auth : FirebaseAuth?){
        if (auth == null){
            return
        }
        auth.signOut()
        _authState.value = AuthState.Unauthenticated
    }


}

sealed class AuthState{
    object Authenticated : AuthState()
    object Unauthenticated : AuthState()
    object Loading : AuthState()
    data class Error(val message : String) : AuthState()
}