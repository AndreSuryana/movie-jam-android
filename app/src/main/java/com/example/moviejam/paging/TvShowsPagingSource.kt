package com.example.moviejam.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.moviejam.data.source.remote.MovieJamAPI
import com.example.moviejam.data.source.remote.response.tvshow.TvShow
import retrofit2.HttpException
import java.io.IOException

private const val STARTING_PAGE_INDEX = 1

class TvShowsPagingSource(
    private val api: MovieJamAPI
) : PagingSource<Int, TvShow>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TvShow> {
        val position = params.key ?: STARTING_PAGE_INDEX

        return try {
            val listTvShows = ArrayList<TvShow>()

            val response = api.getPopularTvShows(page = position)
            val result = response.body()
            result?.tvShows?.let { listTvShows.addAll(it) }

            LoadResult.Page(
                data = listTvShows,
                prevKey = if (position == STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if (listTvShows.isEmpty()) null else position + 1
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, TvShow>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}