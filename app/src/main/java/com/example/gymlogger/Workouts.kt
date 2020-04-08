package com.example.gymlogger

import android.util.Log
import com.example.gymlogger.Programs.programList

object Workouts{
    var workoutsList = arrayListOf<Workout>() // store finished workouts,
    var activeWorkout: Workout? = null
    var workoutPlan: WorkoutPlan?=null
}