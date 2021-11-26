package com.example.moviejam.ui.main.home

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
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.moviejam.R
import com.example.moviejam.adapter.MoviesAdapter
import com.example.moviejam.adapter.TopAdapter
import com.example.moviejam.adapter.TvShowsAdapter
import com.example.moviejam.databinding.FragmentHomeBinding
import com.example.moviejam.ui.moviedetail.MovieDetailActivity
import com.example.moviejam.ui.tvshowdetail.TvShowDetailActivity
import com.example.moviejam.utils.Status
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var fragmentHomeBinding: FragmentHomeBinding? = null
    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentHomeBinding = FragmentHomeBinding.inflate(inflater, container, false)
        return fragmentHomeBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        greetingsContent()
        topMoviesContent()
        popularMoviesContent()
        popularTVShowsContent()
    }

    override fun onResume() {
        super.onResume()

        greetingsContent()
        topMoviesContent()
        popularMoviesContent()
        popularTVShowsContent()
    }

    override fun onDestroy() {
        super.onDestroy()
        fragmentHomeBinding = null
    }

    private fun topMoviesContent() {
        // Adapter
        val topMoviesAdapter = TopAdapter()

        // Observer
        lifecycleScope.launch {
            homeViewModel.getTopMovies().observe(viewLifecycleOwner) { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        hideProgressBarTopMovies()
                        resource.data?.movies?.let { topMoviesAdapter.setListTopMovies(it) }
                    }
                    Status.ERROR -> {
                        showProgressBarTopMovies()
                        showToast(resource.message.toString())
                    }
                    Status.LOADING -> showProgressBarTopMovies()
                }
            }
        }

        // Recycler View
        fragmentHomeBinding?.rvTop?.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
            adapter = topMoviesAdapter
        }

        // Listener
        topMoviesAdapter.setOnItemClickListener(object : TopAdapter.OnItemClickListener {
            override fun onClick(id: Int) {
                Intent(activity, MovieDetailActivity::class.java).also {
                    it.putExtra(MovieDetailActivity.EXTRA_ID, id)
                    startActivity(it)
                }
            }
        })
    }

    private fun popularMoviesContent() {
        // Adapter
        val popularMoviesAdapter = MoviesAdapter()

        // Observer
        lifecycleScope.launch {
            homeViewModel.getPopularMovies().observe(viewLifecycleOwner) { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        hideProgressBarPopularMovies()
                        resource.data?.movies?.let { popularMoviesAdapter.setList(it) }
                    }
                    Status.ERROR -> {
                        showProgressBarPopularMovies()
                        showToast(resource.message.toString())
                    }
                    Status.LOADING -> showProgressBarPopularMovies()
                }
            }
        }

        // Recycler View
        fragmentHomeBinding?.rvPopularMovies?.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = popularMoviesAdapter
        }

        // Listener
        popularMoviesAdapter.setOnItemClickListener(object : MoviesAdapter.OnItemClickListener {
            override fun onClick(id: Int) {
                Intent(activity, MovieDetailActivity::class.java).also {
                    it.putExtra(MovieDetailActivity.EXTRA_ID, id)
                    startActivity(it)
                }
            }
        })
    }

    private fun popularTVShowsContent() {
        // Adapter
        val popularTvShowsAdapter = TvShowsAdapter()

        // Observer
        lifecycleScope.launch {
            homeViewModel.getPopularTvShows().observe(viewLifecycleOwner) { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        hideProgressBarPopularTvShows()
                        resource.data?.tvShows?.let { popularTvShowsAdapter.setList(it) }
                    }
                    Status.ERROR -> {
                        showProgressBarPopularTvShows()
                        showToast(resource.message.toString())
                    }
                    Status.LOADING -> {
                        showProgressBarPopularTvShows()
                    }
                }
            }
        }

        // Recycler View
        fragmentHomeBinding?.rvPopularTvShows?.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = popularTvShowsAdapter
        }

        // Listener
        popularTvShowsAdapter.setOnItemClickListener(object :
            TvShowsAdapter.OnItemClickListener {
            override fun onClick(id: Int) {
                Intent(activity, TvShowDetailActivity::class.java).also {
                    it.putExtra(TvShowDetailActivity.EXTRA_ID, id)
                    startActivity(it)
                }
            }
        })
    }

    private fun greetingsContent() {
        fragmentHomeBinding?.apply {
            Glide.with(this@HomeFragment)
                .load(R.drawable.profile_picture)
                .apply(RequestOptions.placeholderOf(R.drawable.placeholder))
                .error(R.drawable.placeholder)
                .into(ivProfile)
            tvGreetings.text = getText(R.string.dummy_greetings_text)
        }
    }

    private fun showProgressBarTopMovies() {
        fragmentHomeBinding?.progressBarTopMovies?.isVisible = true
    }

    private fun hideProgressBarTopMovies() {
        fragmentHomeBinding?.progressBarTopMovies?.isVisible = false
    }

    private fun showProgressBarPopularMovies() {
        fragmentHomeBinding?.progressBarPopularMovies?.isVisible = true
    }

    private fun hideProgressBarPopularMovies() {
        fragmentHomeBinding?.progressBarPopularMovies?.isVisible = false
    }

    private fun showProgressBarPopularTvShows() {
        fragmentHomeBinding?.progressBarPopularTvShows?.isVisible = true
    }

    private fun hideProgressBarPopularTvShows() {
        fragmentHomeBinding?.progressBarPopularTvShows?.isVisible = false
    }

    private fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }
}