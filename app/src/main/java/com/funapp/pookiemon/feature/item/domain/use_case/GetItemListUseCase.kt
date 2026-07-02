package com.funapp.pookiemon.feature.item.domain.use_case

import com.funapp.pookiemon.core.config.network.ApiResult
import com.funapp.pookiemon.feature.item.domain.model.Item
import com.funapp.pookiemon.feature.item.domain.repository.ItemRepository
import javax.inject.Inject

class GetItemListUseCase @Inject constructor(
    private val repository: ItemRepository,
) {
    companion object {
        const val PAGE_SIZE = 20
    }

    suspend operator fun invoke(offset: Int = 0, forceRefresh: Boolean = false): ApiResult<List<Item>> {
        return repository.getItemList(PAGE_SIZE, offset, forceRefresh)
    }
}
