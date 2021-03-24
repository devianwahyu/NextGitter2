package com.example.nextgitter2.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nextgitter2.data.model.User
import com.example.nextgitter2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

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
                Toast.makeText(this@MainActivity, data.username, Toast.LENGTH_SHORT).show()
            }
        })

        binding.apply {
            rvList.layoutManager = LinearLayoutManager(this@MainActivity)
            rvList.adapter = adapter
            rvList.setHasFixedSize(true)

            btnSearch.setOnClickListener {
                searchUser()
            }
        }

        viewModel.getSearchUser().observe(this, {
            if (it!=null) {
                adapter.setList(it)
                showLoading(false)
            }
        })
    }

    private fun searchUser() {
        binding.apply {
            val q = txtSearch.text.toString()
            if (q.isEmpty()) return
            showLoading(true)
            viewModel.setSearchUser(q)
        }
    }

    private fun showLoading(b: Boolean) {
        binding.apply {
            loading.visibility = if (b) View.VISIBLE else View.GONE
        }
    }

}