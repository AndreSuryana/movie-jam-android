package com.example.moviejam.ui.main.tvshows

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviejam.adapter.TvShowsAdapter
import com.example.moviejam.databinding.FragmentTvShowsBinding
import com.example.moviejam.ui.tvshowdetail.TvShowDetailActivity
import com.example.moviejam.utils.Status
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TvShowsFragment : Fragment() {

    private var fragmentTvShowsBinding: FragmentTvShowsBinding? = null
    private val tvShowsViewModel: TvShowsViewModel by viewModels()
    private lateinit var tvShowsAdapter: TvShowsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentTvShowsBinding = FragmentTvShowsBinding.inflate(inflater, container, false)
        return fragmentTvShowsBinding?.root
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
        fragmentTvShowsBinding = null
    }

    private fun setupUI() {
        tvShowsAdapter = TvShowsAdapter()

        lifecycleScope.launch(Dispatchers.Main) {
            fragmentTvShowsBinding?.let {
                it.rvTvShows.apply {
                    layoutManager = LinearLayoutManager(context)
                    setHasFixedSize(true)
                    adapter = tvShowsAdapter
                }
            }
            tvShowsAdapter.setOnItemClickListener(object : TvShowsAdapter.OnItemClickListener {
                override fun onClick(id: Int) {
                    Intent(activity, TvShowDetailActivity::class.java).also {
                        it.putExtra(TvShowDetailActivity.EXTRA_ID, id)
                        startActivity(it)
                    }
                }
            })
        }
    }

    private fun setupObserver() {
        lifecycleScope.launch {
            tvShowsViewModel.setTvShows()
            tvShowsViewModel.listTvShows.observe(viewLifecycleOwner, { event ->
                event.peekData().let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            hideProgressBar()
                            resource.data?.let { tvShowsAdapter.setList(it) }
                        }
                        Status.ERROR -> {
                            showProgressBar()
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
        fragmentTvShowsBinding?.progressBar?.isVisible = true
    }

    private fun hideProgressBar() {
        fragmentTvShowsBinding?.progressBar?.isVisible = false
    }

    private fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }
}