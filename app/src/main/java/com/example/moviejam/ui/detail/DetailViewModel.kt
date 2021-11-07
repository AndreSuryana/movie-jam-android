package com.example.moviejam.ui.detail

import androidx.lifecycle.*
import com.example.moviejam.data.DataEntity

class DetailViewModel : ViewModel() {

    sealed class DataEvent {
        class Success(val data: DataEntity) : DataEvent()
        class Failure(val message: String) : DataEvent()
        object Loading : DataEvent()
        object Empty : DataEvent()
    }

    private val _listData = MutableLiveData<DataEvent>(DataEvent.Empty)
    val listData: LiveData<DataEvent> = _listData

    fun setData(data: DataEntity?) {
        _listData.value = DataEvent.Loading
        if (data != null)
            _listData.value = DataEvent.Success(data)
        else
            _listData.value = DataEvent.Failure("An error has been occurred!")
    }
}