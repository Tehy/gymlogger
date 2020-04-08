package com.example.gymlogger

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gymlogger.Programs.programList
import com.example.gymlogger.Workouts.activeWorkout


import kotlinx.android.synthetic.main.activity_programs.*
import kotlinx.android.synthetic.main.activity_programs.fabAddBtn
import kotlinx.android.synthetic.main.activity_programs.programsRV
import kotlinx.android.synthetic.main.activity_workout_start.*
import kotlinx.android.synthetic.main.dialog_ask_yes_no.view.*
import kotlinx.android.synthetic.main.dialog_rename_layout.view.*
import java.util.concurrent.TimeUnit


class WorkoutStartActivity : AppCompatActivity() {

    /*TODO Feature, case workout length < 10 min workout is stopped -> ask to save/discard workout ??
        if workout length > 180min -> alert user to stop workout, dialog ask workout stop time.
        8/4/20 no change.
     */

    override fun onStop() {
        super.onStop()
        Log.d("myTag", "WorkoutStartActivity onSTOP")
        if(writeTime+timeDelta<System.currentTimeMillis()){
            DataWrite(this, DataChangeLogger.check())
            writeTime=System.currentTimeMillis()
            DataChangeLogger.setFalse()
        }
    }
    override fun onPause() {
        super.onPause()
        Log.d("myTag", "WorkoutStartActivity onPAUSE")
        if(writeTime+timeDelta<System.currentTimeMillis()){
            DataWrite(this, DataChangeLogger.check())
            writeTime=System.currentTimeMillis()
            DataChangeLogger.setFalse()
        }
    }

    override fun onBackPressed() {  // Clicking back
        Log.d("myTag","WorkoutStartActivity onBackPressed")

        val intent= Intent(this, MainActivity::class.java)
        //startActivity(intent)

        if(activeWorkout!=null){    // IF active workout, asks 'yes'/'no' to stop the workout.
                                    // 'yes' logs workout time and saves workout to Workouts.workoutList
                                    // 'no' starts MainActivity

            Log.d("myTag","activeWorkout!=null -> dialog")
            //Log.d("myTag","backpress activeWorkout")

            val title="Stop workout?"
            val mDialog = LayoutInflater.from(this)
                .inflate(R.layout.dialog_ask_yes_no, null)
            mDialog.yes_btn.text="Yes"
            mDialog.no_btn.text="No"
            val mBuilder = AlertDialog.Builder(this)
                .setView(mDialog)
                .setTitle(title)
            val mAlertDialog = mBuilder.show()

            mDialog.yes_btn.setOnClickListener {
                mAlertDialog.dismiss()
                Log.d("myTag","dialog 'stop workout?' -> YES")
                if(Workouts.workoutPlan==null){
                    Log.d("myTag","Workouts.workoutPlan==null")
                    Workouts.workoutPlan=WorkoutPlan()
                    Log.d("myTag","Workouts.workoutPlan=WorkoutPlan()")
                    updateWorkoutPlan()
                }
                else{
                    updateWorkoutPlan()
                }
                Log.d("myTag","workoutPlan UPDATED")
                //Log.d("myTag","LASTWORKOUT === ${Workouts.workoutPlan!!.lastTrainingName}")
                //Log.d("myTag","NEXTWORKOUT === ${Workouts.workoutPlan!!.nextTrainingName}")

                saveWorkout(this) // Stops workout time, saves workout to Workouts.workoutsList, deletes "activeWorkoutFile"

                /* TODO IF workoutExerciseList != exerciseList ask to save additional workoutExercises to exerciseList ??
                    8/4/20 DUPLICATE SIMILAR.
                 */
                Log.d("myTag","STOPPING WORKOUT program     == ${activeWorkout?.programName}")
                Log.d("myTag","STOPPING WORKOUT training    == ${activeWorkout?.trainingName}")

                var exList=getExerciseList(activeWorkout!!.programName!!, activeWorkout!!.trainingName!!)

                // if user adds exercises to workout && program->training->exerciseList is empty
                if(exList.isNullOrEmpty() && !(activeWorkout!!.workoutExerciseList.isNullOrEmpty())) {
                    val title2 = "Save workout exercises to ${activeWorkout!!.trainingName} training?"
                    val mDialog2 = LayoutInflater.from(this)
                        .inflate(R.layout.dialog_ask_yes_no, null)
                    val mBuilder2 = AlertDialog.Builder(this)
                        .setView(mDialog2)
                        .setTitle(title2)
                    val mAlertDialog2 = mBuilder2.show()

                    mDialog2.yes_btn.setOnClickListener {
                        mAlertDialog2.dismiss()

                        //exList.addAll(activeWorkout!!.workoutExerciseList)

                        for(i in Workouts.activeWorkout!!.workoutExerciseList){
                            val progIndex= getProgramIndex(activeWorkout!!.programName!!)
                            val trainIndex= getTrainingIndex(progIndex, activeWorkout!!.trainingName!!)

                            programList[progIndex].trainingList[trainIndex].exerciseList.add(Exercise(i.name))

                        }
                        Log.d("myTag", "SAVED workoutExercise to exerciseList")
                        activeWorkout = null
                        DataChangeLogger.alteredData["programList"]=true
                        /* todo MOVE DataChangeLogger.alteredData["programList"]=true <- this should be in ifExerciseListCloneToWorkoutAndStart()
                            8/4/20 no change.
                         */
                        startActivity(intent)
                    }
                    mDialog2.no_btn.setOnClickListener {
                        mAlertDialog2.dismiss()
                        activeWorkout = null
                        startActivity(intent)
                    }
                }
                else{
                    activeWorkout = null
                    startActivity(intent)
                }
            }
            mDialog.no_btn.setOnClickListener {
                mAlertDialog.dismiss()
                Log.d("myTag","start intent MainActivity WORKOUT remains ACTIVE")
                startActivity(intent)
            }
        }
        else{

            startActivity(intent)
        }

    }// onBackPressed END

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_workout_start)

        val intent= Intent(this, MainActivity::class.java)

        // check workouts program if false
        /* TODO BUG SelectAndStartWorkout escapes to WorkoutStartActivity with null value
            8/4/20 SOLVED.
         */
        if(getProgramIndex(activeWorkout!!.programName!!)==-1){
            finish()
            startActivity(intent)
        }
        else if(getTrainingIndex(getProgramIndex(activeWorkout!!.programName!!), activeWorkout!!.trainingName!!)==-1){
            finish()
            startActivity(intent)
        }


        DataChangeLogger.alteredData["activeWorkout"]=true
        Log.d("myTag","DataChangeLogger.alteredData[\"activeWorkout\"] == ${DataChangeLogger.alteredData["activeWorkout"]}")

        //programName.text="Workout exercises"
        /* todo <-- bad naming, new layout?->clutter of layouts. what do? OK for now
            8/4/20 no change.
         */
        trainingTV.text= activeWorkout!!.trainingName
        val startTime= "${activeWorkout!!.startTimeHour}:${activeWorkout!!.startTimeMinute}"
        workout_start_time.text=startTime

        //setTitle("${activeWorkout?.trainingName}")
        setTitle("Workout")
        // set navigation tracker to null
        s.setNull()

        /*
        if(programList.isNullOrEmpty()) {
            setContentView(R.layout.activity_workout_start)
            /* TODO if no programs use different layouts ?
                8/4/20 no change. unclear.
            */
        }
        */

        Log.d("myTag","STARTING activeWorkout programName ${activeWorkout!!.programName}")
        Log.d("myTag","STARTING activeWorkout trainingName ${activeWorkout!!.trainingName}")

        if(activeWorkout!!.workoutExerciseList.isNullOrEmpty()){
            toastCenterShort(this, "Click + to add exercises")
        }
        // setup recyclerview
        val recyclerViewProgram = programsRV as RecyclerView
        recyclerViewProgram.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        val adapter = AdapterWorkoutStart(activeWorkout!!.workoutExerciseList)
        recyclerViewProgram.adapter = adapter


        fabAddBtn.setOnClickListener{
            addToList(activeWorkout!!, this)
            /* TODO redo addToList to add exercise to workout
                8/4/20 why?
             */
        }
        fabAddNotesBtn.setOnClickListener{
            addWorkoutNote(activeWorkout!!, this)
        }

    }//onCreate END
    fun addToList(workout: Workout, con:Context){
        /* TODO Don't delete this?
            data changed here while working out should not be saved during workout?
             save on end?
            8/4/20 unclear.
         */

        val name="exercise"

        val mDialog = LayoutInflater.from(this)
            .inflate(R.layout.dialog_rename_layout, null)    //dialog to add Program
        mDialog.insert_name_btn.text="Add"
        val mBuilder = AlertDialog.Builder(this)
            .setView(mDialog)
            .setTitle("Add "+name)
        val mAlertDialog = mBuilder.show()
        mDialog.insert_name_btn.setOnClickListener {
            mAlertDialog.dismiss()
            val objName: String = mDialog.insert_name.text.toString()   // read input

            if (objName.length > 0 && objName.length < 20) { // filter input
                if(workout.workoutExerciseList.indexOf(Exercise(objName))==-1) {
                    intent = Intent(con, WorkoutStartActivity::class.java)

                    workout.addExerciseToWorkout(objName)
                    Log.d("myTag","Exercise '${objName}' added to workoutExerciseList!")
                    DataChangeLogger.alteredData["activeWorkout"]=true

                    toastCenterShort(this, "Added successfully!")

                    finish()
                    startActivity(intent)
                }
                else{
                    toastCenterShort(this,"Name has to be unique")
                }

            } else {
                toastCenterShort(this,"Name has to be 1-20 characters long")
            }
        }
    }
}


