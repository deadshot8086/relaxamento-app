package com.rgssdeveloper.relaxamento.circleimageview

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.rgssdeveloper.relaxamento.BR

class DataCircleImageView(val label: String, selected:Boolean,val image:Int): BaseObservable() {
    @get:Bindable
    var selected = selected
    set(value) {
        field = value
        notifyPropertyChanged(BR.selected)
    }
    var urls = ArrayList<String>()
    fun getRandomUrl():String{
        return urls[(0 until urls.size).random()]
    }
    var gifUrls = ArrayList<String>()
    fun getRandomGifUrl():String{
        return gifUrls[(0 until gifUrls.size).random()]
    }
    //TODO:dynamic loading of label, circle image as well
}