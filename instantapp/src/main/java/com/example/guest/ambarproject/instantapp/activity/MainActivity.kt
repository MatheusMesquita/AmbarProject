package com.example.guest.ambarproject.instantapp.activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.guest.ambarproject.instantapp.R
import com.example.guest.ambarproject.instantapp.adapter.RepositoryListAdapter
import com.example.guest.ambarproject.instantapp.model.Repository
import com.example.guest.ambarproject.instantapp.network.RetrofitInitializer
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var mAdapter: RepositoryListAdapter
    private lateinit var mRepositories: List<Repository>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mRepositories = emptyList()
        swipeRefresh.isRefreshing = true
        callRepos()

        mAdapter = RepositoryListAdapter(this@MainActivity)
        mAdapter.repositories = mRepositories
        rvRepoList.adapter = mAdapter
        rvRepoList.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this@MainActivity)
        rvRepoList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0) fab.hide()
                else if (dy <= 0) fab.show()
            }
        })

        fab.setOnClickListener { rvRepoList.smoothScrollToPosition(0) }

        swipeRefresh.setOnRefreshListener { callRepos() }
    }

    private fun callRepos() {
        val call = RetrofitInitializer().repoService().repositories()
        call.enqueue(object: Callback<List<Repository>> {
            override fun onResponse(call: Call<List<Repository>?>?, response: Response<List<Repository>?>?) {
                response?.body()?.let {
                    mRepositories = it.subList(0, 99)
                    mAdapter.repositories = mRepositories
                    if (swipeRefresh.isRefreshing)
                        swipeRefresh.isRefreshing = false
                }
            }

            override fun onFailure(call: Call<List<Repository>?>?, t: Throwable?) {
                Toast.makeText(this@MainActivity, "Problem receiving data", Toast.LENGTH_LONG).show()
                if (swipeRefresh.isRefreshing)
                    swipeRefresh.isRefreshing = false
            }
        })
    }
}
