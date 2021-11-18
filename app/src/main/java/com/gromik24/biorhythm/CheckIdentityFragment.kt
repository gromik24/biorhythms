package com.gromik24.biorhythm

import android.app.Activity
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
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
import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.round
import kotlin.math.sin


private const val PHYSICAL_CYCLE_LENGTH = 23
private const val EMOTIONAL_CYCLE_LENGTH = 28
private const val INTELLECTUAL_CYCLE_LENGTH = 33

class CheckIdentityFragment : Fragment() {

    private lateinit var birthdate1: LocalDate
    private lateinit var birthdate2: LocalDate
    private var diffInDays: Long = 0

    private lateinit var birthdate1DatePicker: DatePicker
    private lateinit var birthdate2DatePicker: DatePicker

            @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_check_identity, container, false)

        birthdate1DatePicker = view.findViewById(R.id.birthdate1_date_picker)
        birthdate2DatePicker = view.findViewById(R.id.birthdate2_date_picker)

        val calculateIdentityButton: Button = view.findViewById(R.id.calculate_identity_button)

        val emotionalIdentityTextView: TextView =
            view.findViewById(R.id.emotional_identity_text_view)
        val intellectualIdentityTextView: TextView =
            view.findViewById(R.id.intellectual_identity_text_view)
        val physicalIdentityTextView: TextView = view.findViewById(R.id.physical_identity_text_view)

        if (savedInstanceState !=null) {
            birthdate1DatePicker.updateDate(savedInstanceState.getInt("birthdate1_year"),
                savedInstanceState.getInt("birthdate1_month"),
                savedInstanceState.getInt("birthdate1_day"))

            birthdate2DatePicker.updateDate(savedInstanceState.getInt("birthdate2_year"),
                savedInstanceState.getInt("birthdate2_month"),
                savedInstanceState.getInt("birthdate2_day"))
        }

        calculateIdentityButton.setOnClickListener()
        {

            birthdate1 = LocalDate.of(
                birthdate1DatePicker.year, birthdate1DatePicker.month,
                birthdate1DatePicker.dayOfMonth
            )

            birthdate2 = LocalDate.of(
                birthdate2DatePicker.year, birthdate2DatePicker.month,
                birthdate2DatePicker.dayOfMonth
            )
            diffInDays = abs(birthdate1.toEpochDay() - birthdate2.toEpochDay())

            calc_identity(physicalIdentityTextView, diffInDays, PHYSICAL_CYCLE_LENGTH)
            calc_identity(emotionalIdentityTextView, diffInDays, EMOTIONAL_CYCLE_LENGTH)
            calc_identity(intellectualIdentityTextView, diffInDays, INTELLECTUAL_CYCLE_LENGTH)

        }


        return view
    }

    private fun calc_identity(textView: TextView, difference: Long, cycleLength: Int) {
        val cycleDifference = difference.mod(cycleLength)

        val cycleIdentity = round((100 * abs(cycleDifference - cycleLength / 2.0) / cycleLength))

        textView.text = cycleIdentity.toString() + " %"

        if (cycleIdentity <= 40) textView.setBackgroundColor(textView.context.getColor(R.color.red))
        else textView.setBackgroundColor(textView.context.getColor(R.color.green))
    }

    override fun onSaveInstanceState(outState: Bundle) {

        outState.putInt("birthdate1_year", birthdate1DatePicker.year)
        outState.putInt("birthdate1_month", birthdate1DatePicker.month)
        outState.putInt("birthdate1_day", birthdate1DatePicker.dayOfMonth)

        outState.putInt("birthdate2_year", birthdate2DatePicker.year)
        outState.putInt("birthdate2_month", birthdate2DatePicker.month)
        outState.putInt("birthdate2_day", birthdate2DatePicker.dayOfMonth)

        super.onSaveInstanceState(outState)
    }


}