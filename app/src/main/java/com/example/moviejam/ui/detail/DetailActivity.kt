package com.example.moviejam.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.moviejam.R
import com.example.moviejam.data.model.DataEntity
import com.example.moviejam.databinding.ActivityDetailBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailActivity : AppCompatActivity() {

    private lateinit var activityDetailBinding: ActivityDetailBinding
    private lateinit var detailViewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityDetailBinding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(activityDetailBinding.root)

        setupViewModel()
        setupObserver()
    }

    private fun setupUI(data: DataEntity) {
        lifecycleScope.launch(Dispatchers.Main) {
            activityDetailBinding.apply {
                // For BG Images
                ivPosterBg.loadImage(data.poster)
                // For Main Images
                ivPoster.loadImage(data.poster)
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

        // Back button
        activityDetailBinding.btnBack.setOnClickListener { onBackClicked() }
    }

    private fun ImageView.loadImage(url: String?) {
        Glide.with(this.context)
            .load(url)
            .apply(RequestOptions.placeholderOf(R.drawable.placeholder))
            .error(R.drawable.placeholder)
            .into(this)
    }

    private fun setupViewModel() {
        detailViewModel = ViewModelProvider(this)[DetailViewModel::class.java]
    }

    private fun setupObserver() {

        // Setup Detail Data
        lifecycleScope.launch {
            // Getting the data
            val dataReceived = intent.getParcelableExtra<DataEntity>(EXTRA_DATA)

            detailViewModel.setData(dataReceived)
            detailViewModel.dataDetail.observe(this@DetailActivity, { event ->
                when (event) {
                    is DetailViewModel.DataEvent.Success -> {
                        hideProgressBar()
                        setupUI(event.data)
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
        Toast.makeText(this@DetailActivity, message, Toast.LENGTH_LONG).show()
    }

    companion object {
        const val EXTRA_DATA = "extra_data"
    }
}