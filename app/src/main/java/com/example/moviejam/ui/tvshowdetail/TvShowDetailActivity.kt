package com.example.moviejam.ui.tvshowdetail

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
import com.example.moviejam.data.source.remote.response.tvshowdetail.GenresItem
import com.example.moviejam.data.source.remote.response.tvshowdetail.ProductionCompaniesItem
import com.example.moviejam.data.source.remote.response.tvshowdetail.TvShowDetailResponse
import com.example.moviejam.databinding.ActivityDetailBinding
import com.example.moviejam.utils.Status
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TvShowDetailActivity : AppCompatActivity() {

    private lateinit var activityDetailBinding: ActivityDetailBinding
    private val tvShowDetailViewModel: TvShowDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_MovieJam)

        activityDetailBinding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(activityDetailBinding.root)

        setupObserver()
    }

    private fun setContent(data: TvShowDetailResponse) {
        lifecycleScope.launch(Dispatchers.Main) {
            activityDetailBinding.apply {
                // For BG Images
                ivPosterBg.loadImage(data.backdropPath)
                // For Main Images
                ivPoster.loadImage(data.posterPath)
                tvToolbarTitle.text = data.name
                tvTitle.text = data.name
                tvRating.text = data.voteAverage.toString()
                tvDate.text = data.firstAirDate
                tvGenre.text = getGenres(data.genres)
                tvOverview.text = data.overview
                tvStatus.text = data.status
                tvLanguage.text = data.originalLanguage
                tvCreator.text = getCreator(data.productionCompanies)
            }
        }

        // Back button
        activityDetailBinding.btnBack.setOnClickListener { onBackClicked() }
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

            tvShowDetailViewModel.setData(idReceived)

            tvShowDetailViewModel.dataDetail.observe(this@TvShowDetailActivity, { event ->
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
        Toast.makeText(this@TvShowDetailActivity, message, Toast.LENGTH_LONG).show()
    }

    companion object {
        const val EXTRA_ID = "extra_id"
    }
}