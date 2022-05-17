package com.rgssdeveloper.relaxamento.firestore

data class DataObject(
    var label:String="",
    var urls:ArrayList<String?> = ArrayList(0),
    var gifUrls:ArrayList<String?> = ArrayList(0)
    ) {
}