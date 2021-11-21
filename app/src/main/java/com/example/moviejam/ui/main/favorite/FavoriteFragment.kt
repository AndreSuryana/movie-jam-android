package com.example.moviejam.ui.main.favorite

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviejam.adapter.MoviesAdapter
import com.example.moviejam.adapter.TvShowsAdapter
import com.example.moviejam.data.source.local.entity.FavoriteEntity
import com.example.moviejam.data.source.remote.response.movie.Movie
import com.example.moviejam.data.source.remote.response.tvshow.TvShow
import com.example.moviejam.databinding.FragmentFavoriteBinding
import com.example.moviejam.ui.moviedetail.MovieDetailActivity
import com.example.moviejam.ui.tvshowdetail.TvShowDetailActivity
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint

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
        favoriteFragmentBinding?.tabLayout?.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {

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
               Any()
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
        val moviesAdapter = MoviesAdapter()

        favoriteFragmentBinding?.rvFavorites?.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = moviesAdapter
        }

        moviesAdapter.setOnItemClickListener(object : MoviesAdapter.OnItemClickListener {
            override fun onClick(id: Int) {
                Intent(activity, MovieDetailActivity::class.java).also {
                    it.putExtra(MovieDetailActivity.EXTRA_ID, id)
                    startActivity(it)
                }
            }
        })

        favoriteViewModel.getFavoriteMovies().observe(viewLifecycleOwner, { list ->
            val favoriteMovies = mapToMovieList(list)
            moviesAdapter.setList(favoriteMovies)
        })
    }

    private fun setContentTvShowsFavorite() {
        val tvShowsAdapter = TvShowsAdapter()

        favoriteFragmentBinding?.rvFavorites?.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = tvShowsAdapter
        }

        tvShowsAdapter.setOnItemClickListener(object : TvShowsAdapter.OnItemClickListener {
            override fun onClick(id: Int) {
                Intent(activity, TvShowDetailActivity::class.java).also {
                    it.putExtra(TvShowDetailActivity.EXTRA_ID, id)
                    startActivity(it)
                }
            }
        })

        favoriteViewModel.getFavoriteTvShows().observe(viewLifecycleOwner, { list ->
            val favoriteTvShows = mapToTvShowList(list)
            tvShowsAdapter.setList(favoriteTvShows)
        })
    }

    private fun mapToMovieList(list: List<FavoriteEntity>): List<Movie> {
        val listMovie = ArrayList<Movie>()
        list.forEach {
            val newMovie = Movie(
                it.id,
                it.posterPath,
                it.title,
                it.voteAverage,
                it.releaseDate
            )
            listMovie.add(newMovie)
        }
        return listMovie
    }

    private fun mapToTvShowList(list: List<FavoriteEntity>): List<TvShow> {
        val listTvShow = ArrayList<TvShow>()
        list.forEach {
            val newMovie = TvShow(
                it.id,
                it.posterPath,
                it.title,
                it.voteAverage,
                it.releaseDate
            )
            listTvShow.add(newMovie)
        }
        return listTvShow
    }

    override fun onDestroy() {
        super.onDestroy()
        favoriteFragmentBinding = null
    }
}