package fr.paita.rakutenproto.ui.activities

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.text.method.LinkMovementMethod
import androidx.activity.viewModels
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import fr.paita.rakutenproto.R
import fr.paita.rakutenproto.databinding.ActivityProductDetailBinding
import fr.paita.rakutenproto.domain.models.AppResult
import fr.paita.rakutenproto.domain.models.ProductDetails
import fr.paita.rakutenproto.ui.adapters.ViewPagerAdapter
import fr.paita.rakutenproto.ui.utils.setSafeOnClickListener
import fr.paita.rakutenproto.viewmodels.ProductViewModel

@AndroidEntryPoint
class ProductDetailActivity: BaseActivity() {
    private lateinit var binding: ActivityProductDetailBinding
    private val productViewModel: ProductViewModel by viewModels()

    private var productDetails: ProductDetails? = null
    private lateinit var viewPagerAdapter: ViewPagerAdapter

    companion object {
        const val productIdKey = "product_id"

        fun newIntent(context: Context, productId: Long): Intent {
            return Intent(context, ProductDetailActivity::class.java).apply {
                putExtra(productIdKey, productId)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        intent.getLongExtra(productIdKey, -1L).takeIf { it != -1L }?.let {
            productViewModel.getDetails(it)
        } ?: run {
            // Handle error
        }

        initViewPager()
        setUIListeners()
        setObservers()
    }

    private fun setUIListeners() {
        binding.backButton.setSafeOnClickListener {
            finish()
        }
    }

    private fun setObservers() {
        productViewModel.detailsResult.observe(this) {
            when (it) {
                is AppResult.Success -> {
                    productDetails = it.data
                    setUpUI()
                }

                is AppResult.Error -> {
                    //show Error
                }
                is AppResult.Loading -> {
                    //show a loader
                }
                is AppResult.Initial -> {}
            }
        }
    }

    private fun initViewPager() {
        viewPagerAdapter = ViewPagerAdapter()
        binding.imagePager.adapter = viewPagerAdapter
        TabLayoutMediator(binding.tabIndicator, binding.imagePager) { tab, position  -> }.attach()
    }

    @SuppressLint("SetTextI18n")
    private fun setUpUI() {
        productDetails?.let { product ->
            binding.productTitle.text = product.headline
            binding.nbReviewsTextView.text = "${product.nbReviews}"
            binding.averageNoteTextView.text = getString(R.string.note, product.score)
            binding.newBestPriceTextView.text = "${product.newBestPrice} €"
            binding.usedBestPriceTextView.text = "${product.usedBestPrice} €"
            binding.productDescription.text = Html.fromHtml(product.description, Html.FROM_HTML_MODE_LEGACY)
            binding.productDescription.movementMethod= LinkMovementMethod.getInstance()
            viewPagerAdapter.setData(product.imagesUrls)
        }
    }

}