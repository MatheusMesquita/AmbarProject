package com.example.guest.ambarproject.instantapp.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.support.v7.util.DiffUtil
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.guest.ambarproject.instantapp.R
import com.example.guest.ambarproject.instantapp.model.Repository
import com.squareup.picasso.Picasso
import java.util.*
import kotlin.properties.Delegates

class RepositoryListAdapter(val context: Context) : RecyclerView.Adapter<RepositoryListAdapter.RepositoryHolder>() {

    var repositories: List<Repository> by Delegates.observable(emptyList()) { _, oldList, newList ->
        autoNotify(oldList, newList) { (id), (id1) -> id == id1 }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.card_repository, parent, false)
        return RepositoryHolder(view)
    }

    override fun getItemCount() = repositories.size

    override fun onBindViewHolder(holder: RepositoryHolder, position: Int, payloads: MutableList<Any>) {
        if (payloads.isEmpty())
            onBindViewHolder(holder, position)
        else {
            //TODO
            val repository = repositories[position]

            for (itemChangeList in payloads as MutableList<ArrayList<String>>) {
                if (itemChangeList.contains("name_update")) {

                }
            }
        }
    }

    override fun onBindViewHolder(holder: RepositoryHolder, position: Int) {
        val repository = repositories[position]

        Picasso.get().load(repository.owner.avatar_url).into(holder.imgUser)
        holder.txtFullName.text = repository.full_name
        holder.txtDescription.text = repository.description
        holder.txtStars.text = repository.stars.toString()
        holder.txtForks.text = repository.forks.toString()

        holder.card.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(repository.html_url))
            context.startActivity(browserIntent)
        }
    }

    inner class RepositoryHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var card: CardView = itemView.findViewById(R.id.cardRepository)
        var imgUser: ImageView = itemView.findViewById(R.id.imgRepoUser)
        var txtFullName: TextView = itemView.findViewById(R.id.txtRepoFullName)
        var txtDescription: TextView = itemView.findViewById(R.id.txtRepoDescription)
        var txtStars: TextView = itemView.findViewById(R.id.txtStars)
        var txtForks: TextView = itemView.findViewById(R.id.txtForks)
    }
}

fun RecyclerView.Adapter<*>.autoNotify(oldList: List<Repository>, newList: List<Repository>, compare: (Repository, Repository) -> Boolean) {

    val diff = DiffUtil.calculateDiff(object : DiffUtil.Callback() {

        override fun getOldListSize() = oldList.size

        override fun getNewListSize() = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            compare(oldList[oldItemPosition], newList[newItemPosition])

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val oldItem = oldList[oldItemPosition]
            val newItem = newList[newItemPosition]

            return (oldItem.id == newItem.id &&
                    oldItem.name == newItem.name)
        }

        override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
            val oldItem = oldList[oldItemPosition]
            val newItem = newList[newItemPosition]
            val itemChangeList = ArrayList<String>()

            //TODO set possible changes to card
            if (oldItem.name != newItem.name) itemChangeList.add("name_update")

            return itemChangeList
        }
    }, true)

    diff.dispatchUpdatesTo(this)
}