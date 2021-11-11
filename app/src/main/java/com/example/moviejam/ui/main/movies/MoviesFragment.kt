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
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviejam.adapter.DataAdapter
import com.example.moviejam.data.model.DataEntity
import com.example.moviejam.databinding.FragmentMoviesBinding
import com.example.moviejam.repository.DummyDataRepository
import com.example.moviejam.ui.detail.DetailActivity
import com.example.moviejam.utils.Status
import com.example.moviejam.utils.ViewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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

        moviesAdapter = DataAdapter()

        setupUI()
        setupViewModel()
        setupObserver()
    }

    private fun setupUI() {
        lifecycleScope.launch(Dispatchers.Main) {
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
    }

    private fun setupViewModel() {
        moviesViewModel = ViewModelProvider(
            this,
            ViewModelFactory(DummyDataRepository(context))
        )[MoviesViewModel::class.java]
    }

    private fun setupObserver() {
        lifecycleScope.launch {
            moviesViewModel.listMovies.observe(viewLifecycleOwner, { event ->
                event.getDataIfNotHandledYet()?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            hideProgressBar()
                            resource.data?.let { moviesAdapter.setList(it) }
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