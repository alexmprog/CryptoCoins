package com.alexmprog.cryptocoins.data.coins.repository

import androidx.paging.PagingState
import app.cash.paging.PagingSource
import com.alexmprog.common.utils.resource.Resource

class ResourcePagingResource<T : Any>(
    private val pagingData: suspend (page: Int, pageSize: Int) -> Resource<List<T>, com.alexmprog.common.utils.resource.Error>
) : PagingSource<Int, T>() {

    override fun getRefreshKey(state: PagingState<Int, T>): Int? = null

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> =
        (params.key ?: 1).let { _page ->
            pagingData(_page, params.loadSize).run {
                when (this) {
                    is Resource.Success -> {
                        LoadResult.Page(
                            data = data,
                            prevKey = _page.takeIf { it > 1 }?.dec(),
                            nextKey = _page.takeIf { data.size >= params.loadSize }?.inc()
                        )
                    }

                    is Resource.Failure -> LoadResult.Error(IllegalStateException("$this type of [Result] is not allowed here"))
                }
            }
        }
}
