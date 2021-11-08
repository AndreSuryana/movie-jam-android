package com.example.moviejam.ui.main.movies

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviejam.adapter.DataAdapter
import com.example.moviejam.data.model.DataEntity
import com.example.moviejam.databinding.FragmentMoviesBinding
import com.example.moviejam.repository.DummyDataRepository
import com.example.moviejam.ui.detail.DetailActivity
import com.example.moviejam.utils.ViewModelFactory

class MoviesFragment : Fragment() {

    private lateinit var fragmentMoviesBinding: FragmentMoviesBinding
    private lateinit var moviesViewModel: MoviesViewModel
    private lateinit var moviesAdapter: DataAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentMoviesBinding = FragmentMoviesBinding.inflate(inflater, container, false)
        return fragmentMoviesBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUI()
        setupViewModel()
        setupObserver()
    }

    private fun setupUI() {
        moviesAdapter = DataAdapter()
        with(fragmentMoviesBinding.rvMovies) {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = moviesAdapter
        }
        moviesAdapter.setOnItemClickListener(object : DataAdapter.OnItemClickListener {
            override fun onClick(data: DataEntity) {
                Intent(activity, DetailActivity::class.java).also {
                    it.putExtra(DetailActivity.EXTRA_DATA, data)
                    startActivity(it)
                }
            }
        })
    }

    private fun setupViewModel() {
        moviesViewModel = ViewModelProvider(
            this,
            ViewModelFactory(DummyDataRepository(context))
        )[MoviesViewModel::class.java]
    }

    private fun setupObserver() {
        moviesViewModel.listMovies.observe(viewLifecycleOwner, { event ->
            when (event) {
                is MoviesViewModel.MoviesEvent.Success -> {
                    hideProgressBar()
                    moviesAdapter.setList(event.movies)
                }
                is MoviesViewModel.MoviesEvent.Failure -> {
                    hideProgressBar()
                    showToast(event.message)
                }
                is MoviesViewModel.MoviesEvent.Loading -> {
                    showProgressBar()
                }
                is MoviesViewModel.MoviesEvent.Empty -> {
                    showProgressBar()
                    showToast("Movies is Empty!")
                }
            }
        })
    }

    private fun showProgressBar() {
        fragmentMoviesBinding.progressBar.isVisible = true
    }

    private fun hideProgressBar() {
        fragmentMoviesBinding.progressBar.isVisible = false
    }

    private fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }
}