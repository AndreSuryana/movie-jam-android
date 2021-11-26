package com.example.moviejam.ui.moviedetail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.DataSource
import androidx.paging.PagedList
import com.example.moviejam.data.repository.MainRepository
import com.example.moviejam.data.source.local.LocalDataSource
import com.example.moviejam.data.source.local.entity.FavoriteEntity
import com.example.moviejam.data.source.remote.response.moviedetail.MovieDetailResponse
import com.example.moviejam.ui.main.favorite.FavoriteViewModelTest
import com.example.moviejam.utils.DummyData
import com.example.moviejam.utils.PagedListUtil
import com.example.moviejam.utils.Resource
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@Suppress("UNCHECKED_CAST")
@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MovieDetailViewModelTest {

    @get:Rule
    var instantTaskExecutor = InstantTaskExecutorRule()

    @Mock
    private lateinit var observer: Observer<Resource<MovieDetailResponse>>

    @Mock
    private lateinit var favoriteObserver: Observer<Resource<PagedList<FavoriteEntity>>>

    @Mock
    private lateinit var fakeMovieJamRepository: MainRepository

    private val local = mock(LocalDataSource::class.java)

    private lateinit var viewModel: MovieDetailViewModel

    private val dummyDetailMovie = DummyData.dummyDetailMovie
    private val dummyFavoriteMovies = DummyData.dummyFavoriteMovies

    @Before
    fun setUp() {
        viewModel = MovieDetailViewModel(fakeMovieJamRepository)
    }

    @Test
    fun `getData should be success`() = runBlockingTest {
        val id = 238

        val expected = MutableLiveData<Resource<MovieDetailResponse>>()
        expected.value = Resource.success(dummyDetailMovie)

        `when`(fakeMovieJamRepository.getMovieDetail(id.toString())).thenReturn(expected)

        viewModel.getData(id).observeForever(observer)
        verify(observer).onChanged(expected.value)

        val expectedValue = expected.value
        val actualValue = viewModel.getData(id).value

        assertThat(actualValue).isEqualTo(expectedValue)
        assertThat(actualValue?.data?.id).isEqualTo(expectedValue?.data?.id)
    }

    @Test
    fun `getData should be error`() = runBlockingTest {
        val id = 238
        val expectedMessage = "An error occurred!"
        val expected = MutableLiveData<Resource<MovieDetailResponse>>()
        expected.value = Resource.error(expectedMessage)

        `when`(fakeMovieJamRepository.getMovieDetail(id.toString())).thenReturn(expected)

        viewModel.getData(id).observeForever(observer)
        verify(observer).onChanged(expected.value)

        val actualMessage = viewModel.getData(id).value?.message

        assertThat(actualMessage).isEqualTo(expectedMessage)
    }

    @Test
    fun addMovieToFavorites() = runBlockingTest {
        val favoriteMovie = FavoriteEntity(
            580489,
            "/rjkmN1dniUHVYAtwuV3Tji7FsDO.jpg",
            "Venom: Let There Be Carnage",
            6.8,
            "2021-09-30",
            true
        )
        val favoriteList = listOf(favoriteMovie)

        fakeMovieJamRepository.insertToFavorites(favoriteMovie)

        val dataSourceFactory =
            mock(DataSource.Factory::class.java) as DataSource.Factory<Int, FavoriteEntity>
        lenient().`when`(local.getListFavoriteMovies()).thenReturn(dataSourceFactory)
        fakeMovieJamRepository.getListFavoriteMovies()

        val favoriteMovies = Resource.success(PagedListUtil.mockPagedList(favoriteList))
        verify(fakeMovieJamRepository).getListFavoriteMovies()

        assertThat(favoriteMovies.data).isNotNull()
        assertThat(favoriteMovies.data?.size).isEqualTo(favoriteList.size)
    }

    @Test
    fun checkFavoriteMovie() = runBlockingTest {
        val id = 29883
        val mockedReturnValue = 1

        `when`(fakeMovieJamRepository.checkFavoriteMovies(id)).thenReturn(mockedReturnValue)

        val actualValue = viewModel.checkFavoriteMovie(id)

        assertThat(actualValue).isNotNull()
        assertThat(actualValue).isEqualTo(mockedReturnValue)
    }

    @Test
    fun deleteMovieFromFavorites() = runBlockingTest {
        val id = 580489
        val listFavorites = ArrayList<FavoriteEntity>(dummyFavoriteMovies)

        fakeMovieJamRepository.deleteFromFavorites(id)
        listFavorites.removeIf { it.id == id}

        val movies = FavoriteViewModelTest.PagedTestDataSources.snapshot(dummyFavoriteMovies)
        val expected = MutableLiveData<Resource<PagedList<FavoriteEntity>>>()
        expected.value = Resource.success(movies)

        `when`(fakeMovieJamRepository.getListFavoriteMovies()).thenReturn(expected)

        fakeMovieJamRepository.getListFavoriteMovies().observeForever(favoriteObserver)
        verify(favoriteObserver).onChanged(expected.value)

        val expectedValue = expected.value
        val actualValue = fakeMovieJamRepository.getListFavoriteMovies().value

        assertThat(actualValue).isEqualTo(expectedValue)
        assertThat(actualValue?.data).isEqualTo(expectedValue?.data)
        assertThat(actualValue?.data?.size).isEqualTo(expectedValue?.data?.size)
    }
}