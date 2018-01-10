package com.example.pibbou.afca.ui.list

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.example.pibbou.afca.R
import com.example.pibbou.afca.repository.entity.Category
import com.example.pibbou.afca.repository.entity.Event
import java.util.ArrayList

/**
 * Created by arnaudpinot on 07/01/2018.
 */

class CategoryListAdapter(private val mContext: Context, private val eventList: ArrayList<Event>?) : RecyclerView.Adapter<CategoryListAdapter.ItemRowHolder>() {

    // Prepare categories used by events
    private var activeCategories: ArrayList<Category>?  = ArrayList()
    // Prepare categories used by events
    private var filteredCategories: List<Category>? = ArrayList()

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ItemRowHolder {
        val v = LayoutInflater.from(viewGroup.context).inflate(R.layout.list_category, null)
        return ItemRowHolder(v)
    }

    override fun onBindViewHolder(itemRowHolder: ItemRowHolder, i: Int) {

        // Get section name (category name)
        val sectionName = filteredCategories!![i].name
        // Get category ID to compare later
        val categoryId = filteredCategories!![i].id
        // Prepare array to store events by category
        val eventsByCategory : ArrayList<Event> = ArrayList()

        // For each event get category and push it to an array
        for(event in eventList!!){
            if (event.categoryId == categoryId) {
                eventsByCategory.add(event)
            }
        }

        // Set Event List Recycler View (horizontal recycler view)
        val itemListDataAdapter = EventListAdapter(mContext, eventsByCategory)

        // Set Text for category Title
        itemRowHolder.categoryTitle.setText(sectionName)

        // Set fixed size to recycler_view_event_list
        itemRowHolder.recycler_view_event_list.setHasFixedSize(true)
        // Set to Horizontal
        itemRowHolder.recycler_view_event_list.layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)
        itemRowHolder.recycler_view_event_list.isNestedScrollingEnabled = false

        // Set adapter
        itemRowHolder.recycler_view_event_list.adapter = itemListDataAdapter

    }


    // Get Item Count - need because of Interface
    override fun getItemCount(): Int {
        activeCategories!!.clear()

        // For each event get category and push it to an array
        for(event in eventList!!){
            val category = event.category!!
            activeCategories!!.add(category)
        }

        // Remove duplicate categories
        filteredCategories = activeCategories!!.distinct()

        return filteredCategories?.size ?: 0
    }


    inner class ItemRowHolder(view: View) : RecyclerView.ViewHolder(view) {
        var categoryTitle: TextView
        var recycler_view_event_list: RecyclerView

        init {
            // Get category title View
            this.categoryTitle = view.findViewById(R.id.categoryTitle)
            // Get recycler_view View
            this.recycler_view_event_list = view.findViewById(R.id.recycler_view_event_list)
        }
    }
}
