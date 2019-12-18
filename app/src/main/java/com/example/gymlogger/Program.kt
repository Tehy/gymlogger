package com.example.gymlogger

import android.util.Log
import com.example.gymlogger.Training

data class Program(var programName: String) {
    var trainingList = arrayListOf<Training>()

    fun showTrainings(){
        for(i in this.trainingList){
            Log.d("myTag",i.name)
        }
    }

}
