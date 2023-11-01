package com.example.gmaps2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import com.example.gmaps2.activities.CameraViewActivity
import com.example.gmaps2.activities.ControlGesturesActivity
import com.example.gmaps2.activities.EventsActivity
import com.example.gmaps2.activities.FirstMapActivity
import com.example.gmaps2.activities.MapThreeActivity
import com.example.gmaps2.activities.MarkersActivity
import com.example.gmaps2.activities.MyLocationActivity
import com.example.gmaps2.activities.NewProcessorActivity
import com.example.gmaps2.activities.PolylineActivity
import com.example.gmaps2.aristForm.FormActivity
import com.example.gmaps2.artisList.LiteListActivity
import com.example.gmaps2.artistMap.ArtistMapActivity
import com.example.gmaps2.databinding.ActivityMainBinding
import com.example.gmaps2.modes.LiteModeActivity

class MainActivity : AppCompatActivity(), OnClickListener {
    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        with(binding){
            //Botones cuerpo
            btnPMap.setOnClickListener(this@MainActivity)
            btnNewProcessor.setOnClickListener(this@MainActivity)
            btnMapThree.setOnClickListener(this@MainActivity)
            btnPMapFour.setOnClickListener(this@MainActivity)
            btnPMapFive.setOnClickListener(this@MainActivity)
            btnPMapSix.setOnClickListener(this@MainActivity)
            btnMapML.setOnClickListener(this@MainActivity)
            btnMapM.setOnClickListener(this@MainActivity)
            btnPolyline.setOnClickListener(this@MainActivity)
            btnLite.setOnClickListener(this@MainActivity)
            ///Botones menu
            btnArtist.setOnClickListener(this@MainActivity)
            btnLiteList.setOnClickListener(this@MainActivity)
            btnFormMap.setOnClickListener(this@MainActivity)
            }
        }
    override fun onClick(view: View?) {
        view?.let{
            when(it.id){
                //Botones cuerpo
                R.id.btnPMap ->
                    startActivity(Intent(this, FirstMapActivity::class.java))
                R.id.btnNewProcessor ->
                    startActivity(Intent(this, NewProcessorActivity::class.java))
                R.id.btnMapThree ->
                    startActivity(Intent(this, MapThreeActivity::class.java))
                R.id.btnPMapFour ->
                    startActivity(Intent(this, CameraViewActivity::class.java))
                R.id.btnPMapFive ->
                    startActivity(Intent(this, ControlGesturesActivity::class.java))
                R.id.btnPMapSix ->
                    startActivity(Intent(this, EventsActivity::class.java))
                R.id.btnMapML ->
                    startActivity(Intent(this, MyLocationActivity::class.java))
                R.id.btnMapM ->
                    startActivity(Intent(this, MarkersActivity::class.java))
                R.id.btnPolyline ->
                    startActivity(Intent(this, PolylineActivity::class.java))
                R.id.btnLite ->
                    startActivity(Intent(this, LiteModeActivity::class.java))
                //Botones menu
                R.id.btnArtist ->
                    startActivity(Intent(this, ArtistMapActivity::class.java))
                R.id.btnLiteList ->
                    startActivity(Intent(this, LiteListActivity::class.java))
                R.id.btnFormMap ->
                    startActivity(Intent(this, FormActivity::class.java))

            }
        }
    }
}