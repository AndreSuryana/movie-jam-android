package com.example.moviejam.ui.main.favorite

import androidx.lifecycle.*
import com.example.moviejam.data.model.DataEntity
import com.example.moviejam.data.local.DatabaseHelper
import kotlinx.coroutines.launch

class FavoriteViewModel(
    private val database: DatabaseHelper?
) : ViewModel() {

    sealed class ListState {
        class Success(val favList: List<DataEntity>) : ListState()
        class Failure(val message: String) : ListState()
        object Loading : ListState()
        object Empty : ListState()
    }

    private var _favoriteList = MutableLiveData<ListState>(ListState.Empty)
    val favoriteList: LiveData<ListState> = _favoriteList

    init {
        setFavoriteList()
    }

    private fun setFavoriteList() {
        viewModelScope.launch {
            _favoriteList.value = ListState.Loading
            val list = database?.getFavoriteList()
            if (list?.isNotEmpty() == true)
                _favoriteList.value = ListState.Success(list)
            else
                _favoriteList.value = ListState.Failure("An error has been occurred!")
        }
    }
}