package com.rgssdeveloper.relaxamento.viewmodelFactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rgssdeveloper.relaxamento.viewmodel.ViewModelMusicPlay

class MusicPlayViewModelFactory(private val arg:String): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ViewModelMusicPlay::class.java)) {
            return modelClass.getConstructor(String::class.java).newInstance(arg)
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}