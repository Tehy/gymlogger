package com.example.gymlogger

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.NumberPicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gymlogger.Workouts.activeWorkout


import kotlinx.android.synthetic.main.activity_programs.*
import kotlinx.android.synthetic.main.activity_workout_view_exercise.*
import kotlinx.android.synthetic.main.dialog_add_workset.view.*
import kotlinx.android.synthetic.main.dialog_rename_layout.view.*
import kotlinx.android.synthetic.main.dialog_workset_picker_layout.*
import kotlinx.android.synthetic.main.dialog_workset_picker_layout.view.*


class WorkoutViewExerciseActivity : AppCompatActivity() {
    // Check data altered
    override fun onStop() {
        super.onStop()
        Log.d("myTag", "WorkoutViewExerciseActivity onSTOP")
        if(writeTime+timeDelta<System.currentTimeMillis()){
            DataWrite(this, DataChangeLogger.check())
            writeTime=System.currentTimeMillis()
            DataChangeLogger.setFalse()
        }
    }
    override fun onPause() {
        super.onPause()
        Log.d("myTag", "WorkoutViewExerciseActivity onPAUSE")
        if(writeTime+timeDelta<System.currentTimeMillis()){
            DataWrite(this, DataChangeLogger.check())
            writeTime=System.currentTimeMillis()
            DataChangeLogger.setFalse()
        }
    }
    //
    override fun onBackPressed() {
        val intent= Intent(this, WorkoutStartActivity::class.java)
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_workout_view_exercise)

        // Get workout exercise programName from WorkoutStartActivity intent.putStringExtra
        var exerciseName=intent.getStringExtra("workoutName")
        // set TextView as exercise programName
        exerciseNameTV.text=exerciseName

        // make variable for selected exercise
        var exercise: Exercise=Workouts.activeWorkout!!.workoutExerciseList[s.selexercise!!]



        // setup recyclerview to show worksets of selected exercise
        val recyclerView = worksetRV as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        val adapter = AdapterWorkoutViewExerciseWorkset(exercise.worksetList)
        recyclerView.adapter = adapter


        // fab button to add workset to workset list
        // restarts activity on list update
        fabAddWorksetBtn.setOnClickListener{
            //addToList("workset", exercise.worksetList, this) // add to list and reload activity
            //finish()
            //startActivity(intent)
            worksetPicker(this, "someelse",exercise.worksetList)
        }




    }//onCreate END

    fun addToList(name: String, list:ArrayList<Workset>, con: Context) {

        //val msgClickToAdd="Click to add "+programName

        //create Workset()
        var newWorkset=Workset(null,null,null)

        val mDialog1 = LayoutInflater.from(this)
            .inflate(R.layout.dialog_add_workset, null)
        mDialog1.add_btn.text = "Add"
        val mBuilder1 = AlertDialog.Builder(this)
            .setView(mDialog1)
            .setTitle("Add " + name)


        val mAlertDialog1 = mBuilder1.show()
        mDialog1.add_btn.setOnClickListener {
            mAlertDialog1.dismiss()

            val tempSet: String = mDialog1.setET.text.toString()  // read input
            val tempRep: String = mDialog1.repET.text.toString()
            val tempWeight: String = mDialog1.weightET.text.toString()

            if (tempSet.length>0&&tempRep.length>0&&tempWeight.length>0) { // filter input
                newWorkset.set=tempSet.toInt()
                newWorkset.weight=tempWeight.toInt()
                newWorkset.rep=tempRep.toInt()
                list.add(newWorkset)
                Log.d("myTag", "Workset ${newWorkset.set} ${newWorkset.rep} ${newWorkset.weight} added to exercise")
                DataChangeLogger.alteredData["activeWorkout"]=true

                val msgSuccessfullAdd=name+" added successfully!"
                toastCenterShort(this, msgSuccessfullAdd)

                finish()
                startActivity(intent)

            } else {
                var alertDialog =
                    AlertDialog.Builder(this).create()    // alert if invalid programName given
                alertDialog.setTitle("Alert")
                alertDialog.setMessage("Invalid programName")

                toastCenterShort(this, "Name has to be 1-20 characters long")
            }
        }
    }
}




