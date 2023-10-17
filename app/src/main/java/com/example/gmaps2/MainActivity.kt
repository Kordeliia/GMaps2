package com.example.gmaps2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import com.example.gmaps2.databinding.ActivityMainBinding

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
                R.id.btnPMap -> {
                    startActivity(Intent(this, FirstMapActivity::class.java))
                }
                R.id.btnNewProcessor -> {
                    startActivity(Intent(this, NewProcessorActivity::class.java))
                }
                R.id.btnMapThree -> {
                    startActivity(Intent(this, MapThreeActivity::class.java))
                }
                //Botones menu
                
                R.id.btnArtist -> {
                    startActivity(Intent(this, FirstMapActivity::class.java))
                }
                R.id.btnLiteList -> {
                    startActivity(Intent(this, FirstMapActivity::class.java))
                }
                R.id.btnFormMap -> {
                    startActivity(Intent(this, FirstMapActivity::class.java))
                }
            }
        }
    }
}