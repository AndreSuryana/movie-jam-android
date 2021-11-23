package com.example.moviejam.ui.main.tvshows

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviejam.adapter.TvShowsPagingAdapter
import com.example.moviejam.databinding.FragmentTvShowsBinding
import com.example.moviejam.ui.tvshowdetail.TvShowDetailActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TvShowsFragment : Fragment() {

    private var fragmentTvShowsBinding: FragmentTvShowsBinding? = null
    private val tvShowsViewModel: TvShowsViewModel by viewModels()
    private lateinit var tvShowsAdapter: TvShowsPagingAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentTvShowsBinding = FragmentTvShowsBinding.inflate(inflater, container, false)
        return fragmentTvShowsBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUI()
        setupObserver()
    }

    override fun onResume() {
        super.onResume()

        setupUI()
        setupObserver()
    }

    override fun onDestroy() {
        super.onDestroy()
        fragmentTvShowsBinding = null
    }

    private fun setupUI() {
        tvShowsAdapter = TvShowsPagingAdapter()

        lifecycleScope.launch(Dispatchers.Main) {
            fragmentTvShowsBinding?.let {
                it.rvTvShows.apply {
                    layoutManager = LinearLayoutManager(context)
                    setHasFixedSize(true)
                    adapter = tvShowsAdapter
                }
            }
            tvShowsAdapter.setOnItemClickListener(object : TvShowsPagingAdapter.OnItemClickListener {
                override fun onClick(id: Int) {
                    Intent(activity, TvShowDetailActivity::class.java).also {
                        it.putExtra(TvShowDetailActivity.EXTRA_ID, id)
                        startActivity(it)
                    }
                }
            })
        }
    }

    private fun setupObserver() {
        showProgressBar()

        lifecycleScope.launch {
            tvShowsViewModel.setTvShows().observe(viewLifecycleOwner) { pagingData ->
                tvShowsAdapter.submitData(viewLifecycleOwner.lifecycle, pagingData)
                hideProgressBar()
            }
        }

        fragmentTvShowsBinding?.svTvShows?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                lifecycleScope.launch {
                    tvShowsViewModel.searchTvShows(query).observe(viewLifecycleOwner) { pagingData ->
                        tvShowsAdapter.submitData(viewLifecycleOwner.lifecycle, pagingData)
                        hideProgressBar()
                    }
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    private fun showProgressBar() {
        fragmentTvShowsBinding?.progressBar?.isVisible = true
    }

    private fun hideProgressBar() {
        fragmentTvShowsBinding?.progressBar?.isVisible = false
    }
}