package com.example.gymlogger

import android.util.Log

data class Training(var name: String){
    var exerciseList = arrayListOf<Exercise>()

    fun showexercises(){
        for(i in this.exerciseList){
            Log.d("myTag",i.name)
        }
    }
}