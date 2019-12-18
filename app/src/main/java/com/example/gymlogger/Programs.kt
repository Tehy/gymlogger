package com.example.gymlogger

import android.util.Log

object Programs{
    var programList = arrayListOf<Program>()

    fun showPrograms(){

        for(i in this.programList){
            Log.d("my",i.programName)
        }
    }
}