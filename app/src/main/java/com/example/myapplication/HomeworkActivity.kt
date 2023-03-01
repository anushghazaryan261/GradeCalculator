package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity


class HomeworkActivity : AppCompatActivity() {
    private val sharedPrefFile = "myapplication"
    private lateinit var backToMainButton: Button
    private lateinit var hw1grade: EditText
    private lateinit var hw2grade: EditText
    private lateinit var hw3grade: EditText
    private lateinit var hw4grade: EditText
    private lateinit var hw5grade: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homeworks)

        val sharedPreferences: SharedPreferences = this.getSharedPreferences(
            sharedPrefFile,
            Context.MODE_PRIVATE
        )
        hw1grade = findViewById(R.id.hw1grade)
        hw2grade = findViewById(R.id.hw2grade)
        hw3grade = findViewById(R.id.hw3grade)
        hw4grade = findViewById(R.id.hw4grade)
        hw5grade = findViewById(R.id.hw5grade)
        backToMainButton = findViewById(R.id.back_to_main)
        val filter = InputFilterMinMax("0", "100")
        hw1grade.filters = arrayOf(filter, InputFilter.LengthFilter(3))
        hw2grade.filters = arrayOf(filter, InputFilter.LengthFilter(3))
        hw3grade.filters = arrayOf(filter, InputFilter.LengthFilter(3))
        hw4grade.filters = arrayOf(filter, InputFilter.LengthFilter(3))
        hw5grade.filters = arrayOf(filter, InputFilter.LengthFilter(3))

        var hw1text = ""
        var hw2text = ""
        var hw3text = ""
        var hw4text = ""
        var hw5text = ""
        val bundle = intent.extras
        if (bundle != null) {
            hw1text = bundle.getString("hw1grade").toString()
            hw2text = bundle.getString("hw2grade").toString()
            hw3text = bundle.getString("hw3grade").toString()
            hw4text = bundle.getString("hw4grade").toString()
            hw5text = bundle.getString("hw5grade").toString()
        }
        if (hw1text.isNotBlank()) {
            hw1grade.setText(hw1text)
        }
        if (hw2text.isNotBlank()) {
            hw2grade.setText(hw2text)
        }
        if (hw3text.isNotBlank()) {
            hw3grade.setText(hw3text)
        }
        if (hw4text.isNotBlank()) {
            hw4grade.setText(hw4text)
        }
        if (hw5text.isNotBlank()) {
            hw5grade.setText(hw5text)
        }

        backToMainButton.setOnClickListener {
            // Handler code here.
            val intent = Intent(this, MainActivity::class.java)
            val average = calculateAverage()
            intent.putExtra("hw_average", average.toString())
            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            editor.putString("hw1grade", hw1grade.text.toString())
            editor.putString("hw2grade", hw2grade.text.toString())
            editor.putString("hw3grade", hw3grade.text.toString())
            editor.putString("hw4grade", hw4grade.text.toString())
            editor.putString("hw5grade", hw5grade.text.toString())
            editor.apply()
            startActivity(intent)
        }
    }

    @SuppressLint("SetTextI18n")
    fun calculateAverage(): Int {
        if (hw1grade.text.isBlank()) {
            hw1grade.setText("100")
        }
        if (hw2grade.text.isBlank()) {
            hw2grade.setText("100")
        }
        if (hw3grade.text.isBlank()) {
            hw3grade.setText("100")
        }
        if (hw4grade.text.isBlank()) {
            hw4grade.setText("100")
        }
        if (hw5grade.text.isBlank()) {
            hw5grade.setText("100")
        }
        val sum = Integer.parseInt(hw1grade.text.toString()) +
                Integer.parseInt(hw2grade.text.toString()) +
                Integer.parseInt(hw3grade.text.toString()) +
                Integer.parseInt(hw4grade.text.toString()) +
                Integer.parseInt(hw5grade.text.toString())
        return sum / 5
    }
}