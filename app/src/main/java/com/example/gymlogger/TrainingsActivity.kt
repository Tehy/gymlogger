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


class TrainingsActivity : AppCompatActivity() {
    override fun onStop() {
        super.onStop()
        Log.d("myTag", "Trainings onSTOP")
        if(writeTime+timeDelta<System.currentTimeMillis()){
            DataWrite(this, DataChangeLogger.check())
            writeTime=System.currentTimeMillis()
            DataChangeLogger.setFalse()
        }
    }
    override fun onPause() {
        super.onPause()
        Log.d("myTag", "Trainings onPAUSE")
        if(writeTime+timeDelta<System.currentTimeMillis()){
            DataWrite(this, DataChangeLogger.check())
            writeTime=System.currentTimeMillis()
            DataChangeLogger.setFalse()
        }
    }

    override fun onBackPressed() {
        val intent = Intent(this, ProgramsActivity::class.java)
        startActivity(intent)
    }

    //TODO rename ontouchable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_programs)

        val recyclerViewProgram = programsRV as RecyclerView
        recyclerViewProgram.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)



        trainingName.text="trainings"

        // IF nav/index set
        if (s.selProgram != null) {
            // set text
            programName.text = programList[s.selProgram!!].programName

            // set adapter and show recyclerview
            val adapter = AdapterTrainings(programList[s.selProgram!!].trainingList)
            recyclerViewProgram.adapter = adapter

            //if tainingList empty
            if (programList[s.selProgram!!].trainingList.isNullOrEmpty()) { // IF PROGRAMS' LIST EMPTY of Training

                // display toast
                val toast = Toast.makeText(this, "Click + to add training", Toast.LENGTH_SHORT)
                toast.setGravity(Gravity.CENTER,0,0)
                toast.show()
                //TODO SHOULD ADD DIALOG BE MANDATORY IF LIST EMPTY?
                //addToList("training", this)
            }
            // IF nav/index set //TODO DONTSHOW?
            /*
            if (s.selTraining != null) {
                // set text
                trainingName.text = programList[s.selProgram!!].trainingList[s.selTraining!!].programName
            }

             */

        }
        else {
            programName.text = "nameError"
            Log.d("mytag", "s.selProgram === ${s.selProgram}")
        }


        fabAddBtn.setOnClickListener{
            addToList("training",this,null)// add to list and reload activity
        }

    }
    // onCreate END

}
