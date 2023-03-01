package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.InputFilter
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    private val sharedPrefFile = "myapplication"
    private lateinit var hwButton: Button
    private lateinit var calculateTotalButton: Button
    private lateinit var resetButton: Button
    private lateinit var hwAverage: EditText
    private lateinit var participationGrade: EditText
    private lateinit var gpGrade: EditText
    private lateinit var m1Grade: EditText
    private lateinit var m2Grade: EditText
    private lateinit var finalProjectGrade: EditText
    private lateinit var totalGrade: TextView

    @SuppressLint("ResourceType", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val sharedPreferences: SharedPreferences = this.getSharedPreferences(
            sharedPrefFile,
            Context.MODE_PRIVATE
        )

        //Initializing variables
        hwButton = findViewById(R.id.homeworkButton)
        calculateTotalButton = findViewById(R.id.calculate_total_button)
        resetButton = findViewById(R.id.reset_button)
        hwAverage = findViewById(R.id.hw_average)
        participationGrade = findViewById(R.id.participation_grade)
        gpGrade = findViewById(R.id.gp_grade)
        m1Grade = findViewById(R.id.midterm1_grade)
        m2Grade = findViewById(R.id.midterm2_grade)
        finalProjectGrade = findViewById(R.id.final_project_grade)
        totalGrade = findViewById(R.id.total_grade)

        //Grades should be between 0 and 100
        val filter = InputFilterMinMax("0", "100")
        hwAverage.filters = arrayOf(filter, InputFilter.LengthFilter(3))
        participationGrade.filters = arrayOf(filter, InputFilter.LengthFilter(3))
        gpGrade.filters = arrayOf(filter, InputFilter.LengthFilter(3))
        m1Grade.filters = arrayOf(filter, InputFilter.LengthFilter(3))
        m2Grade.filters = arrayOf(filter, InputFilter.LengthFilter(3))
        finalProjectGrade.filters = arrayOf(filter, InputFilter.LengthFilter(3))

        val hwAverageVal = sharedPreferences.getString("hwAverage", "Homework Average")
        val participationGradeVal = sharedPreferences.getString("participationGrade", "")
        val gpGradeVal = sharedPreferences.getString("gpGrade", "")
        val m1GradeVal = sharedPreferences.getString("m1Grade", "")
        val m2GradeVal = sharedPreferences.getString("m2Grade", "")
        val finalProjectGradeVal = sharedPreferences.getString("finalProjectGrade", "")
        val totalGradeVal = sharedPreferences.getString("totalGrade", "Total grade")

        if (hwAverageVal != "Homework Average") {
            hwAverage.setText(hwAverageVal)
        } else {
            var averageGrade = ""
            val bundle = intent.extras
            if (bundle != null) {
                averageGrade = bundle.getString("hw_average").toString()
            }
            if (averageGrade.isNotBlank()) {
                hwAverage.setText(averageGrade)
            }
        }

        if (participationGradeVal != null) {
            if (!participationGradeVal.isBlank()) {
                participationGrade.setText(participationGradeVal)
            }
        }

        if (gpGradeVal != null) {
            if (gpGradeVal.isNotBlank()) {
                gpGrade.setText(gpGradeVal)
            }
        }

        if (m1GradeVal != null) {
            if (m1GradeVal.isNotBlank()) {
                m1Grade.setText(m1GradeVal)
            }
        }
        if (m2GradeVal != null) {
            if (m2GradeVal.isNotBlank()) {
                m2Grade.setText(m2GradeVal)
            }
        }
        if (finalProjectGradeVal != null) {
            if (finalProjectGradeVal.isNotBlank()) {
                finalProjectGrade.setText(finalProjectGradeVal)
            }
        }
        if (totalGradeVal != null) {
            if (totalGradeVal.isNotBlank()) {
                totalGrade.text = totalGradeVal
            }
        }

        hwButton.setOnClickListener {
            // Handler code here.
            val intent = Intent(this, HomeworkActivity::class.java)
            val hw1val = sharedPreferences.getString("hw1grade", "")
            val hw2val = sharedPreferences.getString("hw2grade", "")
            val hw3val = sharedPreferences.getString("hw3grade", "")
            val hw4val = sharedPreferences.getString("hw4grade", "")
            val hw5val = sharedPreferences.getString("hw5grade", "")

            intent.putExtra("hw1grade", hw1val.toString())
            intent.putExtra("hw2grade", hw2val.toString())
            intent.putExtra("hw3grade", hw3val.toString())
            intent.putExtra("hw4grade", hw4val.toString())
            intent.putExtra("hw5grade", hw5val.toString())
            startActivity(intent)
        }

        calculateTotalButton.setOnClickListener {
            val total = calculateAverage()
            totalGrade.text = total.toString()

            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            editor.putString("hwAverage", hwAverage.text.toString())
            editor.putString("participationGrade", participationGrade.text.toString())
            editor.putString("gpGrade", gpGrade.text.toString())
            editor.putString("m1Grade", m1Grade.text.toString())
            editor.putString("m2Grade", m2Grade.text.toString())
            editor.putString("finalProjectGrade", finalProjectGrade.text.toString())
            editor.putString("totalGrade", totalGrade.text.toString())
            editor.apply()
        }
        resetButton.setOnClickListener {
            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            editor.clear()
            editor.apply()
            hwAverage.setText("")
            m1Grade.setText("")
            m2Grade.setText("")
            finalProjectGrade.setText("")
            gpGrade.setText("")
            participationGrade.setText("")
            totalGrade.text = "Total Grade"
        }
    }

    @SuppressLint("SetTextI18n")
    private fun calculateAverage(): Float {
        if (participationGrade.text.isBlank()) {
            participationGrade.setText("100")
        }
        if (gpGrade.text.isBlank()) {
            gpGrade.setText("100")
        }
        if (m1Grade.text.isBlank()) {
            m1Grade.setText("100")
        }
        if (m2Grade.text.isBlank()) {
            m2Grade.setText("100")
        }
        if (finalProjectGrade.text.isBlank()) {
            finalProjectGrade.setText("100")
        }
        if (hwAverage.text.isBlank()) {
            hwAverage.setText("100")
        }

        return Integer.parseInt(participationGrade.text.toString()) * 0.1f +
                Integer.parseInt(hwAverage.text.toString()) * 0.2f +
                Integer.parseInt(gpGrade.text.toString()) * 0.1f +
                Integer.parseInt(m1Grade.text.toString()) * 0.1f +
                Integer.parseInt(m2Grade.text.toString()) * 0.2f +
                Integer.parseInt(finalProjectGrade.text.toString()) * 0.3f
    }
}