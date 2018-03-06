package com.example.pibbou.afca.ui.favorites

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.example.pibbou.afca.App
import com.example.pibbou.afca.R
import com.example.pibbou.afca.ui.base.BaseActivity
import com.example.pibbou.afca.ui.list.FavoriteListAdapter

class FavoritesActivity : BaseActivity() {

    // Prepare eventsByDay array
    private lateinit var recycler_view_favorite_list: RecyclerView
    private var favoriteAdapter: FavoriteListAdapter? = null

    // Favorite manager call
    val favoriteManager = App.sInstance.getFavoriteManager()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Setup List
        this.setupFavoriteList()
    }

    override fun onResume() {
        super.onResume()
        favoriteAdapter?.notifyDataSetChanged()
    }

    override fun provideParentLayoutId(): Int {
        return R.layout.activity_favorites
    }

    override fun setParentLayout(): View {
        return findViewById<View>(R.id.parentPanel) as View
    }

    private fun setupFavoriteList() {
        // Get recyclerview View
        recycler_view_favorite_list = findViewById<View>(R.id.recycler_view_favorite_list) as RecyclerView

        // Set fixed size
        recycler_view_favorite_list.setHasFixedSize(true)


        // Prepare adapter
        favoriteAdapter = FavoriteListAdapter(applicationContext, favoriteManager?.currentFavorites)

        recycler_view_favorite_list.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)

        // Set adapter
        recycler_view_favorite_list.adapter = favoriteAdapter
    }
}