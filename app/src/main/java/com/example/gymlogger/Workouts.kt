package com.example.gymlogger

import android.util.Log
import com.example.gymlogger.Programs.programList

object Workouts{
    var workoutsList = arrayListOf<Workout>() // store finished workouts,

    var activeWorkout: Workout? = null
    var workoutPlan: WorkoutPlan?=null








    /*
    fun selectWorkoutAndStartWorkoutActivity(context:Context){ //TODO REDO select program block, select training workout block, use setActiveWorkOut(pName,tName), getExerciseList(pName,tName)
        var intent = Intent(context, WorkoutStartActivity::class.java)
        if (programList.isNullOrEmpty()) { // case 0 program
            Log.d("myTag", "programList is EMPTY")
            // create "new program", create "new training"
            programList.add(Program("new Program"))
            programList[0].trainingList.add(Training("new Training"))

            activeWorkout!!.programName = programList[0].programName
            activeWorkout!!.trainingName = programList[0].trainingList[0].name

            toastCenterShort(context, "New program created")

            startActivity(context, intent, null)


            //s.selProgram=0

        } else if (programList.size == 1) { // case 1 program, 0 trainings
            // case 1 program, 1 trainings
            // case 1 program, 1 trainings, 0 exercise
            // case 1 program, 1 trainings, 1 or more exercise

            Log.d("myTag", "programList.size==1")

            activeWorkout!!.programName = programList[0].programName

            if(programList[0].trainingList.isNullOrEmpty()){
                programList[0].trainingList.add(Training("new Training"))

                activeWorkout!!.trainingName = programList[0].trainingList[0].name
                startActivity(context, intent, null)
            }
            else if(programList[0].trainingList.size==1) {

                activeWorkout!!.trainingName = programList[0].trainingList[0].name
                Log.d("myTag", "STARTING intent trainingList.size==1")

                if (!(programList[0].trainingList[0].exerciseList.isNullOrEmpty())) { // check exerciseList
                    Log.d("myTag", "STARTING populated workoutList | case 1 1 1ormore")
                    // if exerciseList, add elements to workoutExerciseList

                    //activeWorkout?.workoutExerciseList?.addAll(programList[0].trainingList[0].exerciseList)
                    cloneExerciseListToWorkoutExerciseList(getExerciseList(activeWorkout!!.programName!! ,activeWorkout!!.trainingName!!))

                    startActivity(context, intent, null)

                } else {
                    Log.d("myTag", "STARTING empty exerciseList | case 1 1 0")
                    // start with empty workoutExerciseList
                    startActivity(context, intent, null)

                }
            }
            else if(programList[0].trainingList.size > 1) { // TODO FIX CRASH - IF NO TRAINING IS SELECTED -> activeWorkout.trainingName = null

                //activeWorkout!!.training = programList[0].trainingList[0]
                //Log.d("myTag", "STARTING intent trainingList.size > 1")
                //startActivity(intent)

                var TrainingNamesList = arrayListOf<String>()
                for (i in programList[0].trainingList) {
                    TrainingNamesList.add(i.name)
                }
                // DIALOG 2 START //todo rename mDialog3 to mDialog2
                val mDialog = LayoutInflater.from(context)
                    .inflate(R.layout.dialog_pick_item_spinner_layout, null)
                val mBuilder = AlertDialog.Builder(context)
                    .setView(mDialog)
                    .setTitle("Select training")

                val mAdapter = ArrayAdapter<String>(
                    context,
                    android.R.layout.simple_spinner_item,
                    TrainingNamesList
                )
                mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                val mSpinner = mDialog.spinner
                mSpinner.setAdapter(mAdapter)

                val mAlertDialog = mBuilder.show()
                Log.d("myTag", "TrainingDIALOG")
                mDialog.ok_btn.setOnClickListener {
                    Log.d("myTag", "TrainingDIALOG2")
                    mAlertDialog.dismiss()
                    Log.d("myTag", "TrainingDIALOG3")
                    val selectedTraining =
                        mSpinner.selectedItem.toString() //read spinner selection // TODO FIX null value IF USER FAILS TO PICK TRAINING
                    var indexOfSelectedTraining =
                        programList[0].trainingList.indexOf(Training(selectedTraining))

                    //Log.d("myTag", "indexOfSelectedProgram == ${indexOfSelectedTraining}")
                    activeWorkout!!.trainingName =
                        programList[0].trainingList[indexOfSelectedTraining].name


                    // CHECK EXERCISELIST
                    // TODO FIX null value IF USER FAILS TO PICK TRAINING
                    if (activeWorkout!!.trainingName!=null) {
                        if (!(programList[0].trainingList[indexOfSelectedTraining].exerciseList.isNullOrEmpty())) {
                            Log.d("myTag", "STARTING populated workoutList")
                            // if exerciseList, add elements to workoutExerciseList

                            //activeWorkout?.workoutExerciseList?.addAll(programList[0].trainingList[indexOfSelectedTraining].exerciseList)
                            //cloneExerciseListToWorkoutExerciseList(getExerciseList(activeWorkout!!.programName!! ,activeWorkout!!.trainingName!!))
                            cloneExerciseListToWorkoutExerciseList(programList[0].trainingList[indexOfSelectedTraining].exerciseList)
                            startActivity(context, intent, null)

                        }

                    } else {
                        if(activeWorkout!!.trainingName!=null){ // CHECK IF USER PICKS TRAINING FROM LIST
                            Log.d("myTag", "STARTING empty exerciseList")
                            // start with empty workoutExerciseList
                            startActivity(context, intent, null)
                        }
                        else{
                            Log.d("myTag","NOT TOASTING? 1")
                            toastCenterShort(context, "No training selected")
                        }

                    }
                }
            }


        } else if (programList.size > 1) { // case 2 or more programs

            Log.d("myTag", "programList.size > 1")

            // dialog ask user to select program

            var programNamesList = arrayListOf<String>()
            for (i in programList) {
                programNamesList.add(i.programName)
            }
            // DIALOG 2 START //todo rename mDialog3 to mDialog2
            val mDialog = LayoutInflater.from(context)
                .inflate(R.layout.dialog_pick_item_spinner_layout, null)
            val mBuilder = AlertDialog.Builder(context)
                .setView(mDialog)
                .setTitle("Select training program")

            val mAdapter = ArrayAdapter<String>(
                context,
                android.R.layout.simple_spinner_item,
                programNamesList
            )
            mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            val mSpinner = mDialog.spinner
            mSpinner.setAdapter(mAdapter)

            val mAlertDialog = mBuilder.show()
            Log.d("myTag", "program DIALOG")
            mDialog.ok_btn.setOnClickListener {
                Log.d("myTag", "program DIALOG2")
                mAlertDialog.dismiss()
                Log.d("myTag", "program DIALOG3")
                val selectedProgram =
                    mSpinner.selectedItem.toString() //read spinner selection
                var indexOfSelectedProgram=
                    programList.indexOf(Program(selectedProgram))

                Log.d("myTag", "indexOfSelectedProgram == ${indexOfSelectedProgram}")
                Log.d("myTag", "indexOfSelectedProgram == ${indexOfSelectedProgram}")

                activeWorkout!!.programName = programList[indexOfSelectedProgram].programName
                Log.d("myTag", "PROGRAM : ${activeWorkout!!.programName}")


                //todo this changed
                var i = programList.indexOf(Program(activeWorkout!!.programName!!))

                if (programList[i].trainingList.isNullOrEmpty()) { // case selected program has no trainings // TODO REDO WITH INDEXOF PROGRAMNAME
                    Log.d("myTag", "trainingList is EMPTY")
                    // if empty add "new Training"
                    //activeWorkout!!.programName?.trainingList?.add(Training("new Training"))
                    programList[i].trainingList.add(Training("new Training"))
                    activeWorkout!!.trainingName = "new Training"

                    Log.d("myTag", "NULL?? ${activeWorkout!!.programName}")


                    Log.d("myTag", "STARTING trainingList is EMPTY")
                    startActivity(context, intent, null)
                } else if (programList[i].trainingList.size == 1) { // case selected program has 1 training
                    Log.d("myTag", "trainingList.size == 1")

                    Log.d("myTag", "NULL?? 2 ${activeWorkout!!.programName}")

                    activeWorkout?.trainingName = programList[i].trainingList[0].name

                    // CHECK exerciseList
                    if (!(programList[i].trainingList[0].exerciseList.isNullOrEmpty())) {
                        Log.d("myTag", "STARTING populated workoutList | 1ormore 1 1ormore")

                        // if exerciseList, add elements to workoutExerciseList
                        //activeWorkout?.workoutExerciseList?.addAll(programList[indexOfSelectedProgram].trainingList[0].exerciseList)
                        cloneExerciseListToWorkoutExerciseList(programList[indexOfSelectedProgram].trainingList[0].exerciseList)
                        startActivity(context, intent, null)

                    } else {
                        Log.d("myTag", "STARTING empty exerciseList")
                        // start with empty workoutExerciseList
                        startActivity(context, intent, null)
                    }

                }
                else if (programList[i].trainingList.size > 1) {
                    Log.d("myTag", "trainingList.size > 1")
                    // CASE selected program has multiple trainings, dialog ask user to select training
                    //TODO ask to select training
                    //create list of traing names for spinner picker
                    var trainingNamesList = arrayListOf<String>()
                    for (j in programList[i].trainingList) {
                        trainingNamesList.add(j.name)
                    }

                    Log.d("myTag", "trainingNamesList=== ${trainingNamesList}")

                    // DIALOG 2 START //todo rename mDialog3 to mDialog2
                    val mDialog3 = LayoutInflater.from(context)
                        .inflate(R.layout.dialog_pick_item_spinner_layout, null)
                    val mBuilder3 = AlertDialog.Builder(context)
                        .setView(mDialog3)
                        .setTitle("Select training")

                    val mAdapter3 = ArrayAdapter<String>(
                        context,
                        android.R.layout.simple_spinner_item,
                        trainingNamesList
                    )
                    mAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    val mSpinner3 = mDialog3.spinner
                    mSpinner3.setAdapter(mAdapter3)

                    val mAlertDialog3 = mBuilder3.show()

                    mDialog3.ok_btn.setOnClickListener {
                        mAlertDialog3.dismiss()

                        val selectedTraining =
                            mSpinner3.selectedItem.toString() //read spinner selection // TODO FIX null value IF USER FAILS TO PICK TRAINING
                        val indexOfSelectedTraining =
                            programList[i].trainingList.indexOf(
                                Training(
                                    selectedTraining
                                )
                            )



                        activeWorkout?.trainingName=programList[i].trainingList[indexOfSelectedTraining].name


                        Log.d("myTag", "indexOfSelectedTRAINING ${indexOfSelectedProgram}")
                        Log.d("myTag", "activeWorkout!!.program!!.trainingList[indexOfSelectedProgram] ${programList[i].trainingList[indexOfSelectedProgram].name}")

                        // CHECK exerciseList
                        // TODO FIX null value IF USER FAILS TO PICK TRAINING
                        if (activeWorkout!!.trainingName!=null) {
                            if (!(programList[i].trainingList[indexOfSelectedTraining].exerciseList.isNullOrEmpty())) {
                                Log.d("myTag", "STARTING populated workoutList")
                                // if exerciseList, add elements to workoutExerciseList

                                //activeWorkout?.workoutExerciseList?.addAll(programList[0].trainingList[indexOfSelectedTraining].exerciseList)
                                //cloneExerciseListToWorkoutExerciseList(getExerciseList(activeWorkout!!.programName!! ,activeWorkout!!.trainingName!!))
                                cloneExerciseListToWorkoutExerciseList(programList[i].trainingList[indexOfSelectedTraining].exerciseList)
                                startActivity(context, intent, null)

                            }

                        } else {
                            if(activeWorkout!!.trainingName==null){ // CHECK IF USER PICKS TRAINING FROM LIST
                                Log.d("myTag", "STARTING NULL TRAINING ")
                                Log.d("myTag", "STARTING empty exerciseList")
                                // start with empty workoutExerciseList
                                startActivity(context, intent, null)
                            }
                            else{
                                Log.d("myTag","NOT TOASTING? 1")
                                toastCenterShort(context, "No training selected")
                            }

                        }

                    }

                } //SELECT TRAINING END
            }

        }// PROGRAM SELECT END
    }

     */


    /*
    fun showWorkouts(){

        for(i in this.workoutExerciseList){
            Log.d("my",i)
        }
    }
     */
}