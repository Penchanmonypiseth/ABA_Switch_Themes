package com.example.aba_themes// com.example.aba_themes.MainActivity.kt
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences
    private val items = arrayOf("Modern", "Aesthetic")
    private var selectedItemIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        initTheme();
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<LinearLayout>(R.id.toggle_button).setOnLongClickListener {
            showSingleChoiceDialog();
            return@setOnLongClickListener true
        }
        initCurrentSelectionTheme();
    }

    private fun showSingleChoiceDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Select a Theme")

        builder.setSingleChoiceItems(items, selectedItemIndex) { _, which ->
            selectedItemIndex = which
        }

        builder.setPositiveButton("OK") { _, _ ->
            val selectedItem = items[selectedItemIndex]
            val newTheme = if (selectedItem == "Modern") "AppTheme" else "AppTheme.Dark"
            sharedPreferences.edit().putString("theme", newTheme).apply()
            recreate()
        }

        builder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.dismiss()
        }

        val dialog = builder.create()
        dialog.show()
    }


    private fun initTheme(){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val theme = sharedPreferences.getString("theme", "AppTheme")
        if (theme == "AppTheme.Dark") {
            setTheme(R.style.AppTheme_Dark)
        } else {
            setTheme(R.style.AppTheme)
        }
    }

    private fun initCurrentSelectionTheme(){
        val currentTheme = sharedPreferences.getString("theme", "AppTheme")
        selectedItemIndex = if (currentTheme == "AppTheme") {
            items.indexOf("Modern")
        } else {
            items.indexOf("Aesthetic")
        }
    }
}

