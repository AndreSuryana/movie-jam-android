package com.example.moviejam.ui.main.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moviejam.data.model.Profile
import com.example.moviejam.utils.Event
import com.example.moviejam.utils.Resource
import com.example.moviejam.utils.Status

class ProfileViewModel : ViewModel() {

    private val _profileUIState = MutableLiveData<Event<Resource<Profile>>>()
    val profileUIState: LiveData<Event<Resource<Profile>>> = _profileUIState

    fun setProfileContent(profileName: String, profilePicture: Int, profileEmail: String) {

        val profile = Profile(profileName, profilePicture, profileEmail)

        _profileUIState.value = Event(Resource(Status.LOADING, null, null))
        /**
         * Check if :
         * - name is not empty, picture is not 0, email is not empty, return success
         * - name is empty/picture is 0/email is empty, return error
         */
        if (profile.name.isNotEmpty() && profile.picture != 0 && profile.email.isNotEmpty())
            _profileUIState.value = Event(Resource(Status.SUCCESS, profile, null))
        else if (profile.name.isEmpty() || profile.picture == 0 || profile.email.isEmpty())
            _profileUIState.value = Event(Resource(Status.ERROR, null, "Profile Information is Missing!"))
        else
            _profileUIState.value = Event(Resource(Status.ERROR, null, "An error has been occurred!"))
    }
}