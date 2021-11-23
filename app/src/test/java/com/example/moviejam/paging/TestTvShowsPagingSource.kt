package com.example.moviejam.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.moviejam.data.source.remote.response.tvshow.TvShow
import com.example.moviejam.utils.DummyData

private const val STARTING_PAGE_INDEX = 1

class TestTvShowsPagingSource : PagingSource<Int, TvShow>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TvShow> {
        val position = params.key ?: STARTING_PAGE_INDEX

        val listMovies = ArrayList<TvShow>()

        val response = DummyData.dummyTvShowsResponse
        val result = response.tvShows

        listMovies.addAll(result)

        return LoadResult.Page(
            data = listMovies,
            prevKey = if (position == STARTING_PAGE_INDEX) null else position - 1,
            nextKey = if (listMovies.isEmpty()) null else position + 1
        )
    }

    override fun getRefreshKey(state: PagingState<Int, TvShow>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}
