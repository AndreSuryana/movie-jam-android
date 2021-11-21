package com.example.moviejam.ui.moviedetail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.moviejam.R
import com.example.moviejam.constant.Constants
import com.example.moviejam.data.source.remote.response.moviedetail.GenresItem
import com.example.moviejam.data.source.remote.response.moviedetail.MovieDetailResponse
import com.example.moviejam.data.source.remote.response.moviedetail.ProductionCompaniesItem
import com.example.moviejam.databinding.ActivityDetailBinding
import com.example.moviejam.utils.Status
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class MovieDetailActivity : AppCompatActivity() {

    private lateinit var activityDetailBinding: ActivityDetailBinding
    private val movieDetailViewModel: MovieDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_MovieJam)

        activityDetailBinding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(activityDetailBinding.root)

        setupObserver()
    }

    private fun setContent(data: MovieDetailResponse) {
        lifecycleScope.launch(Dispatchers.Main) {
            activityDetailBinding.apply {
                // For BG Images
                ivPosterBg.loadImage(data.backdropPath)
                // For Main Images
                ivPoster.loadImage(data.posterPath)
                tvToolbarTitle.text = data.title
                tvTitle.text = data.title
                tvRating.text = data.voteAverage.toString()
                tvDate.text = data.releaseDate
                tvGenre.text = getGenres(data.genres)
                tvOverview.text = data.overview
                tvStatus.text = data.status
                tvLanguage.text = data.originalLanguage
                tvCreator.text = getCreator(data.productionCompanies)
            }
        }

        // Back button
        activityDetailBinding.btnBack.setOnClickListener { onBackClicked() }

        // Toggle Favorite
        var checked = false
        lifecycleScope.launch(Dispatchers.IO) {
            val count = movieDetailViewModel.checkFavoriteMovie(data.id)
            withContext(Dispatchers.Main) {
                if (count != null && count > 0) {
                    activityDetailBinding.toggleFavorite.isChecked = true
                    checked = true
                } else {
                    activityDetailBinding.toggleFavorite.isChecked = false
                    checked = false
                }
            }
        }

        activityDetailBinding.toggleFavorite.setOnClickListener {
            checked = !checked
            lifecycleScope.launch(Dispatchers.IO) {
                if (checked)
                    movieDetailViewModel.addMovieToFavorites(data)
                else
                    movieDetailViewModel.deleteMovieFromFavorites(data.id)
            }
        }
    }

    private fun getCreator(creatorItems: List<ProductionCompaniesItem>): String {
        val creators = arrayListOf<String>()
        creatorItems.forEach { creator ->
            creators.add(creator.name)
        }
        return creators.joinToString(separator = ", ")
    }

    private fun getGenres(genresItems: List<GenresItem>): String {
        val genres = arrayListOf<String>()
        genresItems.forEach { genre ->
            genres.add(genre.name)
        }
        return genres.joinToString(separator = ", ")
    }

    private fun ImageView.loadImage(path: String) {
        val url = Constants.IMAGE_BASE_URL + path
        Glide.with(this.context)
            .load(url)
            .apply(RequestOptions.placeholderOf(R.drawable.placeholder))
            .error(R.drawable.placeholder)
            .into(this)
    }

    private fun setupObserver() {

        // Setup Detail Data
        lifecycleScope.launch {
            // Getting the data
            val idReceived = intent.getIntExtra(EXTRA_ID, 0)

            movieDetailViewModel.setData(idReceived)
            movieDetailViewModel.dataDetail.observe(this@MovieDetailActivity, { event ->
                event.getDataIfNotHandledYet()?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            hideProgressBar()
                            resource.data?.let { setContent(it) }
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
            })
        }
    }

    private fun onBackClicked() {
        finish()
    }

    private fun showProgressBar() {
        activityDetailBinding.progressBar.isVisible = true
    }

    private fun hideProgressBar() {
        activityDetailBinding.progressBar.isVisible = false
    }

    private fun showToast(message: String) {
        Toast.makeText(this@MovieDetailActivity, message, Toast.LENGTH_LONG).show()
    }

    companion object {
        const val EXTRA_ID = "extra_id"
    }
}