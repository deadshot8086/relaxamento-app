package com.rgssdeveloper.relaxamento.viewmodel

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

//TODO: Notification.MediaStyle maybe?
class ViewModelMusicPlay(val time:String): ViewModel() {
    private val TAG by lazy { this::class.java.simpleName }
    val statePlay: MutableLiveData<Boolean> = MutableLiveData<Boolean>(true)
    init{
        //init
    }
    fun onPlayButtonClick(view : View){
        statePlay.value = statePlay.value!!.not()
    }
}