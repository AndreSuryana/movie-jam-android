package com.example.moviejam.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.moviejam.data.source.remote.MovieJamAPI
import com.example.moviejam.data.source.remote.response.movie.Movie
import retrofit2.HttpException
import java.io.IOException

private const val STARTING_PAGE_INDEX = 1

class SearchMoviesPagingSource(
    private val api: MovieJamAPI,
    private val query: String?
) : PagingSource<Int, Movie>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val position = params.key ?: STARTING_PAGE_INDEX

        return try {
            val listMovies = ArrayList<Movie>()

            val response = api.searchMovies(query = query, page = position)
            val result = response.body()
            result?.movies?.let { listMovies.addAll(it) }

            LoadResult.Page(
                data = listMovies,
                prevKey = if (position == STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if (listMovies.isEmpty()) null else position + 1
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}