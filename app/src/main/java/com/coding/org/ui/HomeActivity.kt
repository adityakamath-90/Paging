package com.coding.org.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.coding.`fun`.databinding.ActivityHomeBinding
import com.coding.org.ui.presentation.MovieAdapter
import com.coding.org.ui.presentation.ViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeActivity : ComponentActivity() {

    private lateinit var binding: ActivityHomeBinding
    private val viewModel : ViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = MovieAdapter()
        binding.listView.layoutManager  = LinearLayoutManager(this@HomeActivity)
        binding.listView.adapter =adapter
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getMovieList() // triggers Pager
                viewModel.movies.collectLatest {
                    Log.i("MOVIES", "📦 Submitting PagingData to adapter")
                    adapter.submitData(it)
                }
            }
        }

        // Optional: observe load state (e.g., for showing loading spinners or retry buttons)
        lifecycleScope.launch {
            adapter.loadStateFlow.collectLatest { loadStates ->
                Log.i("MOVIES", "🔄 LoadState: ${loadStates.refresh}")
            }
        }
    }
}
