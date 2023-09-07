package com.example.finalproject

import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml
import retrofit2.http.Body

@Xml(name = "rfcOpenApi")
data class responseInfo(
    @Element
    val header : Header,
    @Element
    val body : myBody,
)
@Xml(name="header")
data class Header(
    @PropertyElement
    val resultCode : Int,
    @PropertyElement
    val resultMsg : String
)
@Xml(name="body")
data class myBody(
    @PropertyElement
    val pageIndex : Int,
    @PropertyElement
    val pageSize : Int,
    @PropertyElement
    val startPage : Int,
    @PropertyElement
    val totalCount : Int,
    @Element
    val data : myDatas
)
@Xml(name="data")
data class myDatas(
    @Element
    val list : MutableList<mylists>
)
@Xml(name="list")
data class mylists(
    @PropertyElement
    val uiryeongsingleparentAddr : String?,
    @PropertyElement
    val uiryeongsingleparentEntId : Int?,
    @PropertyElement
    val uiryeongsingleparentPeriod : String?,
    @PropertyElement
    val uiryeongsingleparentQuota : String?,
    @PropertyElement
    val uiryeongsingleparentRegDt : String?,
    @PropertyElement
    val uiryeongsingleparentTarget : String?,
    @PropertyElement
    val uiryeongsingleparentTitle : String?,
    @PropertyElement
    val uiryeongsingleparentType : String?
){
    constructor() : this(null, null, null, null, null, null, null, null)
}
