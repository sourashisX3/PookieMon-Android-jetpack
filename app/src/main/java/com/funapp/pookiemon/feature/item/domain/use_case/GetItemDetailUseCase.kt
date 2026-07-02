package com.funapp.pookiemon.feature.item.domain.use_case

import com.funapp.pookiemon.core.config.network.ApiResult
import com.funapp.pookiemon.feature.item.domain.model.Item
import com.funapp.pookiemon.feature.item.domain.repository.ItemRepository
import javax.inject.Inject

class GetItemDetailUseCase @Inject constructor(
    private val repository: ItemRepository,
) {
    suspend operator fun invoke(id: Int, forceRefresh: Boolean = false): ApiResult<Item> {
        return repository.getItemDetail(id, forceRefresh)
    }
}
