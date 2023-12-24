package com.pavan.newsapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import com.pavan.newsapp.MyResponse
import com.pavan.newsapp.MyResponse.Status.*
import com.pavan.newsapp.R
import com.pavan.newsapp.adapter
import com.pavan.newsapp.databinding.ActivityMainBinding
import com.pavan.newsapp.viewmodel.NewsViewmodel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var _binding : ActivityMainBinding?= null
    private val binding get() = _binding
    private val viewmodel : NewsViewmodel by viewModels()

    @Inject
    lateinit var newsapdapter : adapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        SetupViews()
        observenewsdata()
        viewmodel.getallnotes()
    }

    private fun observenewsdata() {
        viewmodel.newsdata.observe(this@MainActivity ){response ->
            when(response.status){

                MyResponse.Status.LOADING -> {
                    binding?.spinkitview?.visibility = View.VISIBLE
                }

                MyResponse.Status.SUCCESS -> {
                    binding?.spinkitview?.visibility = View.GONE
                    response?.data?.articleslist?.let {
                        newsapdapter.submitdata(it)
                    }
                }

                MyResponse.Status.ERROR -> {
                    binding?.spinkitview?.visibility  = View.GONE
                    Toast.makeText(this@MainActivity,response.message,Toast.LENGTH_SHORT).show()
                    Log.e("Mainactivity", response.message.toString())
                }
            }
        }
    }

    private fun SetupViews(){
        binding?.recyclerview?.apply {
            layoutManager = LinearLayoutManager(this@MainActivity,LinearLayoutManager.VERTICAL,false)
            adapter = newsapdapter
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}