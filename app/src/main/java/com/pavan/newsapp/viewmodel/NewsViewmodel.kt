package com.pavan.newsapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pavan.newsapp.MyResponse
import com.pavan.newsapp.model.Newsresponse
import com.pavan.newsapp.repository.Newsrepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewmodel @Inject constructor (val newsrepository: Newsrepository) :ViewModel() {

    var newsdata  = MutableLiveData<MyResponse<Newsresponse>>()
    fun getallnotes() = viewModelScope.launch{
        newsrepository.lastnews().collect{
            newsdata.postValue(it)
        }
    }
}