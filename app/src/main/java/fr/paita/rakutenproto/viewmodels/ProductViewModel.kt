package fr.paita.rakutenproto.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.paita.rakutenproto.domain.models.AppResult
import fr.paita.rakutenproto.domain.models.ProductDetails
import fr.paita.rakutenproto.domain.repositories.ProductRepository
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ProductViewModel @Inject constructor(
    private val productRepository: ProductRepository
): ViewModel() {

    private var _detailsResult: MutableLiveData<AppResult<ProductDetails?>> = MutableLiveData()
    var detailsResult: LiveData<AppResult<ProductDetails?>> = _detailsResult

    fun getDetails(productId: Long) {
        viewModelScope.launch {
            productRepository.getDetails(productId).collect { result ->
                _detailsResult.value = result
            }
        }
    }
}