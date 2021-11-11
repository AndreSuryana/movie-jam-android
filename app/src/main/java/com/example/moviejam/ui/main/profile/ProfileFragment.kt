package com.example.moviejam.ui.main.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.moviejam.R
import com.example.moviejam.data.model.Profile
import com.example.moviejam.databinding.FragmentProfileBinding
import com.example.moviejam.utils.Status
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProfileFragment : Fragment() {

    private lateinit var fragmentProfileBinding: FragmentProfileBinding
    private lateinit var profileViewModel: ProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentProfileBinding = FragmentProfileBinding.inflate(inflater, container, false)
        return fragmentProfileBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewModel()
        setupObserver()
    }

    private fun setupViewModel() {
        profileViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[ProfileViewModel::class.java]
    }

    private fun setupObserver() {
        lifecycleScope.launch {
            profileViewModel.profileUIState.observe(viewLifecycleOwner, { event ->
                event.getDataIfNotHandledYet()?.let { resource ->
                    when(resource.status) {
                        Status.SUCCESS -> {
                            hideProgressBar()
                            resource.data?.let { setContent(it) }
                        }
                        Status.ERROR -> {
                            hideProgressBar()
                            resource.message?.let { showToast(it) }
                        }
                        Status.LOADING -> {
                            showProgressBar()
                        }
                    }
                }
            })
        }
    }

    private fun setContent(profile: Profile) {
        lifecycleScope.launch(Dispatchers.Main) {
            fragmentProfileBinding.apply {
                Glide.with(this@ProfileFragment)
                    .load(profile.picture)
                    .apply(RequestOptions.placeholderOf(R.drawable.placeholder))
                    .error(R.drawable.placeholder)
                    .into(ivProfilePicture)
                tvProfileName.text = profile.name
                tvProfileEmail.text = profile.email
            }
        }
    }

    private fun showProgressBar() {
        fragmentProfileBinding.progressBar.isVisible = true
    }

    private fun hideProgressBar() {
        fragmentProfileBinding.progressBar.isVisible = false
    }

    private fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }
}