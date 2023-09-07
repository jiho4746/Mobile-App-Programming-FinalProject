package com.example.finalproject

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NetworkService {
    @GET("rest/uiryeongsingleparent/getUiryeongsingleparentList?")
    fun getXmlList(
        @Query("serviceKey") apiKey: String?,
        @Query("numOfRows") pageSize:Int,
        @Query("pageNo") page:Int
    ):Call<responseInfo>
}