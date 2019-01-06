package com.example.guest.ambarproject.instantapp.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.guest.ambarproject.instantapp.R
import com.example.guest.ambarproject.instantapp.model.Repository
import com.example.guest.ambarproject.instantapp.network.RetrofitInitializer
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val call = RetrofitInitializer().repoService().repositories()
        call.enqueue(object: Callback<List<Repository>> {
            override fun onResponse(call: Call<List<Repository>?>?,
                                    response: Response<List<Repository>?>?) {

                response?.body()?.let {
                    val reps: List<Repository> = it
                    reps.forEach { println(it.full_name) }
                }
            }

            override fun onFailure(call: Call<List<Repository>?>?,
                                   t: Throwable?) {
            }
        })

    }
}
