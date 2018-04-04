package com.fesskiev.kotlinsamples.ui.top

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fesskiev.kotlinsamples.R
import com.fesskiev.kotlinsamples.domain.entity.Track
import kotlinx.android.synthetic.main.item_track.view.*

class TopTracksAdapter : RecyclerView.Adapter<TopTracksAdapter.ViewHolder>() {

    private var tracks: MutableList<Track> = ArrayList()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindTopTrack(track: Track) {
            with(track) {
                itemView.trackName.text = name
                itemView.trackUrl.text = url
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = parent.inflate(R.layout.item_track)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int = tracks.size

    override fun getItemId(position: Int) = tracks[position].hashCode().toLong()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindTopTrack(tracks[position])
    }

    fun refresh(tracks: List<Track>) {
        this.tracks.clear()
        this.tracks.addAll(tracks)
        notifyDataSetChanged()
    }
}

private fun ViewGroup.inflate(layoutRes: Int): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, false)
}
