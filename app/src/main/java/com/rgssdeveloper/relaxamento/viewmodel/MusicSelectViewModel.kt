package com.rgssdeveloper.relaxamento.viewmodel

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rgssdeveloper.relaxamento.R
import com.rgssdeveloper.relaxamento.circleimageview.DataCircleImageView

class ViewModelMusicSelect : ViewModel() {
    private val TAG by lazy { this::class.java.simpleName }
    val navigateToPlayMusic: MutableLiveData<Boolean> = MutableLiveData<Boolean>(false)
    var selected = R.id.circle_image_widget_1
    val circleImageView1 = DataCircleImageView("forest",true,R.drawable.forest)
    val circleImageView2 = DataCircleImageView("ocean",false,R.drawable.ocean)
    val circleImageView3 = DataCircleImageView("rain",false,R.drawable.rain)
    val circleImageView4 = DataCircleImageView("winter",false,R.drawable.winter)
    val circleImageView5 = DataCircleImageView("campfire",false,R.drawable.campfire)
    val circleImageView6 = DataCircleImageView("waterfall",false,R.drawable.waterfall)
    val getDataFromId = mapOf<Int,DataCircleImageView>(
        R.id.circle_image_widget_1 to circleImageView1,
        R.id.circle_image_widget_2 to circleImageView2,
        R.id.circle_image_widget_3 to circleImageView3,
        R.id.circle_image_widget_4 to circleImageView4,
        R.id.circle_image_widget_5 to circleImageView5,
        R.id.circle_image_widget_6 to circleImageView6,)
    init {
        //init
    }
    fun onCircleImageViewClick(view: View){

        if(selected!=view.id){
            getDataFromId.getValue(selected).selected=false
            selected = view.id
            getDataFromId.getValue(selected).selected=true
//            Log.i(TAG,getDataFromId.getValue(selected).urls[0])
//            Log.i(TAG,getDataFromId.getValue(selected).gifUrls[0])
        }
    }
    fun onStartButtonClick(view:View){
        navigateToPlayMusic.value=true
    }
    fun onNavigatedToPlayMusic(){
        navigateToPlayMusic.value=false
    }
}