package com.kotlinroid.eventease.viewmodels

import android.provider.CalendarContract.Events
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.kotlinroid.eventease.data.Categories
import com.kotlinroid.eventease.data.Movies
import com.kotlinroid.eventease.data.Popular
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class CardViewModel : ViewModel() {
    // StateFlow to hold the list of items
    private val _items = MutableLiveData<List<Popular>>()
    val items: LiveData<List<Popular>> = _items

    private val _movies = MutableLiveData<List<Movies>>()
    val movies: LiveData<List<Movies>> = _movies

    private val _categories = MutableLiveData<List<Categories>>()
    val categories: LiveData<List<Categories>> = _categories

    // Fetch items from Firebase Firestore
    init {
        fetchItems()

    }

    private fun fetchItems() {
        viewModelScope.launch {
            val db = FirebaseFirestore.getInstance()
            val fetchedItems = mutableListOf<Popular>()

            try {
                val snapshot = db.collection("popular").get().await()
                for (document in snapshot.documents) {
                    val image = document.getString("image") ?: ""
                    val title = document.getString("title") ?: ""
                    val type = document.getString("type") ?: ""
                    val month = document.getString("month") ?: ""
                    val date = document.getString("date") ?: ""
                    fetchedItems.add(Popular(image, title, type, month, date))
                }
            } catch (e: Exception) {
                // Handle error
            }
            _items.value = fetchedItems


            val fetchedmovies = mutableListOf<Movies>()

            try {
                val snapshot = db.collection("movies").get().await()
                for (document in snapshot.documents) {
                    val image = document.getString("image") ?: ""
                    val title = document.getString("title") ?: ""
                    val type = document.getString("type") ?: ""
                    val month = document.getString("month") ?: ""
                    val date = document.getString("date") ?: ""
                    fetchedmovies.add(Movies(image, title, type, month, date))
                }
            } catch (e: Exception) {
                // Handle error
            }
            _movies.value = fetchedmovies


            val fetchedcategories = mutableListOf<Categories>()

            try {
                val snapshot = db.collection("categories").get().await()
                for (document in snapshot.documents) {
                    val image = document.getString("image") ?: ""
                    val title = document.getString("title") ?: ""
                    val number = document.getString("number") ?: ""
                    fetchedcategories.add(Categories(image, title, number))
                }
            } catch (e: Exception) {
                // Handle error
            }
            _categories.value = fetchedcategories
        }

//         fun fetchMovies() {
//            viewModelScope.launch {
//                val db1 = FirebaseFirestore.getInstance()
//                val fetchedmovies = mutableListOf<Movies>()
//
//                try {
//                    val snapshot = db.collection("movies").get().await()
//                    for (document in snapshot.documents) {
//                        val image = document.getString("image") ?: ""
//                        val title = document.getString("title") ?: ""
//                        val type = document.getString("type") ?: ""
//                        val month = document.getString("month") ?: ""
//                        val date = document.getString("date") ?: ""
//                        fetchedmovies.add(Movies(image, title, type, month, date))
//                    }
//                } catch (e: Exception) {
//                    // Handle error
//                }
//
//                _movies.value = fetchedmovies
//            }
//        }
    }
}