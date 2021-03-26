package com.example.nextgitter2.ui.detail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.nextgitter2.R
import com.example.nextgitter2.databinding.ActivityDetailBinding
import com.example.nextgitter2.ui.main.MainActivity

class DetailUserActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var viewModel: DetailUserViewModel

    companion object{
        const val EXTRA_USERNAME = "extra_username"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        val username = intent.getStringExtra(EXTRA_USERNAME)
        val bundle = Bundle()
        bundle.putString(EXTRA_USERNAME, username)

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

        binding.back.setOnClickListener(this)
        binding.logo.setOnClickListener(this)

        val sectionPagerAdapter = SectionPagerAdapter(this, supportFragmentManager, bundle)
        binding.apply {
            viewPager.adapter = sectionPagerAdapter
            tabs.setupWithViewPager(viewPager)
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.back -> onBackPressedDispatcher.onBackPressed()
            R.id.logo -> {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }
    }
}