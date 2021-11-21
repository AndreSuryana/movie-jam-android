package com.example.moviejam.ui.tvshowdetail

import androidx.lifecycle.*
import com.example.moviejam.data.source.local.LocalDataSource
import com.example.moviejam.data.source.local.entity.FavoriteEntity
import com.example.moviejam.data.source.remote.response.tvshowdetail.TvShowDetailResponse
import com.example.moviejam.repository.MainRepository
import com.example.moviejam.utils.Event
import com.example.moviejam.utils.Resource
import com.example.moviejam.utils.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TvShowDetailViewModel @Inject constructor(
    private val repository: MainRepository,
    private val localDataSource: LocalDataSource
) : ViewModel() {

    private val _dataDetail = MutableLiveData<Event<Resource<TvShowDetailResponse>>>()
    val dataDetail: LiveData<Event<Resource<TvShowDetailResponse>>> = _dataDetail

    fun setData(id: Int) {
        viewModelScope.launch {
            _dataDetail.postValue(Event(Resource(Status.LOADING, null, null)))

            val response = repository.getTvShowDetail(id.toString())
            val data = response.data

            if (data != null)
                _dataDetail.postValue(Event(Resource(Status.SUCCESS, data, null)))
            else
                _dataDetail.postValue(Event(Resource(Status.ERROR, null, response.message)))
        }
    }

    suspend fun addTvShowToFavorite(tvShow: TvShowDetailResponse, isMovie: Boolean = false) {
        viewModelScope.launch {
            val favTvShow = FavoriteEntity(
                tvShow.id,
                tvShow.posterPath,
                tvShow.name,
                tvShow.voteAverage,
                tvShow.firstAirDate,
                isMovie
            )
            localDataSource.insertToFavorites(favTvShow)
        }
    }

    suspend fun checkFavoriteTvShow(id: Int?): Int? =
        localDataSource.checkFavoriteTvShows(id)

    fun deleteTvShowFromFavorite(id: Int?) {
        viewModelScope.launch {
            localDataSource.deleteFromFavorites(id)
        }
    }
}