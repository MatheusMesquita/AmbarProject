package com.example.guest.ambarproject.instantapp.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
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

        val call = RetrofitInitializer().repoService().repositories()
        call.enqueue(object: Callback<List<Repository>> {
            override fun onResponse(call: Call<List<Repository>?>?,
                                    response: Response<List<Repository>?>?) {

                response?.body()?.let {
                    mRepositories = it.subList(0, 99)
                    mAdapter = RepositoryListAdapter(this@MainActivity)
                    mAdapter.repositories = mRepositories
                    rvRepoList.adapter = mAdapter
                    rvRepoList.layoutManager = LinearLayoutManager(this@MainActivity)
                }
            }

            override fun onFailure(call: Call<List<Repository>?>?,
                                   t: Throwable?) {
            }
        })

    }
}
