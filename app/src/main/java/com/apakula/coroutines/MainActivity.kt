package com.apakula.coroutines

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.apakula.coroutines.model.User
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    private lateinit var context: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        context = this
        initViewModel()
        setupEventListeners()
        observeUserDetails()
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
    }

    private fun setupEventListeners() {
        fab.setOnClickListener {
            fetchUser((1..50).shuffled().first().toString())
        }
    }

    private fun observeUserDetails() {

        viewModel.user.observe(this, {

            when (it) {

                is Any -> {
                    tv_main.text = getString(R.string.loading)
                }

                is User -> {
                    tv_main.text = it.data.toString()
                }

                else -> {
                    tv_main.text = getString(R.string.error)
                }

            }
        })
    }

    private fun fetchUser(userId: String) {
        progress_bar.visibility = View.VISIBLE
        viewModel.setUserId(userId)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        viewModel.cancelJobs()
    }
}