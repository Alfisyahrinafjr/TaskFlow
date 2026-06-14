package com.example.taskflow.data.remote.dto

import com.google.gson.annotations.SerializedName

data class HolidayResponse(
    @SerializedName("holiday_date")
    val holidayDate: String,
    @SerializedName("holiday_name")
    val holidayName: String,
    @SerializedName("is_national_holiday")
    val isNationalHoliday: Boolean
)