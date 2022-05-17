package com.rgssdeveloper.relaxamento.firestore

import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class Dao {
    private val TAG by lazy { this::class.java.simpleName }
    private val firestore = Firebase.firestore
    suspend fun getDataFromFireStore(): ArrayList<DataObject> {
        val result: ArrayList<DataObject> = ArrayList(0)
        try{
            val tokenReference = firestore.collection("data")
            val tokenSnapshot = tokenReference.get().await()
            tokenSnapshot.forEach{doc ->
                result.add(doc.toObject<DataObject>())
            };
            Log.i(TAG,"done")
        }catch (e : Exception){
            Log.e(TAG,e.toString())
        }
        return result
    }

}