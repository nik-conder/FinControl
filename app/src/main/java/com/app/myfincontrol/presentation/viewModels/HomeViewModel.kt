package com.app.myfincontrol.presentation.viewModels

import android.content.Context
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
): ViewModel() {

    val test = "abcd"
    init {
        println("viewModel created")
    }
}