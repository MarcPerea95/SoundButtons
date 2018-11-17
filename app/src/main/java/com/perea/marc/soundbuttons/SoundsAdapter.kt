package com.perea.marc.soundbuttons

import android.media.MediaPlayer
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import kotlinx.android.synthetic.main.row_sound.view.*



class SoundsAdapter(val soundList: ArrayList<SoundModel>): RecyclerView.Adapter<SoundsAdapter.SoundsViewHolder>() {

    var soundClickListener: OnSoundClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SoundsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_sound, parent, false)
        return SoundsViewHolder(view)
    }

    override fun getItemCount(): Int {
        return soundList.size
    }

    override fun onBindViewHolder(viewHolder: SoundsViewHolder, position: Int) {
        val sound = soundList[position]

        // Set Title
        viewHolder.soundTitle.text = sound.title
        // Set Button
        viewHolder.soundButton.setOnClickListener {
            soundClickListener?.onSoundClick(sound)
        }
    }

    class SoundsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var soundButton: Button = itemView.soundButton
        var soundTitle: TextView = itemView.soundTitle

    }
}