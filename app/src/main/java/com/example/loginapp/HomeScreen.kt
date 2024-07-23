// MainActivity.kt
package com.example.loginapp

import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.ImageButton
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.loginapp.databinding.ActivityHomeScreenBinding
import java.util.Locale.filter


class HomeScreen : AppCompatActivity() {
    lateinit var binding: ActivityHomeScreenBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var logoutButton: ImageButton
    private lateinit var adapter: GroceryAdapter
    private lateinit var searchEditText: EditText
    private var items = ArrayList<GroceryItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recyclerView = findViewById(R.id.list_view)
        recyclerView.layoutManager = LinearLayoutManager(this)


        items.add(GroceryItem("Apple", "$5.99/lb", R.drawable.apple))
        items.add(GroceryItem("Onions", "$8.99/lb", R.drawable.onions))
        items.add(GroceryItem("Banana", "$3.99/lb", R.drawable.banana))
        items.add(GroceryItem("Potatoes", "$10.49/lb", R.drawable.potatoes))
        items.add(GroceryItem("Grapes", "$6.59/lb", R.drawable.grapes))
        items.add(GroceryItem("Tomatoes", "$2.99/lb", R.drawable.tomato))
        items.add(GroceryItem("Corriander", "$1.89/lb", R.drawable.corriander))
        items.add(GroceryItem("Coffee", "$12.99/lb", R.drawable.coffee))
        items.add(GroceryItem("Cabbage", "$4.39/lb", R.drawable.cabbage))
        items.add(GroceryItem("Garlic", "$9.59/lb", R.drawable.garlic))
        items.add(GroceryItem("Tea", "$16.99/lb", R.drawable.tea))

        adapter = GroceryAdapter(items)
        recyclerView.adapter = adapter

        logoutButton = binding.logoutBtn
        logoutButton.setOnClickListener {
            logout()
        }

        searchEditText = binding.searchEditText
        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable) {
                filter(s.toString())
            }
        })
    }

    private fun filter(text: String) {
        val filteredItems = ArrayList<GroceryItem>()
        for (item in items) {
            if (item.name.lowercase().contains(text.lowercase())) {
                filteredItems.add(item)
            }
        }
        adapter.filterList(filteredItems)
    }

    private fun logout() {
        val sharedPreferences = getSharedPreferences("login_preferences", MODE_PRIVATE)
        val rememberMe = sharedPreferences.getBoolean("rememberMe", false)

        if (!rememberMe) {
            sharedPreferences.edit().clear().apply()
        }

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}