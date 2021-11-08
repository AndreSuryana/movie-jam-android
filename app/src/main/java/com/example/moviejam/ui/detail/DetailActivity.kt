package com.example.moviejam.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.moviejam.R
import com.example.moviejam.data.local.DatabaseBuilder
import com.example.moviejam.data.local.DatabaseHelperImpl
import com.example.moviejam.data.model.DataEntity
import com.example.moviejam.databinding.ActivityDetailBinding
import com.example.moviejam.dispatchers.DispatcherProvider
import com.example.moviejam.dispatchers.MainDispatcher
import com.example.moviejam.utils.ViewModelFactory
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailActivity : AppCompatActivity() {

    private lateinit var activityDetailBinding: ActivityDetailBinding
    private lateinit var detailViewModel: DetailViewModel
    private val dispatcher: DispatcherProvider by lazy {
        MainDispatcher()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityDetailBinding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(activityDetailBinding.root)

        setupViewModel()
        setupObserver()
    }

    private fun setupUI(data: DataEntity) {
        lifecycleScope.launch(dispatcher.main) {
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

        // Setup Favorite Feature
        var checked = false
        lifecycleScope.launch(dispatcher.io) {
            val count = detailViewModel.checkFavoriteItem(data.title)
            withContext(dispatcher.main) {
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
            lifecycleScope.launch(dispatcher.io) {
                if (checked) detailViewModel.addToFavorite(data)
                else detailViewModel.deleteFavoriteItem(data.title)
            }
        }

        // Back button
        activityDetailBinding.btnBack.setOnClickListener { onBackClicked() }
    }

    private fun setupViewModel() {
        detailViewModel = ViewModelProvider(
            this,
            ViewModelFactory(
                database = DatabaseBuilder.getInstance(this)?.let {
                    DatabaseHelperImpl(it)
                }
            )
        )[DetailViewModel::class.java]
    }

    private fun setupObserver() {
        // Getting the data
        val dataReceived = intent.getParcelableExtra<DataEntity>(EXTRA_DATA)

        // Setup Detail Data
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