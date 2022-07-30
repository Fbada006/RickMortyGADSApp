package com.gads.rickmortygadsapp

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.gads.rickmortygadsapp.databinding.ActivityMainBinding
import com.gads.rickmortygadsapp.network.createApiService
import com.gads.rickmortygadsapp.repo.CharacterRepositoryImpl
import com.gads.rickmortygadsapp.viewmodel.CharacterAdapter
import com.gads.rickmortygadsapp.viewmodel.CharacterLoadingAdapter
import com.gads.rickmortygadsapp.viewmodel.CharacterViewModel
import com.gads.rickmortygadsapp.viewmodel.CharacterViewModelFactory
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val characterAdapter = CharacterAdapter()
    private val apiService = createApiService()
    private val characterViewModel: CharacterViewModel by viewModels {
        CharacterViewModelFactory(
            repository = CharacterRepositoryImpl(apiService)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        characterViewModel.getCharacters()

        setUpRecView()
        observeViewModelCharacters()
    }

    private fun observeViewModelCharacters() {
        lifecycleScope.launch {
            characterViewModel.characterData
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collect { data ->
                    if (data != null) {
                        characterAdapter.submitData(data)
                    }
                }
        }
    }

    private fun setUpRecView() {
        binding.characterList.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = characterAdapter
            adapter = characterAdapter.withLoadStateHeaderAndFooter(
                header = CharacterLoadingAdapter { characterAdapter.retry() },
                footer = CharacterLoadingAdapter { characterAdapter.retry() }
            )
        }
    }
}