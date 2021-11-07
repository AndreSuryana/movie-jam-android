package com.example.moviejam.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.moviejam.R
import com.example.moviejam.data.DataEntity
import com.example.moviejam.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private lateinit var activityDetailBinding: ActivityDetailBinding
    private lateinit var detailViewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityDetailBinding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(activityDetailBinding.root)

        // Getting the data
        val dataReceived = intent.getParcelableExtra<DataEntity>(EXTRA_DATA)

        // Setup Detail Data
        detailViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[DetailViewModel::class.java]
        detailViewModel.setData(dataReceived)

        detailViewModel.listData.observe(this, { event ->
            when (event) {
                is DetailViewModel.DataEvent.Success -> {
                    hideProgressBar()
                    val data: DataEntity = event.data
                    activityDetailBinding.apply {
                        // For BG Images
                        Glide.with(this@DetailActivity)
                            .load(data.poster)
                            .apply(RequestOptions.placeholderOf(R.drawable.placeholder))
                            .error(R.drawable.placeholder)
                            .into(ivPosterBg)
                        // For Main Images
                        Glide.with(this@DetailActivity)
                            .load(data.poster)
                            .apply(RequestOptions.placeholderOf(R.drawable.placeholder))
                            .error(R.drawable.placeholder)
                            .into(ivPoster)
                        tvToolbarTitle.text = data.title
                        tvTitle.text = data.title
                        tvRating.text = data.rating
                        tvDate.text = data.date
                        tvDuration.text = data.duration
                        tvGenre.text = data.genre
                        tvOverview.text = data.overview
                        tvStatus.text = data.status
                        tvLanguage.text = data.language
                        tvCreator.text = data.creator
                    }
                }
                is DetailViewModel.DataEvent.Failure -> {
                    showProgressBar()
                    showToast(event.message)
                }
                is DetailViewModel.DataEvent.Loading -> {
                    showProgressBar()
                }
                is DetailViewModel.DataEvent.Empty -> {
                    hideProgressBar()
                    showToast("Error! No data was loaded!")
                }
            }
        })
    }

    private fun showProgressBar() {
        activityDetailBinding.progressBar.isVisible = true
    }

    private fun hideProgressBar() {
        activityDetailBinding.progressBar.isVisible = false
    }

    private fun showToast(message: String) {
        Toast.makeText(this@DetailActivity, message, Toast.LENGTH_LONG).show()
    }

    companion object {
        const val EXTRA_DATA = "extra_data"
    }
}