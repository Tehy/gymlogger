package com.example.gymlogger

import android.util.Log

object DataChangeLogger{
    var alteredData = mutableMapOf(
        "programList"   to false,
        "workoutPlan"   to false,
        "workoutList"   to false,
        "activeWorkout" to false
    )

    fun check():ArrayList<String>{  // read alteredData values if set to true, if key value=true, add key to listToWrite
                                    // used in DataWriteRead.kt DataWrite(listToWrite)
        var listToWrite= arrayListOf<String>()
        for((k,v) in this.alteredData){
            if(v==true){
                listToWrite.add(k)
            }
        }
        Log.d("myTag", "DataChangeLogger.check() -> ${listToWrite}")
        return listToWrite
    }

    fun setFalse(){
        this.alteredData = mutableMapOf(
            "programList" to false,
            "workoutPlan" to false,
            "workoutList" to false,
            "activeWorkout" to false
        )
    }
}