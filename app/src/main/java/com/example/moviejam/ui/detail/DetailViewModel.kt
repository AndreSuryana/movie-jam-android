package com.example.moviejam.ui.detail

import androidx.lifecycle.*
import com.example.moviejam.data.model.DataEntity
import com.example.moviejam.data.local.DatabaseHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailViewModel constructor(
    private val database: DatabaseHelper?
) : ViewModel() {

    sealed class DataEvent {
        class Success(val data: DataEntity) : DataEvent()
        class Failure(val message: String) : DataEvent()
        object Loading : DataEvent()
        object Empty : DataEvent()
    }

    private val _dataDetail = MutableLiveData<DataEvent>(DataEvent.Empty)
    val dataDetail: LiveData<DataEvent> = _dataDetail

    fun setData(data: DataEntity?) {
        _dataDetail.value = DataEvent.Loading
        if (data != null)
            _dataDetail.value = DataEvent.Success(data)
        else
            _dataDetail.value = DataEvent.Failure("An error has been occurred!")
    }

    suspend fun addToFavorite(data: DataEntity) {
        CoroutineScope(Dispatchers.IO).launch {
            database?.addToFavorite(data)
        }
    }

    suspend fun checkFavoriteItem(title: String) = database?.checkFavoriteItem(title)

    fun deleteFavoriteItem(title: String) {
        CoroutineScope(Dispatchers.IO).launch {
            database?.deleteFavoriteItem(title)
        }
    }
}