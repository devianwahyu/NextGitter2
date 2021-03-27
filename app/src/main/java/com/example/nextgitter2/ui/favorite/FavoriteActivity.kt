package com.example.nextgitter2.ui.favorite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.nextgitter2.data.local.FavoriteUser
import com.example.nextgitter2.data.model.User
import com.example.nextgitter2.databinding.ActivityFavoriteBinding
import com.example.nextgitter2.ui.detail.DetailUserActivity
import com.example.nextgitter2.ui.main.UserAdapter

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var viewModel: FavoriteViewModel
    private lateinit var adapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        adapter = UserAdapter()
        adapter.notifyDataSetChanged()

        viewModel = ViewModelProvider(this).get(FavoriteViewModel::class.java)

        adapter.setOnItemClickCallback(object: UserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: User) {
                val favorite = FavoriteUser(
                    data.id,
                    data.username,
                    data.avatarUrl
                )
                Intent(this@FavoriteActivity, DetailUserActivity::class.java).also {
                    it.putExtra(DetailUserActivity.EXTRA_FAVORITE, favorite)
                    startActivity(it)
                }
            }
        })

        binding.apply {
            rv.layoutManager = LinearLayoutManager(this@FavoriteActivity)
            rv.adapter = adapter
            rv.setHasFixedSize(true)

            back.setOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }
        }

        viewModel.getFavoriteUsers().observe(this, {
            if (it != null) {
                binding.apply {
                    val list = mapList(it)
                    adapter.setList(list)
                }
            }
        })
    }

    private fun mapList(users: List<FavoriteUser>): ArrayList<User> {
        val listUsers = ArrayList<User>()
        for (user in users) {
            val userMapped = User(
                user.username,
                user.id,
                user.avatarUrl
            )
            listUsers.add(userMapped)
        }
        return listUsers
    }
}