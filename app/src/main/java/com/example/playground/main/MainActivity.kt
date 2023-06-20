package com.example.playground.main

import androidx.activity.viewModels
import com.example.playground.R
import com.example.playground.databinding.ActivityMainBinding
import com.example.playground.main.base.BaseBindingActivity
import dagger.hilt.android.AndroidEntryPoint

// DataBinding, ViewBinding 연습
@AndroidEntryPoint
class MainActivity : BaseBindingActivity<ActivityMainBinding>(R.layout.activity_main) {
    private val mainActivityViewModel: MainActivityViewModel by viewModels()

    override fun initView() {
        // Init binding vm
        binding.vm = mainActivityViewModel

        initVM()
        clickListener()
    }

    private fun initVM() {
        mainActivityViewModel.isReset.observe(this) {
            if (it) {
                binding.tvState.text = "Complete Reset"
            } else {
                binding.tvState.text = "Start!"
            }
        }
    }

    private fun clickListener() {
        binding.btnReset.setOnClickListener {
            mainActivityViewModel.resetTime()
        }
    }
}
