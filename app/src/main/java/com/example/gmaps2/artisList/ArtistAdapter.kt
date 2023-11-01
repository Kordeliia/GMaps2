package com.example.gmaps2.artisList

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.gmaps2.R
import com.example.gmaps2.common.entities.Artist
import com.example.gmaps2.databinding.ItemLiteListBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class ArtistAdapter: ListAdapter<Artist, RecyclerView.ViewHolder>(ArtistDiffCallBack()) {
    private lateinit var context : Context
    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view), OnMapReadyCallback{
        val binding = ItemLiteListBinding.bind(view)
        lateinit var map : GoogleMap
        lateinit var location: LatLng
        init{
            with(binding.itemMap){
                onCreate(null)
            getMapAsync(this@ViewHolder)
            }
        }

        override fun onMapReady(gMap: GoogleMap) {
            MapsInitializer.initialize(context)
            map = gMap
            setupMapLocation()
        }

        fun setupMapLocation() {
            if(!::map.isInitialized) return
            map.run{
                moveCamera(CameraUpdateFactory.newLatLngZoom(location, 15f))
                addMarker(MarkerOptions().position(location))
                mapType = GoogleMap.MAP_TYPE_NORMAL
            }
        }

        fun clearMap(){
            map.run{
                clear()
                mapType = GoogleMap.MAP_TYPE_NONE
            }
        }
    }
    class ArtistDiffCallBack: DiffUtil.ItemCallback<Artist>(){
        override fun areItemsTheSame(oldItem: Artist, newItem: Artist) = oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Artist, newItem: Artist) = oldItem == newItem

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        context = parent.context
        val view = LayoutInflater.from(context).inflate(R.layout.item_lite_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val artist = getItem(position)
        with(holder as ViewHolder) {
            location = artist.birthLocation.getLocation()
            binding.itemMap.tag = this
            binding.tvName.text = artist.getFullName()
            Glide.with(context).load(artist.photoURL).diskCacheStrategy(DiskCacheStrategy.ALL)
                .circleCrop().into(binding.imgePhoto)
            setupMapLocation()
        }
    }


}