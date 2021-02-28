package com.udacity.asteroidradar

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.udacity.asteroidradar.main.AsteroidAdapter
import com.udacity.asteroidradar.main.MainViewModel


class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: AsteroidAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }



//    private fun setupViewModel() {
//        val startDate = LocalDate.now().toString()
//        val endDate = LocalDate.now().plusDays(7).toString()
//        viewModel = ViewModelProviders.of(
//                this,
//                MainViewModelFactory(AsteroidHelper(NasaApiService.Companion.AsteroidApi, startDate, endDate, KEY ),
//                        ImageOfDayHelper(NasaApiService.Companion.ImageApi, KEY))
//        ).get(MainViewModel::class.java)
//    }

//    private fun setupUI() {
//        asteroid_recycler.layoutManager = LinearLayoutManager(this)
//        adapter = AsteroidAdapter()
//        asteroid_recycler.addItemDecoration(
//                DividerItemDecoration(
//                        asteroid_recycler.context,
//                        (asteroid_recycler.layoutManager as LinearLayoutManager).orientation
//                )
//        )
//        asteroid_recycler.adapter = adapter
//    }
}
