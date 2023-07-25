package com.example.movies_app.presentation.ui

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.movies_app.R
import com.example.movies_app.databinding.ActivityMainBinding
import com.example.movies_app.utils.NetworkUtils
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var networkUtils: NetworkUtils

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navController = findNavController(R.id.nav_host_fragment)
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        binding.toolbar.setupWithNavController(navController, appBarConfiguration)
        handleNetwork()
    }

    private fun handleNetwork() {
        networkUtils.getNetworkLiveData().observe(this, Observer { isConnected: Boolean ->
            if (!isConnected) {
                with(binding) {
                    textViewNetworkStatus.text = "No Internet Connection"
                    networkStatusLayout.visibility = View.VISIBLE
                    networkStatusLayout.setBackgroundColor(
                        ResourcesCompat.getColor(
                            resources,
                            R.color.colorStatusNotConnected,
                            null
                        )
                    )
                }
            } else {
                with(binding) {
                    textViewNetworkStatus.text = "Conncected"
                    networkStatusLayout.setBackgroundColor(
                        ResourcesCompat.getColor(resources, R.color.colorStatusConnected, null)
                    )
                    networkStatusLayout.animate().alpha(1f).setDuration(ANIMATION_DURATION.toLong())
                        .setStartDelay(ANIMATION_DURATION.toLong()).setListener(
                            object : AnimatorListenerAdapter() {
                                override fun onAnimationEnd(animation: Animator) {
                                    super.onAnimationEnd(animation)
                                    networkStatusLayout.visibility = View.GONE
                                }
                            }
                        )

                }
            }
        })
    }

}

private const val ANIMATION_DURATION = 1000