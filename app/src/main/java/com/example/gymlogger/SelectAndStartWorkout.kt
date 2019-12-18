package com.example.gymlogger

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import kotlinx.android.synthetic.main.dialog_pick_item_spinner_layout.view.*


fun selectAndStartWorkout(context: Context){ // function with better describing name
    selectProgram(context)
}

fun selectProgram(context: Context){ // nested function to select workout: program, training, exercises

    var programName=""

    val intent = Intent(context, WorkoutStartActivity::class.java)

    if (Programs.programList.isNullOrEmpty()) { // case 0 program
        Log.d("myTag", "programList isNullOrEmpty, creating 'new Program'")
        Programs.programList.add(Program("new Program"))
    }
    if (Programs.programList.size==1){          // case 1 program
        programName=Programs.programList[0].programName

        selectTraining(context, intent,  programName)
    }
    else if (Programs.programList.size>1) {     // case >1 programs

        // read programs to a list for spinner
        var programNamesList = arrayListOf<String>()
        for (i in Programs.programList) {
            programNamesList.add(i.programName)
        }
        // SPINNER DIALOG START
        val mDialog = LayoutInflater.from(context)
            .inflate(R.layout.dialog_pick_item_spinner_layout, null)
        val mBuilder = AlertDialog.Builder(context)
            .setView(mDialog)
            .setTitle("Select program")

        val mAdapter = ArrayAdapter<String>(
            context,
            android.R.layout.simple_spinner_item,
            programNamesList
        )
        mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        val mSpinner = mDialog.spinner
        mSpinner.setAdapter(mAdapter)

        val mAlertDialog = mBuilder.show()

        //Log.d("myTag", "Workouts.activeWorkout-->${Workouts.activeWorkout}")
        //Log.d("myTag", "Workouts.activeWorkout.programName-->${Workouts.activeWorkout?.programName}")
        //Log.d("myTag", "Workouts.activeWorkout.trainingName-->${Workouts.activeWorkout?.trainingName}")

        Log.d("myTag", "program DIALOG") // log spinner stages/bugtrack
        mDialog.ok_btn.setOnClickListener {
            Log.d("myTag", "program DIALOG2")
            mAlertDialog.setOnDismissListener { // run on dismiss (to halt event running for intented program flow)
                Log.d("myTag", "program DIALOG3 setOnDismissListener")

                val spinner=mSpinner.selectedItem.toString() // read spinner selection

                Log.d("myTag","program SPINNER-->${spinner}")

                if(!(spinner.isNullOrEmpty())){ // check user actually picks item from list

                    programName = spinner
                    Log.d("myTag","setOnDismissListener programName==${Workouts.activeWorkout?.programName}")

                    selectTraining(context, intent, programName)
                }
            }
            mAlertDialog.dismiss()
        }
    }
}
fun selectTraining(context: Context, intent:Intent, programName:String){

    val programIndex = getProgramIndex(programName)

    var trainingName=""

    if (programIndex>=0){ // check program selection is in Programs.programList


        if (Programs.programList[programIndex].trainingList.isNullOrEmpty()) {  // case 0 training
            Log.d("myTag", "trainingList isNullOrEmpty, creating 'new Training'")
            Programs.programList[programIndex].trainingList.add(Training("new Training"))
        }
        if (Programs.programList[programIndex].trainingList.size==1){           // case 1 training
            trainingName=Programs.programList[programIndex].trainingList[0].name

            // make sure workout is set before checking exercises // todo remove duplicate check
            if(getProgramIndex(programName)>=0){
                if(getTrainingIndex(getProgramIndex(programName), trainingName)>=0){

                    val exList = getExerciseList(programName, trainingName)

                    ifExerciseListCloneToWorkoutAndStart(context,intent,exList,programName, trainingName)

                }
            }
        }
        else if (Programs.programList[programIndex].trainingList.size>1) {      // case >1 trainings

            // read trainings to a list for spinner
            var trainingNamesList = arrayListOf<String>()
            for (i in Programs.programList[programIndex].trainingList) {
                trainingNamesList.add(i.name)
            }
            // SPINNER DIALOG START
            val mDialog = LayoutInflater.from(context)
                .inflate(R.layout.dialog_pick_item_spinner_layout, null)
            val mBuilder = AlertDialog.Builder(context)
                .setView(mDialog)
                .setTitle("Select training")

            val mAdapter = ArrayAdapter<String>(
                context,
                android.R.layout.simple_spinner_item,
                trainingNamesList
            )
            mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            val mSpinner = mDialog.spinner
            mSpinner.setAdapter(mAdapter)

            val mAlertDialog = mBuilder.show()
            Log.d("myTag", "training DIALOG") // log spinner stages/bugtrack
            mDialog.ok_btn.setOnClickListener {
                Log.d("myTag", "training DIALOG2")
                mAlertDialog.setOnDismissListener { // run on dismiss (to halt event running for intented program flow)

                    Log.d("myTag", "training DIALOG3 setOnDismissListener")

                    val spinner=mSpinner.selectedItem.toString()
                    Log.d("myTag","training SPINNER-->${spinner}")

                    trainingName = mSpinner.selectedItem.toString() //read spinner selection
                    Log.d(
                        "myTag",
                        "setOnDismissListener trainingName==${Workouts.activeWorkout?.trainingName}"
                    )

                    // make sure workout is set before checking exercises
                    if(getProgramIndex(programName)>=0){ //todo remove duplicate check
                        if(getTrainingIndex(getProgramIndex(programName), trainingName)>=0){

                            val exList = getExerciseList(programName, trainingName)

                            ifExerciseListCloneToWorkoutAndStart(context,intent,exList,programName,trainingName)

                        }
                    }
                }
                mAlertDialog.dismiss()
            }
        }

    }
}
fun ifExerciseListCloneToWorkoutAndStart(context:Context, intent:Intent,exArray:ArrayList<Exercise>, programName: String,trainingName: String){

    Log.d("myTag","ifExerciseListCloneToWorkoutAndStart programName ${programName}")
    Log.d("myTag","ifExerciseListCloneToWorkoutAndStart trainingName ${trainingName}")

    val newWorkout=Workout()
    Workouts.activeWorkout=newWorkout
    Workouts.activeWorkout!!.programName=programName
    Workouts.activeWorkout!!.trainingName=trainingName

    if(!(exArray.isNullOrEmpty())){     // TODO REMOVE LOGIC FROM FUNCTION
        for(i in exArray){
            Workouts.activeWorkout!!.addExerciseToWorkout(i.name)

            startActivity(context, intent, null)
        }
    }
    else{
        startActivity(context, intent, null)
    }

}
fun cloneExerciseListToWorkoutExerciseList(exList:ArrayList<Exercise>){ // TODO FUNCTION NOT USED
    if(!(exList.isNullOrEmpty())){     // TODO REMOVE LOGIC FROM FUNCTION
        for(i in exList){
            Workouts.activeWorkout!!.addExerciseToWorkout(i.name)

        }
    }

}
fun setActiveWorkout(programName:String,trainingName:String){ //
    try {
        Workouts.activeWorkout = Workout()
        Workouts.activeWorkout!!.programName = programName
        Workouts.activeWorkout!!.trainingName = trainingName
        cloneExerciseListToWorkoutExerciseList(getExerciseList(programName, trainingName))
    }catch (e: Exception) {
        Log.d("myTag","ERROR setActiveWorkout(): ${e}")
    }
    //activeWorkout!!.workoutExerciseList.addAll(getExerciseList(programName,trainingName)) //
}

fun getExerciseList(programName:String,trainingName:String):ArrayList<Exercise> {
    return Programs.programList[getProgramIndex(programName)].trainingList[getTrainingIndex(getProgramIndex(programName),trainingName)].exerciseList
}