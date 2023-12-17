package com.pavan.newsapp

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pavan.newsapp.databinding.NewsItemBinding
import com.pavan.newsapp.model.articles
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject

class adapter @Inject constructor(@ActivityContext private val context: Context): RecyclerView.Adapter<adapter.viewholder>() {
    private lateinit var  binding: NewsItemBinding
    private var newsList = emptyList<articles>()


    inner class viewholder:RecyclerView.ViewHolder(binding.root) {

        fun setdata(data:articles){
            binding.apply {
                newstitle.text = data.title
                newsdescription.text = ShortenString(data.description)
                Glide.with(context)
                    .load(data.imageUrl)
                    .error(R.drawable.newspaper)
                    .placeholder(R.drawable.newspaper)
                    .into(newsimage)


            }
        }

    }

    private fun ShortenString(input: String?): String {

        if(input != null){
            return if(input.length > 100){
                    val shortendstring = input.substring(0,100)
                    val lastSpaceindex = shortendstring.lastIndexOf(' ')
                    if(lastSpaceindex != -1){
                        shortendstring.substring(0,lastSpaceindex)+" ..."
                    }else{
                        "$shortendstring..."
                    }
               }else{
                   input
            }
        }else{
            return ""
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewholder {
        binding = NewsItemBinding.inflate(LayoutInflater.from(context),parent,false)
        return viewholder()
    }

    override fun getItemCount(): Int {
        return newsList.size
    }
    override fun getItemViewType(position:Int): Int {
        return position
    }


    override fun onBindViewHolder(holder: viewholder, position: Int) {
        holder.setdata(newsList[position])
    }
    fun submitdata(data: List<articles>){
        val newsdiffUtil = NewsDiffutils(newsList,data)
        val diffutils = DiffUtil.calculateDiff(newsdiffUtil)
        newsList = data
        diffutils.dispatchUpdatesTo(this)
    }
    class  NewsDiffutils
        (
        private val olditem : List<articles>,
        private val newitem : List<articles>): DiffUtil.Callback() {
        override fun getOldListSize(): Int {

            return olditem.size
        }

        override fun getNewListSize(): Int {
                return newitem.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return olditem[oldItemPosition] === newitem[newItemPosition]
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return  olditem[oldItemPosition] === newitem[newItemPosition]
        }


    }

}