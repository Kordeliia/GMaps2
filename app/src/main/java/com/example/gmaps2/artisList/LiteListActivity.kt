package com.example.gmaps2.artisList

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gmaps2.common.dataAccess.FakeDatabase
import com.example.gmaps2.databinding.ActivityLiteListBinding

class LiteListActivity : AppCompatActivity() {
    private lateinit var binding : ActivityLiteListBinding
    private lateinit var adapter: ArtistAdapter
    private val recyclerViewListener = RecyclerView.RecyclerListener {holder ->
        (holder as ArtistAdapter.ViewHolder).clearMap()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLiteListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupRecyclerView()
        getArtists()
    }

    private fun setupRecyclerView() {
        adapter = ArtistAdapter()
        binding.recycleView.apply {
            layoutManager = LinearLayoutManager(this@LiteListActivity)
            adapter = this@LiteListActivity.adapter
            setHasFixedSize(true)
            setRecyclerListener(recyclerViewListener)
        }
    }

    private fun getArtists() {
        FakeDatabase.getArtists(this)?.let{
            adapter.submitList(it)
        }
    }
}