package com.pavan.newsapp.model

import com.google.gson.annotations.SerializedName

data class Newsresponse(
    val articleslist:List<articles>?=null

)
data class articles(
    val title: String,
    val description: String,
    val url:String,
    @SerializedName("UrltoImage")
    val imageUrl:String
)
