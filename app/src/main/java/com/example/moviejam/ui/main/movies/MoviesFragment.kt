package com.example.moviejam.ui.main.movies

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviejam.adapter.MoviesAdapter
import com.example.moviejam.databinding.FragmentMoviesBinding
import com.example.moviejam.ui.moviedetail.MovieDetailActivity
import com.example.moviejam.utils.Status
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MoviesFragment : Fragment() {

    private var fragmentMoviesBinding: FragmentMoviesBinding? = null
    private val moviesViewModel: MoviesViewModel by viewModels()
    private lateinit var moviesAdapter: MoviesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentMoviesBinding = FragmentMoviesBinding.inflate(inflater, container, false)
        return fragmentMoviesBinding?.root
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
        fragmentMoviesBinding = null
    }

    private fun setupUI() {
        moviesAdapter = MoviesAdapter()

        lifecycleScope.launch(Dispatchers.Main) {
            fragmentMoviesBinding?.let {
                it.rvMovies.apply {
                    layoutManager = LinearLayoutManager(context)
                    setHasFixedSize(true)
                    adapter = moviesAdapter
                }
            }
            moviesAdapter.setOnItemClickListener(object : MoviesAdapter.OnItemClickListener {
                override fun onClick(id: Int) {
                    Intent(activity, MovieDetailActivity::class.java).also {
                        it.putExtra(MovieDetailActivity.EXTRA_ID, id)
                        startActivity(it)
                    }
                }
            })
        }
    }

    private fun setupObserver() {
        showProgressBar()

        lifecycleScope.launch {
            moviesViewModel.getMovies().observe(viewLifecycleOwner) { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        hideProgressBar()
                        resource?.data?.movies?.let { moviesAdapter.setList(it) }
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

        fragmentMoviesBinding?.svMovies?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                lifecycleScope.launch {
                    moviesViewModel.searchMovies(query).observe(viewLifecycleOwner) { resource ->
                        when (resource.status) {
                            Status.SUCCESS -> {
                                hideProgressBar()
                                resource?.data?.movies?.let { moviesAdapter.setList(it) }
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
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    private fun showProgressBar() {
        fragmentMoviesBinding?.progressBar?.isVisible = true
    }

    private fun hideProgressBar() {
        fragmentMoviesBinding?.progressBar?.isVisible = false
    }

    private fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }
}