package com.perea.marc.soundbuttons

import android.media.MediaPlayer
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.widget.Button
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import com.google.firebase.analytics.FirebaseAnalytics
import android.util.StatsLog.logEvent

class MainActivity : AppCompatActivity(), OnSoundClickListener {

    var mediaPlayer: MediaPlayer? = null
    private var mFirebaseAnalytics: FirebaseAnalytics? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Analytics
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);


        // Admob
        MobileAds.initialize(this, "ca-app-pub-9379813552532416~5445620455")

        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)



        val jsonString = resources.openRawResource(R.raw.sounds).bufferedReader().use { it.readText() }


        val soundResponse = Gson().fromJson(jsonString, SoundResponse::class.java)

        soundsRecyclerview.layoutManager = GridLayoutManager(this, 3)
        soundResponse.sounds?.let {
            val adapter = SoundsAdapter(it)
            adapter.soundClickListener = this
            soundsRecyclerview.adapter = adapter
        }
    }


    override fun onSoundClick(sound: SoundModel) {
        mediaPlayer?.release()
        mediaPlayer = MediaPlayer.create(this, resources.getIdentifier(sound.title, "raw", packageName))
        mediaPlayer?.start()


        // Log Event
        val bundle = Bundle()
        bundle.putString("sound_title", sound.title)
        bundle.putInt("sound_id", sound.id ?: -1)

        mFirebaseAnalytics?.logEvent("sound_clicked", bundle)
    }


    override fun onStop() {
        super.onStop()
        mediaPlayer?.release()
        mediaPlayer = null
    }
}
