package com.example.gymlogger

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import java.io.File
import java.lang.Error

val workoutsFile="workoutsFile"
val programsFile="programsFile"
val workoutPlanFile="workoutPlanFile"
val activeWorkoutFile = "activeWorkoutFile"

fun DataWrite(context:Context, listOfDataToWrite:ArrayList<String>) {
/* TODO datawrite("dataChangedName")
    8/4/20 why? datawrite([listOfDataToWrite]) works fine
 */
    if(!(listOfDataToWrite.isNullOrEmpty())){
        for(i in listOfDataToWrite){
            when(i){
                "workoutPlan"->{
                    try {
                        var gson = Gson()

                        var json = (gson.toJson(Workouts.workoutPlan))
                        //Log.d("myTag","JSON GSON === ${json}")

                        context.openFileOutput(workoutPlanFile, Context.MODE_PRIVATE).use {
                            it.write(json.toByteArray())
                        }
                        Log.d("myTag", "workoutPlanFile WRITTEN")
                    }
                    catch (e: Error){
                        Log.d("myTag", "ERROR writing workoutPlanFile ${e}")
                    }
                }
                "programList"->{
                    try {
                        var gson = Gson()
                        var json = ""
                        for (j in Programs.programList) {
                            json = json.plus(gson.toJson(j)) + "\n"
                            //Log.d("myTag","JSON GSON === ${json}")
                        }
                        context.openFileOutput(programsFile, Context.MODE_PRIVATE).use {
                            it.write(json.toByteArray())
                        }
                        Log.d("myTag", "programsFile WRITTEN")
                    }
                    catch (e: Error){
                        Log.d("myTag", "ERROR writing programsFile ${e}")
                    }
                }
                "workoutList"->{
                    try {
                        var gson = Gson()
                        var json = ""
                        for (j in Workouts.workoutsList) {
                            json = json.plus(gson.toJson(j)) + "\n"
                            //Log.d("myTag","JSON GSON === ${json}")
                        }
                        context.openFileOutput(workoutsFile, Context.MODE_PRIVATE).use {
                            it.write(json.toByteArray())
                        }
                        Log.d("myTag", "workoutsFile WRITTEN")
                    }
                    catch (e: Error){
                        Log.d("myTag", "ERROR writing workoutsFile ${e}")
                    }
                }
                "activeWorkout"->{
                    try {
                        var gson = Gson()

                        var json = (gson.toJson(Workouts.activeWorkout))
                        //Log.d("myTag","JSON GSON === ${json}")

                        context.openFileOutput(activeWorkoutFile, Context.MODE_PRIVATE).use {
                            it.write(json.toByteArray())
                        }
                        Log.d("myTag", "activeWorkoutFile WRITTEN")
                    }
                    catch (e: Error){
                        Log.d("myTag", "ERROR writing activeWorkoutFile ${e}")
                    }
                }
            }
        }
    }
}

fun DataRead(context:Context) {
    var errMsg="READ ERROR "
    val folder=context.filesDir
    var file1 = File(folder, workoutsFile)
    var file2 = File(folder, programsFile)
    var file3 = File(folder, workoutPlanFile)
    var file4 = File(folder, activeWorkoutFile)
    var file1Exists = file1.exists()
    var file2Exists = file2.exists()
    var file3Exists = file3.exists()
    var file4Exists = file4.exists()

    //Log.d("myTag", "Checking ${fileName1}")
    if(file1Exists){
        var gson=Gson()
        //Log.d("myTag", "Reading ${fileName1}")
        for(line in context.openFileInput(workoutsFile).bufferedReader().use { it.readLines() }){
            var json = line
            //Log.d("myTag", "json toString()===${json.toString()}")
            if(gson.fromJson(json, WorkoutPlan().javaClass)!=null) {
                var wo:Workout=gson.fromJson(json, Workout().javaClass)
                Workouts.workoutsList.add(wo)
            }
            else{
                Log.d("myTag", "GSON json == ${json} ")
                Log.d("myTag", "GSON ERROR readin Workout() object")
            }

        }
        Log.d("myTag", "-->${workoutsFile} READ")
    }
    else{
        Log.d("myTag", "${errMsg} ${workoutsFile} not found")
    }
    //Log.d("myTag", "Checking ${fileName2}")
    if(file2Exists){
        var gson=Gson()
        //Log.d("myTag", "Reading ${fileName2}")
        for(line in context.openFileInput(programsFile).bufferedReader().use { it.readLines() }){
            var json = line
            var str="\\{\"programName\":\""
            var progName=json.split(str.toRegex(),0)[1]
            var progNameSplit=progName.split("\"")[0]

            if(gson.fromJson(json, WorkoutPlan().javaClass)!=null) {
                var p:Program=gson.fromJson(json, Program(progNameSplit).javaClass)
                Programs.programList.add(p)
            }
            else{
                Log.d("myTag", "GSON json == ${json} ")
                Log.d("myTag", "GSON ERROR readin Program() object")
            }

        }
        Log.d("myTag", "-->${programsFile} READ")
    }
    else{
        Log.d("myTag", "${errMsg} ${programsFile} not found")
    }
    if(file3Exists){

        var gson=Gson()
        //Log.d("myTag", "Reading ${fileName2}")
        for(line in context.openFileInput(workoutPlanFile).bufferedReader().use { it.readLines() }){
            var json = line
            if(gson.fromJson(json, WorkoutPlan().javaClass)!=null) {

                //Log.d("myTag", "JSON-->${json}")

                var wop: WorkoutPlan = gson.fromJson(json, WorkoutPlan().javaClass)
                Workouts.workoutPlan = wop
                Log.d("myTag", "-->${workoutPlanFile} READ")

                /*TODO add updateworkoutplan() after dataread
                    8/4/20 no change
                 */
            }
            else{
                Log.d("myTag", "GSON json == ${json} ")
                file3.delete()
                Log.d("myTag", "GSON ERROR ${workoutPlanFile} DELETED")
            }
        }
    }
    else{
        Log.d("myTag", "${errMsg} ${workoutPlanFile} not found")
    }
    if(file4Exists){

        var gson=Gson()
        for(line in context.openFileInput(activeWorkoutFile).bufferedReader().use { it.readLines() }){
            var json = line
            if(gson.fromJson(json, Workout().javaClass)!=null){
                var activeWorkout:Workout=gson.fromJson(json, Workout().javaClass)
                Workouts.activeWorkout=activeWorkout
                Log.d("myTag", "-->${activeWorkoutFile} READ")
            }
            else{
                Log.d("myTag", "GSON json == ${json} ")
                file4.delete()
                Log.d("myTag", "GSON ERROR ${activeWorkoutFile} DELETED")
            }
            /*TODO add updateworkoutplan() after dataread
                8/4/20 no change
             */
        }
    }
    else{
        Log.d("myTag", "${errMsg} ${activeWorkoutFile} not found")
    }

}