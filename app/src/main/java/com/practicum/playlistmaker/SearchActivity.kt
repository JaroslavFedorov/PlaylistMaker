package com.practicum.playlistmaker

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import com.google.android.material.appbar.MaterialToolbar

class SearchActivity : AppCompatActivity() {

    private lateinit var searchEditText: EditText
    private lateinit var clearButton: ImageView
    private lateinit var toolbar: MaterialToolbar

    private var currentSearchText: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        searchEditText = findViewById(R.id.search_edit_text)
        clearButton = findViewById(R.id.clear_search_button)
        toolbar = findViewById(R.id.search_toolbar)

        // Устанавливаем Toolbar как ActionBar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        // Программно устанавливаем цвет стрелки назад
        toolbar.navigationIcon?.setTint(
            resources.getColor(R.color.colorNavigationIcon, theme)
        )

        // Клик по стрелке назад
        toolbar.setNavigationOnClickListener {
            finish()
        }

        // Скрываем кнопку очистки по умолчанию
        clearButton.visibility = View.GONE

        // Отслеживаем изменения текста в поле поиска
        searchEditText.doOnTextChanged { text, _, _, _ ->
            currentSearchText = text.toString()
            clearButton.visibility = if (text.isNullOrEmpty()) View.GONE else View.VISIBLE
        }

        // Обработка клика по кнопке очистки
        clearButton.setOnClickListener {
            searchEditText.text.clear()
            clearButton.visibility = View.GONE
            hideKeyboard()
            searchEditText.clearFocus()
            currentSearchText = ""
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("search_text", currentSearchText)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val restoredText = savedInstanceState.getString("search_text", "")
        currentSearchText = restoredText
        searchEditText.setText(restoredText)
        clearButton.visibility = if (restoredText.isEmpty()) View.GONE else View.VISIBLE
    }

    private fun hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(searchEditText.windowToken, 0)
    }
}

