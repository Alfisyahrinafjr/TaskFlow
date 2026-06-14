package com.example.taskflow.data.remote.api

import com.example.taskflow.data.remote.dto.HolidayResponse
import retrofit2.http.GET

interface HolidayApiService {
    @GET("get-holiday")
    suspend fun getNationalHolidays(): List<HolidayResponse>
}