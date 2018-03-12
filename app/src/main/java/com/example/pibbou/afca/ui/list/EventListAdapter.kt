package com.example.pibbou.afca.ui.list

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.ToggleButton
import com.example.pibbou.afca.App
import com.example.pibbou.afca.R
import com.example.pibbou.afca.repository.entity.Event
import com.example.pibbou.afca.ui.detail.DetailActivity
import com.squareup.picasso.Picasso
import java.util.ArrayList


/**
 * Created by arnaudpinot on 07/01/2018.
 */

class EventListAdapter(private val mContext: Context, private val eventList: ArrayList<Event>?) : RecyclerView.Adapter<EventListAdapter.SingleItemRowHolder>() {

    private val mOnClickListener: View.OnClickListener

    // Favorite manager call
    val favoriteManager = App.sInstance.getFavoriteManager()


    var isInList: Boolean = false
    private val onCheckedChanged: CompoundButton.OnCheckedChangeListener


    init {
        // Inspired by Jetbrains Kotlin Examples
        // https://github.com/JetBrains/kotlin-examples/blob/master/gradle/android-dbflow/app/src/main/java/mobi/porquenao/poc/kotlin/ui/MainAdapter.kt
        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as Event

            val I = Intent(mContext, DetailActivity::class.java)
                    .putExtra("id", item.id)
            mContext.startActivity(I)
        }

        onCheckedChanged = CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            val event = buttonView.tag as Event
            Log.i("checked", isChecked.toString())
            showToast(event, isChecked)
        }
    }


    fun showToast(event: Event, isChecked: Boolean) {

        if (!isChecked) {
            favoriteManager?.removeFavorite(mContext, event)
            Log.i("strin", "remove")
            isInList = false
        } else {
            favoriteManager?.addFavorite(mContext, event)
            Log.i("strin", "addw")
            isInList = true
        }
    }

    fun setButton(toggle:ToggleButton, event: Event?) {

        if(isInList == false) {
            // Init
            toggle.isChecked = false
        } else {
            // Init
            toggle.isChecked = true

        }

        with (toggle) {
            tag = event
            setOnCheckedChangeListener(onCheckedChanged)
        }

    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): SingleItemRowHolder {
        val v = LayoutInflater.from(viewGroup.context).inflate(R.layout.list_event_card, null)
        return SingleItemRowHolder(v)
    }


    override fun onBindViewHolder(holder: SingleItemRowHolder, i: Int) {
        // Get singleEvent
        val singleEvent = eventList?.get(i)
        // Set text to card title
        holder.eventCardTitle.setText(singleEvent?.name)
        // holder.eventCardAuthor.setText(singleEvent?.author)



        Picasso.with(mContext)
            .load(singleEvent?.image)
            .into(holder.eventCardImage)

        isInList = favoriteManager?.currentFavorites!!.filter {
            it.id === singleEvent?.id
        }.count() > 0


        // Inspired by Jetbrains Kotlin Examples
        // https://github.com/JetBrains/kotlin-examples/blob/master/gradle/android-dbflow/app/src/main/java/mobi/porquenao/poc/kotlin/ui/MainAdapter.kt
        with (holder.itemView) {
            tag = singleEvent
            setOnClickListener(mOnClickListener)
        }

        setButton(holder.eventCardFavorite, singleEvent)

        Log.i("info", "setting it")

    }


    // Get Item Count - need because of Interface
    override fun getItemCount(): Int {
        return eventList?.size ?: 0
    }


    inner class SingleItemRowHolder(view: View) : RecyclerView.ViewHolder(view) {

        var eventCardTitle: TextView
        var eventCardImage: ImageView
        /*var eventCardAuthor: TextView
        var eventCardHour: TextView
        var eventCardPlace: TextView*/
        var eventCardFavorite: ToggleButton

        init {
            // Get card title view
            this.eventCardTitle = view.findViewById(R.id.eventCardTitle)
            // Get card Image view
            this.eventCardImage = view.findViewById(R.id.eventCardImage)

            /*this.eventCardAuthor = view.findViewById(R.id.eventCardAuthor)

            this.eventCardHour = view.findViewById(R.id.eventCardHour)

            this.eventCardPlace = view.findViewById(R.id.eventCardPlace)*/

            this.eventCardFavorite = view.findViewById(R.id.button_favorite)
        }
    }
}