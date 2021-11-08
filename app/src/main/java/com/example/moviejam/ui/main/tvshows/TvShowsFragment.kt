package com.example.moviejam.ui.main.tvshows

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
import com.example.moviejam.databinding.FragmentTvShowsBinding
import com.example.moviejam.repository.DummyDataRepository
import com.example.moviejam.ui.detail.DetailActivity
import com.example.moviejam.utils.ViewModelFactory

class TvShowsFragment : Fragment() {

    private lateinit var fragmentTvShowsBinding: FragmentTvShowsBinding
    private lateinit var tvShowsViewModel: TvShowsViewModel
    private lateinit var tvShowsAdapter: DataAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentTvShowsBinding = FragmentTvShowsBinding.inflate(inflater, container, false)
        return fragmentTvShowsBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUI()
        setupViewModel()
        setupObserver()
    }

    private fun setupUI() {
        tvShowsAdapter = DataAdapter()
        with(fragmentTvShowsBinding.rvTvShows) {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = tvShowsAdapter
        }
        tvShowsAdapter.setOnItemClickListener(object : DataAdapter.OnItemClickListener {
            override fun onClick(data: DataEntity) {
                Intent(activity, DetailActivity::class.java).also {
                    it.putExtra(DetailActivity.EXTRA_DATA, data)
                    startActivity(it)
                }
            }
        })
    }

    private fun setupViewModel() {
        tvShowsViewModel = ViewModelProvider(
            this,
            ViewModelFactory(DummyDataRepository(context))
        )[TvShowsViewModel::class.java]
    }

    private fun setupObserver() {
        tvShowsViewModel.listTvShows.observe(viewLifecycleOwner, { event ->
            when (event) {
                is TvShowsViewModel.TvShowsEvent.Success -> {
                    hideProgressBar()
                    tvShowsAdapter.setList(event.tvShows)
                }
                is TvShowsViewModel.TvShowsEvent.Failure -> {
                    hideProgressBar()
                    showToast(event.message)
                }
                is TvShowsViewModel.TvShowsEvent.Loading -> {
                    showProgressBar()
                }
                is TvShowsViewModel.TvShowsEvent.Empty -> {
                    showProgressBar()
                    showToast("Movies is Empty!")
                }
            }
        })
    }

    private fun showProgressBar() {
        fragmentTvShowsBinding.progressBar.isVisible = true
    }

    private fun hideProgressBar() {
        fragmentTvShowsBinding.progressBar.isVisible = false
    }

    private fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }
}