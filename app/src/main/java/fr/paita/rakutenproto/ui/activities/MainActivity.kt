package fr.paita.rakutenproto.ui.activities

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import fr.paita.rakutenproto.databinding.ActivityMainBinding
import fr.paita.rakutenproto.domain.models.AppResult
import fr.paita.rakutenproto.domain.models.Product
import fr.paita.rakutenproto.ui.adapters.ProductAdapter
import fr.paita.rakutenproto.ui.adapters.ProductListener
import fr.paita.rakutenproto.viewmodels.MainViewModel


@AndroidEntryPoint
class MainActivity : BaseActivity(), ProductListener {
    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels()
    private lateinit var productAdapter: ProductAdapter


    private val searchHandler = Handler(Looper.getMainLooper())
    private val searchDelay = 1000L // Add a delay before launching the search call. It will run if the user stop writing for 1 second
    private val searchRunnable = Runnable { doResearch() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpUI()
    }

    private fun setUpUI() {
        initRecyclerViews()
        setUIListeners()
        setObservers()
    }

    private fun setUIListeners() {
        binding.searchEditText.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // reset searchHandler
                searchHandler.removeCallbacks(searchRunnable)
                searchHandler.postDelayed(searchRunnable, searchDelay)
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun initRecyclerViews() {
        productAdapter = ProductAdapter(this)
        binding.productSearchRecyclerView.adapter = productAdapter
        binding.productSearchRecyclerView.setHasFixedSize(true)
    }

    private fun setObservers() {
        mainViewModel.searchResult.observe(this) {
            when (it) {
                is AppResult.Success -> {
                    setProducts(it.data?.products)
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

    private fun doResearch() {
        mainViewModel.searchFor(binding.searchEditText.text.toString())
    }

    private fun setProducts(products: List<Product>?) {
        // set visibility of recyclerview and empty message
        binding.productSearchRecyclerView.visibility = if (products.isNullOrEmpty()) View.GONE else View.VISIBLE
        binding.messageTextView.visibility = if (products.isNullOrEmpty()) View.VISIBLE else View.GONE

        productAdapter.setData(products)

    }

    override fun onProductClick(productId: Long) {
        // Option 1: Fetch product details then navigate to details activity

        // Option 2: navigate to details activity then fetch product details
        startActivity(ProductDetailActivity.newIntent(
            this,
            productId
        ))
    }
}