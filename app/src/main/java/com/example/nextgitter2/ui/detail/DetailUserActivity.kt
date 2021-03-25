package com.example.nextgitter2.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.nextgitter2.databinding.ActivityDetailBinding

class DetailUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var viewModel: DetailUserViewModel
    val bundle = Bundle()

    companion object{
        const val EXTRA_USERNAME = "extra_username"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        val username = intent.getStringExtra(EXTRA_USERNAME)
        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(DetailUserViewModel::class.java)
        username?.let { viewModel.setUserDetail(it) }
        viewModel.getUserDetail().observe(this, {
            if (it != null) {
                binding.apply {
                    Glide.with(this@DetailUserActivity)
                        .load(it.avatarUrl)
                        .into(imgAvatar)
                    tusername.text = it.username
                    name.text = it.name ?: "404"
                    location.text = it.location ?: "404"
                    company.text = it.company ?: "404"
                    repository.text = it.repository.let { repo -> if (repo > 1) "$repo Repositories" else "$repo Repository" }
                    follower.text = it.followers.let { follower -> if (follower > 1) "$follower Followers" else "$follower Follower" }
                    following.text = it.following.let { following -> "$following Following" }

                }
            }
        })

        binding.back.setOnClickListener { moveBack() }

        val sectionPagerAdapter = SectionPagerAdapter(this, supportFragmentManager, bundle)
        binding.apply {
            viewPager.adapter = sectionPagerAdapter
            tabs.setupWithViewPager(viewPager)
        }
    }

    private fun moveBack() {
        onBackPressedDispatcher.onBackPressed()
    }
}