package com.example.guest.ambarproject.instantapp.adapter

import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory
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
import com.example.guest.ambarproject.instantapp.network.RetrofitInitializer
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.properties.Delegates


class RepositoryListAdapter(val context: Context) : RecyclerView.Adapter<RepositoryListAdapter.RepositoryHolder>() {

    var repositories: List<Repository> by Delegates.observable(emptyList()) { _, oldList, newList ->
        autoNotify(oldList, newList) { (id), (id1) -> id == id1 }
    }

    private fun setImage(repository: Repository, holder: RepositoryHolder) {
        Picasso
            .get()
            .load(repository.owner.avatar_url)
            .placeholder(R.drawable.placeholder)
            .into(holder.imgUser, object : com.squareup.picasso.Callback {
                override fun onSuccess() {
                    val imageBitmap = (holder.imgUser.drawable as BitmapDrawable).bitmap
                    val imageDrawable = RoundedBitmapDrawableFactory.create(Resources.getSystem(), imageBitmap)
                    imageDrawable.isCircular = true
                    imageDrawable.cornerRadius = Math.max(imageBitmap.width, imageBitmap.height) / 2.0f
                    holder.imgUser.setImageDrawable(imageDrawable)
                }

                override fun onError(e: Exception?) {
                    holder.imgUser.setImageResource(R.drawable.placeholder)
                }
            })
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
            val repository = repositories[position]

            for (itemChangeList in payloads as MutableList<ArrayList<String>>) {
                if (itemChangeList.contains("img_update"))
                    setImage(repository, holder)

                if (itemChangeList.contains("name_update"))
                    holder.txtFullName.text = repository.full_name

                if (itemChangeList.contains("description_update"))
                    holder.txtDescription.text = repository.description

                if (itemChangeList.contains("stars_update"))
                    holder.txtStars.text = repository.stargazers_count.toString()

                if (itemChangeList.contains("forks_update"))
                    holder.txtForks.text = repository.forks_count.toString()

            }
        }
    }

    override fun onBindViewHolder(holder: RepositoryHolder, position: Int) {
        val repository = repositories[position]

        setImage(repository, holder)
        holder.txtFullName.text = repository.full_name
        holder.txtDescription.text = repository.description

        val call = RetrofitInitializer().repoService().repository(repository.owner.login, repository.name)
        call.enqueue(object: Callback<Repository> {
            override fun onResponse(call: Call<Repository>?,
                                    response: Response<Repository>?) {

                response?.body()?.let {
                    holder.txtStars.text = it.stargazers_count.toString()
                    holder.txtForks.text = it.forks_count.toString()
                }
            }

            override fun onFailure(call: Call<Repository>?,
                                   t: Throwable?) {
            }
        })

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

            return (oldItem.owner.avatar_url == newItem.owner.avatar_url &&
                    oldItem.full_name == newItem.full_name &&
                    oldItem.description == newItem.description &&
                    oldItem.stargazers_count == newItem.stargazers_count &&
                    oldItem.forks_count == newItem.forks_count)
        }

        override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
            val oldItem = oldList[oldItemPosition]
            val newItem = newList[newItemPosition]
            val itemChangeList = ArrayList<String>()

            if (oldItem.owner.avatar_url != newItem.owner.avatar_url) itemChangeList.add("img_update")
            if (oldItem.full_name != newItem.full_name) itemChangeList.add("name_update")
            if (oldItem.description != newItem.description) itemChangeList.add("description_update")
            if (oldItem.stargazers_count != newItem.stargazers_count) itemChangeList.add("stars_update")
            if (oldItem.forks_count != newItem.forks_count) itemChangeList.add("forks_update")

            return itemChangeList
        }
    }, true)

    diff.dispatchUpdatesTo(this)
}