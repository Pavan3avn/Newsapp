package com.pavan.newsapp.repository

import com.pavan.newsapp.Constants.TOKEN
import com.pavan.newsapp.MyResponse
import com.pavan.newsapp.model.Newsresponse
import com.pavan.newsapp.server.newsApiservice
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class Newsrepository @Inject constructor(val apiservice: newsApiservice) {

    suspend fun lastnews() : Flow<MyResponse<Newsresponse>> = flow{
        emit(MyResponse.loading())
        val response = apiservice.getallarticles("de","business",TOKEN)

        if(response.isSuccessful){
           emit(MyResponse.success(response.body()))
        }else{
            emit(MyResponse.error("please try again later"))
        }
    }.catch {

        emit(MyResponse.error(it.message.toString()))
    }


}