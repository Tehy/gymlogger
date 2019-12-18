package com.example.gymlogger

import android.util.Log
import java.sql.Time
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList

class Workout{


    var programName:String?=null
    var trainingName:String?=null

    var notes:String?=null // todo workout notes

    // TODO add generic cardio time var cardio: Int

    var workoutExerciseList = arrayListOf<Exercise>()   // Trainings exerciseList is base for workout, exercise can be added during workout

    val startTimeYear       = LocalDateTime.now().year
    val startTimeMonth      = LocalDateTime.now().monthValue
    val startTimeDay        = LocalDateTime.now().dayOfMonth
    val startTimeDayOfWeek  = LocalDateTime.now().dayOfWeek
    val startTimeHour       = LocalDateTime.now().hour.toString().padStart(2,'0')
    val startTimeMinute     = LocalDateTime.now().minute.toString().padStart(2,'0')
    val startTimeSecond     = LocalDateTime.now().second.toString().padStart(2,'0')

    val startMillis         = System.currentTimeMillis()

    //var workoutTimeArray:ArrayList<Int>?=null //todo redo with class ex: timer(hours, minutes, seconds)
    var workoutTime = mutableMapOf<String, String>()

    //    fun workoutLength(): ArrayList<Int> {
    fun workoutStopTime(): MutableMap<String, String>{
        //val stopTime        = LocalDateTime.now()
        val workoutDayOfWeek= this.startTimeDayOfWeek.toString()

        val workoutDate     = this.startTimeDay.toString()+"/"+this.startTimeMonth.toString()+"/"+this.startTimeYear.toString()

        val stopMillis=System.currentTimeMillis()-startMillis // delta milliseonds

        //val workoutHours    = stopTime.hour     - this.startTimeHour
        //val workoutHours    = TimeUnit.MILLISECONDS.toHours(stopMillis)//.toString().padStart(2,'0')
        val workoutHours    = TimeUnit.MILLISECONDS.toHours(stopMillis).toString().padStart(2,'0')

        //val workoutMinutes  = stopTime.minute   - this.startTimeMinute
        //val workoutMinutes  = (TimeUnit.MILLISECONDS.toMinutes(stopMillis)%60).toString().padStart(2,'0')
        val workoutMinutes  = (TimeUnit.MILLISECONDS.toMinutes(stopMillis)%60).toString().padStart(2,'0')
        //val workoutSeconds  = stopTime.second   - this.startTimeSecond //TODO remove seconds after TEST ing
        val workoutSeconds  =TimeUnit.MILLISECONDS.toSeconds(stopMillis).toString()

        workoutTime.put("dayofweek", workoutDayOfWeek)
        workoutTime.put("date", workoutDate)
        workoutTime.put("hours", workoutHours)
        workoutTime.put("minutes", workoutMinutes)
        workoutTime.put("seconds", workoutSeconds)

        /*
        Log.d("myTag", "workouttime == ${workoutTime}")
        Log.d("myTag", "workouttime dayofweek == ${workoutTime.get("dayofweek")}")
        Log.d("myTag", "workouttime date == ${workoutTime.get("date")}")
        Log.d("myTag", "workouttime hours == ${workoutTime.get("hours")}")
        Log.d("myTag", "workouttime minutes == ${workoutTime.get("minutes")}")
        Log.d("myTag", "workouttime seconds == ${workoutTime.get("seconds")}")

        Log.d("myTag", "workoutDayOfWeek == ${workoutDayOfWeek}")
        Log.d("myTag", "workoutDate == ${workoutDate}")
        Log.d("myTag", "workoutHours == ${workoutHours}")
        Log.d("myTag", "workoutMinutes == ${workoutMinutes}")
        Log.d("myTag", "workoutSeconds == ${workoutSeconds}")
         */

        return workoutTime
    }

    fun addExerciseToWorkout(name: String){
        this.workoutExerciseList.add(Exercise(name))

    }



    fun showWorkoutExercises(){

        for(i in this.workoutExerciseList){
            Log.d("my",i.name)
        }
    }

}