package com.example.nextgitter2.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nextgitter2.R
import com.example.nextgitter2.data.local.FavoriteUser
import com.example.nextgitter2.data.model.User
import com.example.nextgitter2.databinding.ActivityMainBinding
import com.example.nextgitter2.ui.detail.DetailUserActivity
import com.example.nextgitter2.ui.favorite.FavoriteActivity
import com.example.nextgitter2.ui.setting.SettingActivity

class MainActivity : AppCompatActivity(), View.OnClickListener {

    // Declare variables
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: UserViewModel
    private lateinit var adapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        adapter = UserAdapter()
        adapter.notifyDataSetChanged()
        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(UserViewModel::class.java)

        adapter.setOnItemClickCallback(object: UserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: User) {
                val favorite = FavoriteUser(
                    data.id,
                    data.username,
                    data.avatarUrl
                )
                Intent(this@MainActivity, DetailUserActivity::class.java).also {
                    it.putExtra(DetailUserActivity.EXTRA_FAVORITE, favorite)
                    startActivity(it)
                }
            }
        })

        // Binding all view in MainActivity
        binding.apply {
            rvList.layoutManager = LinearLayoutManager(this@MainActivity)
            rvList.adapter = adapter
            rvList.setHasFixedSize(true)

            // Button in MainActivity
            btnSettings.setOnClickListener(this@MainActivity)
            btnSearch.setOnClickListener (this@MainActivity)
            btnFloat.setOnClickListener (this@MainActivity)
        }

        viewModel.getSearchUser().observe(this, {
            if (it!=null) {
                adapter.setList(it)
                showLoading(false)
            }
        })


    }

    // Function for set loading state
    private fun showLoading(b: Boolean) {
        binding.apply {
            loading.visibility = if (b) View.VISIBLE else View.GONE
        }
    }

    // All button actions
    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.btn_settings -> {
                startActivity(Intent(this@MainActivity, SettingActivity::class.java))
            }
            R.id.btn_search -> {
                binding.apply {
                    val q = txtSearch.text.toString()
                    if (q.isEmpty()) return
                    showLoading(true)
                    viewModel.setSearchUser(q)
                }
            }
            R.id.btn_float -> {
                startActivity(Intent(this, FavoriteActivity::class.java))
            }
        }
    }

}