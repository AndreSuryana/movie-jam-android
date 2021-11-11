package com.example.moviejam.ui.main.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.moviejam.R
import com.example.moviejam.adapter.DataAdapter
import com.example.moviejam.adapter.TopAdapter
import com.example.moviejam.data.model.DataEntity
import com.example.moviejam.databinding.FragmentHomeBinding
import com.example.moviejam.repository.DummyDataRepository
import com.example.moviejam.ui.detail.DetailActivity
import com.example.moviejam.utils.Status
import com.example.moviejam.utils.ViewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private lateinit var fragmentHomeBinding: FragmentHomeBinding
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var topMoviesAdapter: TopAdapter
    private lateinit var popularMoviesAdapter: DataAdapter
    private lateinit var popularTvShowsAdapter: DataAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentHomeBinding = FragmentHomeBinding.inflate(inflater, container, false)
        return fragmentHomeBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        topMoviesAdapter = TopAdapter()
        popularMoviesAdapter = DataAdapter()
        popularTvShowsAdapter = DataAdapter()

        setupViewModel()
        setupUI()
        setupObserver()
    }

    private fun setupUI() {
        // Set dummy data for username in Home Fragment
        lifecycleScope.launch(Dispatchers.Main) {
            fragmentHomeBinding.apply {
                Glide.with(this@HomeFragment)
                    .load(R.drawable.profile_picture)
                    .apply(RequestOptions.placeholderOf(R.drawable.placeholder))
                    .error(R.drawable.placeholder)
                    .into(ivProfile)
                tvGreetings.text = getText(R.string.dummy_greetings_text)
            }

            // UI Setup Top Movies
            fragmentHomeBinding.rvTop.apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                setHasFixedSize(true)
                adapter = topMoviesAdapter
            }
            topMoviesAdapter.setOnItemClickListener(object : TopAdapter.OnItemClickListener {
                override fun onClick(data: DataEntity) {
                    Intent(activity, DetailActivity::class.java).also {
                        it.putExtra(DetailActivity.EXTRA_DATA, data)
                        startActivity(it)
                    }
                }
            })

            // UI Setup Popular Movies
            fragmentHomeBinding.rvPopularMovies.apply {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = popularMoviesAdapter
            }
            popularMoviesAdapter.setOnItemClickListener(object : DataAdapter.OnItemClickListener {
                override fun onClick(data: DataEntity) {
                    Intent(activity, DetailActivity::class.java).also {
                        it.putExtra(DetailActivity.EXTRA_DATA, data)
                        startActivity(it)
                    }
                }
            })
        }

        // UI Setup Popular TV Shows
        fragmentHomeBinding.rvPopularTvShows.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = popularTvShowsAdapter
        }
        popularTvShowsAdapter.setOnItemClickListener(object : DataAdapter.OnItemClickListener {
            override fun onClick(data: DataEntity) {
                Intent(activity, DetailActivity::class.java).also {
                    it.putExtra(DetailActivity.EXTRA_DATA, data)
                    startActivity(it)
                }
            }
        })
    }

    private fun setupObserver() {

        lifecycleScope.launch {
            // Observer Top Movies
            homeViewModel.listTopMovies.observe(viewLifecycleOwner, { event ->
                event.getDataIfNotHandledYet()?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            hideProgressBarTopMovies()
                            resource.data?.let { topMoviesAdapter.setListTopMovies(it) }
                        }
                        Status.ERROR -> {
                            hideProgressBarTopMovies()
                            showToast(resource.message.toString())
                        }
                        Status.LOADING -> showProgressBarTopMovies()
                    }
                }
            })

            // Observer Popular Movies
            homeViewModel.listPopularMovies.observe(viewLifecycleOwner, { event ->
                event.getDataIfNotHandledYet()?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            hideProgressBarPopularMovies()
                            resource.data?.let { popularMoviesAdapter.setList(it) }
                        }
                        Status.ERROR -> {
                            hideProgressBarPopularMovies()
                            showToast(resource.message.toString())
                        }
                        Status.LOADING -> showProgressBarPopularMovies()
                    }
                }
            })

            // Observer
            homeViewModel.listPopularTvShows.observe(viewLifecycleOwner, { event ->
                event.getDataIfNotHandledYet()?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            hideProgressBarPopularTvShows()
                            resource.data?.let { popularTvShowsAdapter.setList(it) }
                        }
                        Status.ERROR -> {
                            hideProgressBarPopularTvShows()
                            showToast(resource.message.toString())
                        }
                        Status.LOADING -> {
                            showProgressBarPopularTvShows()
                        }
                    }
                }
            })
        }
    }

    private fun setupViewModel() {
        homeViewModel = ViewModelProvider(
            this,
            ViewModelFactory(
                repository = DummyDataRepository(context)
            )
        )[HomeViewModel::class.java]
    }

    private fun showProgressBarTopMovies() {
        fragmentHomeBinding.progressBarTopMovies.isVisible = true
    }

    private fun hideProgressBarTopMovies() {
        fragmentHomeBinding.progressBarTopMovies.isVisible = false
    }

    private fun showProgressBarPopularMovies() {
        fragmentHomeBinding.progressBarPopularMovies.isVisible = true
    }

    private fun hideProgressBarPopularMovies() {
        fragmentHomeBinding.progressBarPopularMovies.isVisible = false
    }

    private fun showProgressBarPopularTvShows() {
        fragmentHomeBinding.progressBarPopularTvShows.isVisible = true
    }

    private fun hideProgressBarPopularTvShows() {
        fragmentHomeBinding.progressBarPopularTvShows.isVisible = false
    }

    private fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }
}