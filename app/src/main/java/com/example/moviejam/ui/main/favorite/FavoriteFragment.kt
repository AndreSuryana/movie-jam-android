package com.example.moviejam.ui.main.favorite

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviejam.adapter.FavoriteAdapter
import com.example.moviejam.databinding.FragmentFavoriteBinding
import com.example.moviejam.ui.moviedetail.MovieDetailActivity
import com.example.moviejam.ui.tvshowdetail.TvShowDetailActivity
import com.example.moviejam.utils.Status
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavoriteFragment : Fragment() {

    private var favoriteFragmentBinding: FragmentFavoriteBinding? = null
    private val favoriteViewModel: FavoriteViewModel by viewModels()

    object TabContent {
        const val Movies = "Movies"
        const val TvShows = "TV Shows"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        favoriteFragmentBinding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return favoriteFragmentBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUI()
    }

    private fun setupUI() {
        // Set default content to movies favorite
        setContentMoviesFavorite()

        // Tab Layout Listener
        favoriteFragmentBinding?.tabLayout?.addOnTabSelectedListener(object :
            TabLayout.OnTabSelectedListener {

            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.text) {
                    TabContent.Movies -> {
                        // Load Recycler View with MoviesAdapter
                        setContentMoviesFavorite()
                    }
                    TabContent.TvShows -> {
                        // Load Recycler View with TvShowsAdapter
                        setContentTvShowsFavorite()
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                when (tab?.text) {
                    TabContent.Movies -> {
                        setContentTvShowsFavorite()
                    }
                    TabContent.TvShows -> {
                        setContentMoviesFavorite()
                    }
                }
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                when (tab?.text) {
                    TabContent.Movies -> {
                        // Load Recycler View with MoviesAdapter
                        setContentMoviesFavorite()
                    }
                    TabContent.TvShows -> {
                        // Load Recycler View with TvShowsAdapter
                        setContentTvShowsFavorite()
                    }
                }
            }
        })
    }

    private fun setContentMoviesFavorite() {
        val moviesAdapter = FavoriteAdapter()

        favoriteFragmentBinding?.rvFavorites?.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = moviesAdapter
        }

        moviesAdapter.setOnItemClickListener(object :
            FavoriteAdapter.OnItemClickListener {
            override fun onClick(id: Int) {
                Intent(activity, MovieDetailActivity::class.java).also {
                    it.putExtra(MovieDetailActivity.EXTRA_ID, id)
                    startActivity(it)
                }
            }
        })

        moviesAdapter.setOnBtnDeleteClickListener(object :
            FavoriteAdapter.OnBtnDeleteClickListener {
            override fun onClick(id: Int) {
                favoriteViewModel.deleteFromFavorites(id)
            }
        })

        lifecycleScope.launch {
            favoriteViewModel.getFavoriteMovies().observe(viewLifecycleOwner) { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        hideProgressBar()
                        moviesAdapter.submitList(resource.data)
                    }
                    Status.ERROR -> {
                        hideProgressBar()
                        showToast(resource.message.toString())
                    }
                    Status.LOADING -> {
                        showProgressBar()
                    }
                }
            }
        }
    }

    private fun setContentTvShowsFavorite() {
        val tvShowsAdapter = FavoriteAdapter()

        favoriteFragmentBinding?.rvFavorites?.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = tvShowsAdapter
        }

        tvShowsAdapter.setOnItemClickListener(object :
            FavoriteAdapter.OnItemClickListener {
            override fun onClick(id: Int) {
                Intent(activity, TvShowDetailActivity::class.java).also {
                    it.putExtra(TvShowDetailActivity.EXTRA_ID, id)
                    startActivity(it)
                }
            }
        })

        tvShowsAdapter.setOnBtnDeleteClickListener(object :
            FavoriteAdapter.OnBtnDeleteClickListener {
            override fun onClick(id: Int) {
                favoriteViewModel.deleteFromFavorites(id)
            }
        })

        lifecycleScope.launch {
            favoriteViewModel.getFavoriteTvShows().observe(viewLifecycleOwner) { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        hideProgressBar()
                        tvShowsAdapter.submitList(resource.data)
                    }
                    Status.ERROR -> {
                        hideProgressBar()
                        showToast(resource.message.toString())
                    }
                    Status.LOADING -> {
                        showProgressBar()
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        favoriteFragmentBinding = null
    }

    private fun showProgressBar() {
        favoriteFragmentBinding?.progressBar?.isVisible = true
    }

    private fun hideProgressBar() {
        favoriteFragmentBinding?.progressBar?.isVisible = false
    }

    private fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }
}