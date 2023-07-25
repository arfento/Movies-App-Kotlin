package com.example.movies_app.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.movies_app.BR
import com.example.movies_app.R
import com.example.movies_app.databinding.FragmentHomeBinding
import com.example.movies_app.domain.entities.Movie
import com.example.movies_app.presentation.adapter.HomeAdapter
import com.example.movies_app.presentation.viewmodel.MovieViewModel
import com.example.movies_app.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {

    @Inject
    lateinit var adapter: HomeAdapter

    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!

    private val viewModel: MovieViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false).apply {
            setVariable(BR.vm, viewModel)
            lifecycleOwner = viewLifecycleOwner
        }
        handleResults()
        initUiElements()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun handleResults() {
        lifecycleScope.launch {
            viewModel.stateFlow.collect {
                when (it) {
                    is Resource.Loading -> showLoading(true)
                    is Resource.Success -> {
                        handleResults(it.data)
                        showLoading(false)
                    }
                    is Resource.Error -> {
                        showError(it.message)
                        showLoading(false)
                    }
                }
            }
        }
    }

    private fun initUiElements() {
        binding.recyclerView.adapter = adapter
    }

    private fun handleResults(movies: List<Movie>) {
        if (movies.isEmpty()) {
            with(binding) {
                recyclerView.visibility = View.GONE
                emptyView.visibility = View.VISIBLE
                emptyView.text = getString(R.string.empty_list)
            }
        } else {
            with(binding) {
                recyclerView.visibility = View.VISIBLE
                emptyView.visibility = View.GONE
            }
            adapter.submitList(movies)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.swipeRefreshLayout.isRefreshing = isLoading
    }

    private fun showError(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }
}