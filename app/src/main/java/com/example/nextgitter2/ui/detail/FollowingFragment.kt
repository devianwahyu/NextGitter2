package com.example.nextgitter2.ui.detail

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nextgitter2.R
import com.example.nextgitter2.data.model.User
import com.example.nextgitter2.databinding.FragmentFollowBinding
import com.example.nextgitter2.ui.main.UserAdapter

class FollowingFragment: Fragment(R.layout.fragment_follow) {

    private var _binding: FragmentFollowBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: FollowingViewModel
    private lateinit var adapter: UserAdapter
    private lateinit var username: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args = arguments
        username = args?.getString(DetailUserActivity.EXTRA_USERNAME).toString()
        _binding = FragmentFollowBinding.bind(view)

        adapter = UserAdapter()
        adapter.notifyDataSetChanged()

        binding.apply {
            rv.setHasFixedSize(true)
            rv.layoutManager = LinearLayoutManager(activity)
            rv.adapter = adapter
        }

        showLoading(true)

        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(FollowingViewModel::class.java)
        viewModel.setFollowing(username)
        viewModel.getFollowing().observe(viewLifecycleOwner, {
            if (it != null) {
                adapter.setList(it)
                adapter.setOnItemClickCallback(object: UserAdapter.OnItemClickCallback {
                    override fun onItemClicked(data: User) {
                        startActivity(Intent(activity, DetailUserActivity::class.java).putExtra(DetailUserActivity.EXTRA_USERNAME, data.username))
                    }
                })
                showLoading(false)
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun showLoading(b: Boolean) {
        binding.loading.visibility = if (b) View.VISIBLE else View.GONE
    }
}