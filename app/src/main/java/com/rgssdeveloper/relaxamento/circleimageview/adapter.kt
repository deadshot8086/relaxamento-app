package com.rgssdeveloper.relaxamento.circleimageview

import androidx.databinding.BindingAdapter

@BindingAdapter("civ_border_width")
fun CircleImageView.civ_border_width(size:Int){
    this.borderWidth=size
}

@BindingAdapter("android:src")
fun CircleImageView.setImageViewResource(resource: Int) {
    this.setImageResource(resource)
}