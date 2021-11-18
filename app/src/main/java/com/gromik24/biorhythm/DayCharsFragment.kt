package com.gromik24.biorhythm

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.DatePicker
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.Period
import java.util.*
import kotlin.math.PI
import kotlin.math.round
import kotlin.math.sin

private const val PHYSICAL_CYCLE_LENGTH = 23
private const val EMOTIONAL_CYCLE_LENGTH = 28
private const val INTELLECTUAL_CYCLE_LENGTH = 33


class DayCharsFragment : Fragment() {

    private lateinit var birthdate: LocalDate
    private lateinit var baseDate: LocalDate
    private var ageInDays: Long = 0

    private lateinit var birthdateDatePicker: DatePicker
    private lateinit var baseDateDatePicker: DatePicker

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_day_chars, container, false)

        birthdateDatePicker = view.findViewById(R.id.birthdate_date_picker)
        baseDateDatePicker = view.findViewById(R.id.base_date_date_picker)

        val calculateLevelsButton: Button = view.findViewById(R.id.calculate_levels_button)
        val shareButton: Button = view.findViewById(R.id.share_button)

        val emotionalLevelTextView: TextView = view.findViewById(R.id.emotional_level_text_view)
        val intellectualLevelTextView: TextView =
            view.findViewById(R.id.intellectual_level_text_view)
        val physicalLevelTextView: TextView = view.findViewById(R.id.physical_level_text_view)

        if (savedInstanceState != null) {

            Log.d("TAG","заполнение дат")
            birthdateDatePicker.updateDate(
                savedInstanceState.getInt("birthdate_year"),
                savedInstanceState.getInt("birthdate_month"),
                savedInstanceState.getInt("birthdate_day")
            )

            baseDateDatePicker.updateDate(
                savedInstanceState.getInt("baseDate_year"),
                savedInstanceState.getInt("baseDate_month"),
                savedInstanceState.getInt("baseDate_day")
            )
        }


        calculateLevelsButton.setOnClickListener()
        {

            birthdate = LocalDate.of(
                birthdateDatePicker.year, birthdateDatePicker.month,
                birthdateDatePicker.dayOfMonth
            )

            baseDate = LocalDate.of(
                baseDateDatePicker.year, baseDateDatePicker.month,
                baseDateDatePicker.dayOfMonth
            )
            ageInDays = baseDate.toEpochDay() - birthdate.toEpochDay()

            if (ageInDays < 0) Toast.makeText(
                view.context,
                "Дата рождения должна быть раньше даты расчета",
                Toast.LENGTH_SHORT
            ).show()
            else {

                calc_biorhythm(physicalLevelTextView, ageInDays, PHYSICAL_CYCLE_LENGTH)
                calc_biorhythm(emotionalLevelTextView, ageInDays, EMOTIONAL_CYCLE_LENGTH)
                calc_biorhythm(intellectualLevelTextView, ageInDays, INTELLECTUAL_CYCLE_LENGTH)
            }


        }

        shareButton.setOnClickListener() {
            if (physicalLevelTextView.text == "")
                Toast.makeText(
                    view.context,
                    "Сначала нажмите кнопку расчета биоритмов",
                    Toast.LENGTH_SHORT
                ).show()
            else {

                val shareIntent = Intent()
                shareIntent.action = Intent.ACTION_SEND

                val textToSend: String = "Мои биоритмы сегодня\n" +
                        it.context.getString(R.string.physical) + ": " + physicalLevelTextView.text + "\n" +
                        it.context.getString(R.string.emotional) + ": " + emotionalLevelTextView.text + "\n" +
                        it.context.getString(R.string.intellectual) + ": " + intellectualLevelTextView.text

                shareIntent.putExtra(Intent.EXTRA_TEXT, textToSend)
                shareIntent.type = "text/plain"
                startActivity(Intent.createChooser(shareIntent, "send to"))
            }
        }
        return view
    }

    private fun calc_biorhythm(textView: TextView, age: Long, cycleLength: Int) {
        val cycleDay = age.mod(cycleLength)
        val currentLevel = round(100 * sin(2 * PI * age / cycleLength)).toInt()
        textView.text = "\n день цикла: " + cycleDay.toString() +
                "\n уровень: " + currentLevel.toString() + " %"
        if (currentLevel <= 0) textView.setBackgroundColor(textView.context.getColor(R.color.red))
        else textView.setBackgroundColor(textView.context.getColor(R.color.green))
    }

    override fun onSaveInstanceState(outState: Bundle) {
        Log.d("TAG","сохранение дат")
        outState.putInt("birthdate_year", birthdateDatePicker.year)
        outState.putInt("birthdate_month", birthdateDatePicker.month)
        outState.putInt("birthdate_day", birthdateDatePicker.dayOfMonth)

        outState.putInt("baseDate_year", baseDateDatePicker.year)
        outState.putInt("baseDate_month", baseDateDatePicker.month)
        outState.putInt("baseDate_day", baseDateDatePicker.dayOfMonth)

        super.onSaveInstanceState(outState)
    }

}


