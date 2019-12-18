package com.example.gymlogger

import android.util.Log
import java.util.ArrayList

data class Exercise(var name: String) {
    var worksetList= arrayListOf<Workset>()

}