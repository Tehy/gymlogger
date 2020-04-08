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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gymlogger.Programs.programList


import kotlinx.android.synthetic.main.activity_programs.*
import kotlinx.android.synthetic.main.dialog_rename_layout.view.*


class exercisesActivity : AppCompatActivity() {
    /*TODO rename exerciseActivity -> ExerciseActivity
        8/4/20 no change
     */
    override fun onPause() {
        super.onPause()
        Log.d("myTag", "exercises onPAUSE")
        if(writeTime+timeDelta<System.currentTimeMillis()){
            DataWrite(this, DataChangeLogger.check())
            writeTime=System.currentTimeMillis()
            DataChangeLogger.setFalse()
        }
    }

    override fun onStop() {
        super.onStop()
        Log.d("myTag", "exercises onSTOP")
        if(writeTime+timeDelta<System.currentTimeMillis()){
            DataWrite(this, DataChangeLogger.check())
            writeTime=System.currentTimeMillis()
            DataChangeLogger.setFalse()
        }
    }

    override fun onBackPressed() {

        val intent= Intent(this, TrainingsActivity::class.java)
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_programs)

        val recyclerViewProgram = programsRV as RecyclerView
        recyclerViewProgram.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)



        exerciseName.text="exercises"

        // IF nav/index set
        if (s.selProgram != null) {
            // set text
            programName.text = programList[s.selProgram!!].programName

            // IF nav/index set
            if (s.selTraining != null) {
                // set text
                trainingName.text = programList[s.selProgram!!].trainingList[s.selTraining!!].name

                // set adapter and show recyclerview
                val adapter = AdapterExercises(programList[s.selProgram!!].trainingList[s.selTraining!!].exerciseList)
                recyclerViewProgram.adapter = adapter

                // if exerciseList empty
                if (programList[s.selProgram!!].trainingList[s.selTraining!!].exerciseList.isNullOrEmpty()) { // IF PROGRAMS' LIST EMPTY of Training

                    // display toast
                    toastCenterShort(this, "Click + to add exercise")

                    /*TODO SHOULD ADD DIALOG BE MANDATORY IF LIST EMPTY?
                        8/4/20 no change. probably not.
                     */
                    //addToList("exercise", this) //add item, set s.selEcercise = 0 and reload page
                }
                /* TODO NOT IMPLEMENTED exercise SINGLE VIEW
                    8/4/20 no change. Could be useful in changing worksets data
                if (s.selexercise!=null){
                    exerciseName.text = programList[s.selProgram!!].trainingList[s.selTraining!!].exerciseList[s.selexercise].programName
                }

                 */
            }
            else {
                programName.text = "nameError"
                trainingName.text = "nameError"
                Log.d("mytag", "s.selProgram === ${s.selProgram}")
                Log.d("mytag", "s.selTraining === ${s.selTraining}")
            }
        }

        fabAddBtn.setOnClickListener{
            addToList("exercise",this,null)
        }



    }
    // onCreate END


}
