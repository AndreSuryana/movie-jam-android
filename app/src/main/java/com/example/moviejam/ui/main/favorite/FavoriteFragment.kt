package com.example.moviejam.ui.main.favorite

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
import com.example.moviejam.data.local.DatabaseBuilder
import com.example.moviejam.data.local.DatabaseHelperImpl
import com.example.moviejam.data.model.DataEntity
import com.example.moviejam.databinding.FragmentFavoriteBinding
import com.example.moviejam.ui.detail.DetailActivity
import com.example.moviejam.utils.ViewModelFactory

class FavoriteFragment : Fragment() {

    private lateinit var fragmentFavoriteBinding: FragmentFavoriteBinding
    private lateinit var favoriteViewModel: FavoriteViewModel
    private lateinit var favoriteAdapter: DataAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentFavoriteBinding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return fragmentFavoriteBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewModel()
        setupObserver()
        setupUI()
    }

    private fun setupUI() {
        favoriteAdapter = DataAdapter()
        with(fragmentFavoriteBinding.rvFavorite) {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = favoriteAdapter
        }
        favoriteAdapter.setOnItemClickListener(object : DataAdapter.OnItemClickListener {
            override fun onClick(data: DataEntity) {
                Intent(activity, DetailActivity::class.java).also {
                    it.putExtra(DetailActivity.EXTRA_DATA, data)
                    startActivity(it)
                }
            }
        })
    }

    private fun setupViewModel() {
        favoriteViewModel = ViewModelProvider(
            this,
            ViewModelFactory(
                database = DatabaseBuilder.getInstance(context)?.let {
                    DatabaseHelperImpl(it)
                }
            )
        )[FavoriteViewModel::class.java]
    }

    private fun setupObserver() {
        favoriteViewModel.favoriteList.observe(viewLifecycleOwner, { state ->
            when (state) {
                is FavoriteViewModel.ListState.Success -> {
                    hideProgressBar()
                    favoriteAdapter.setList(state.favList)
                }
                is FavoriteViewModel.ListState.Failure -> {
                    hideProgressBar()
                    showToast(state.message)
                }
                is FavoriteViewModel.ListState.Loading ->
                    showProgressBar()
                is FavoriteViewModel.ListState.Empty ->
                    showToast("Favorite List is Empty!")
            }
        })
    }

    private fun showProgressBar() {
        fragmentFavoriteBinding.progressBar.isVisible = true
    }

    private fun hideProgressBar() {
        fragmentFavoriteBinding.progressBar.isVisible = false
    }

    private fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }
}