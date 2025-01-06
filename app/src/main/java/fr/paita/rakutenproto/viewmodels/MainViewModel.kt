package fr.paita.rakutenproto.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.paita.rakutenproto.domain.models.AppResult
import fr.paita.rakutenproto.domain.models.SearchResponse
import fr.paita.rakutenproto.domain.repositories.ProductRepository
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val productRepository: ProductRepository
): ViewModel() {

    private var _searchResult: MutableLiveData<AppResult<SearchResponse?>> = MutableLiveData()
    var searchResult: LiveData<AppResult<SearchResponse?>> = _searchResult

    fun searchFor(keyword: String) {
        viewModelScope.launch {
            productRepository.search(keyword).collect { result ->
                Log.d("PRODUCT_SEARCH",result.toString())
                _searchResult.value = result
            }
        }
    }
}