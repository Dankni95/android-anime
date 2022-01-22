package com.watch.anime

import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso


class EpisodeAdapter(private val listener: OnItemClickListener, private val exampleList: List<Items>) :
    RecyclerView.Adapter<EpisodeAdapter.EpisodeViewHolder>() {
    var mLastClickTime: Long = 0


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodeViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.example_item,
            parent, false)

        return EpisodeViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: EpisodeViewHolder, position: Int) {
        val currentItem = exampleList[position]

        Picasso.get()
            .load(currentItem.imageResource)
            .resize(100, 100)
            .centerCrop()
            .into(holder.imageView)


        holder.textView1.text = currentItem.text1
        holder.textView2.text = currentItem.text2
    }

    override fun getItemCount() = exampleList.size


    inner class EpisodeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
    View.OnClickListener {
        val imageView: ImageView = itemView.findViewById(R.id.mtrl_list_item_icon)
        val textView1: TextView = itemView.findViewById(R.id.mtrl_list_item_text)
        val textView2: TextView = itemView.findViewById(R.id.mtrl_list_item_secondary_text)



        init {
            itemView.setOnClickListener(this)
        }


        override fun onClick(v: View?) {


            // mis-clicking prevention, using threshold of 1000 ms
            if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
                return;
            }
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(position, exampleList)
            }
            mLastClickTime = SystemClock.elapsedRealtime();

        }
    }

interface OnItemClickListener {
        fun onItemClick(position: Int,exampleList:List<Items>)
    }
}
