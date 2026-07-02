package com.funapp.pookiemon.feature.item.domain.repository

import com.funapp.pookiemon.core.config.network.ApiResult
import com.funapp.pookiemon.feature.item.domain.model.Item

interface ItemRepository {
    suspend fun getItemList(limit: Int, offset: Int, forceRefresh: Boolean = false): ApiResult<List<Item>>
    suspend fun getItemDetail(id: Int, forceRefresh: Boolean = false): ApiResult<Item>
}
