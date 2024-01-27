package com.pavan.newsapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pavan.newsapp.Constants
import com.pavan.newsapp.MyResponse
import com.pavan.newsapp.model.Newsresponse
import com.pavan.newsapp.repository.Newsrepository
import com.pavan.newsapp.server.newsApiservice
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

@HiltViewModel
class NewsViewmodel @Inject constructor (val newsrepository: Newsrepository) :ViewModel() {

    var newsdata  = MutableLiveData<MyResponse<Newsresponse>>()
    fun getallnews() = viewModelScope.launch{
        newsrepository.lastnews().collect{
            newsdata.postValue(it)
//            println("Received data: $it")
        }




    }
}