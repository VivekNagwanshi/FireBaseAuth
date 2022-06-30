package com.example.firebaseauth

import android.media.session.MediaController
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.VideoView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide


class DeskBoardAdapter(private val mList: List<ItemDataItem>) :
    RecyclerView.Adapter<DeskBoardAdapter.ViewHolder>() {

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_view_item, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mList[position]
        val postImage = holder.imageView
        val dataImage = item.mediaFile
        if (item.mediaFile.toString().endsWith(".png") || item.mediaFile.toString().endsWith(".jpg")) {
            holder.imageView.visibility = View.VISIBLE
            Glide.with(postImage).load(dataImage).centerCrop().into(postImage)
        } else if (dataImage.toString().endsWith(".mp4")) {
            holder.videoView.visibility = View.VISIBLE
            val video: Uri = Uri.parse(dataImage.toString())
            holder.videoView.setVideoURI(video)
            holder.videoView.setOnPreparedListener { mp ->
                mp.isLooping = true
                holder.videoView.start()
            }
        }

        if (item.externallink != null && item.externallink.isNotEmpty()) {
            holder.hyperLink.text = item.externallink
            holder.hyperLink.visibility = View.VISIBLE
        } else {
            holder.hyperLink.visibility = View.GONE
        }
        if (item.heading != null && item.heading.isNotEmpty()) {
            holder.headLine.text = item.heading
            holder.headLine.visibility = View.VISIBLE
        } else {
            holder.headLine.visibility = View.GONE
        }


    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val imageView: ImageView = itemView.findViewById(R.id.ivImageview)
        val headLine: TextView = itemView.findViewById(R.id.tvHeading)
        val hyperLink: TextView = itemView.findViewById(R.id.tvHyperLink)
        val videoView: VideoView = itemView.findViewById(R.id.videoView)
    }
}