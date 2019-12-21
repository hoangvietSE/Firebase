package com.soict.hoangviet.firebase.widget

import android.app.DatePickerDialog
import android.content.Context
import android.widget.DatePicker
import com.soict.hoangviet.firebase.R
import java.util.*

class DatePickerDialogWidget(val context: Context, val mListener: DatePickerListener) :
    DatePickerDialog.OnDateSetListener {
    private val mCalendar: Calendar = Calendar.getInstance(Locale.getDefault())
    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        mListener.onDateSet(year, month + 1, dayOfMonth)
    }

    fun showDatePickerDialog() {
        val mDatePickerDialog = DatePickerDialog(
            context,
            R.style.DatePickerTheme,
            this,
            mCalendar.get(Calendar.YEAR),
            mCalendar.get(Calendar.MONTH),
            mCalendar.get(Calendar.DAY_OF_MONTH)
        )
        mDatePickerDialog.show()
    }

    interface DatePickerListener {
        fun onDateSet(year: Int, month: Int, dayOfMonth: Int)
    }
}