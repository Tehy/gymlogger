package com.example.gymlogger


import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_programs.*
import kotlinx.android.synthetic.main.activity_programs.programsRV
import kotlinx.android.synthetic.main.activity_workouts.*
import java.util.*
import java.util.Collections.reverse
import kotlin.collections.ArrayList


class WorkoutsActivity : AppCompatActivity() {

    override fun onStop() {
        super.onStop()
        Log.d("myTag", "Workouts onSTOP")
        if(writeTime+timeDelta<System.currentTimeMillis()){
            DataWrite(this, DataChangeLogger.check())
            writeTime=System.currentTimeMillis()
            DataChangeLogger.setFalse()
        }
    }
    override fun onPause() {
        Log.d("myTag", "Workouts onPAUSE")
        super.onPause()
        if(writeTime+timeDelta<System.currentTimeMillis()){
            DataWrite(this, DataChangeLogger.check())
            writeTime=System.currentTimeMillis()
            DataChangeLogger.setFalse()
        }
    }

    override fun onBackPressed() {
        val intent= Intent(this, MainActivity::class.java)
        startActivity(intent)
        s.selWorkout=null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_workouts)

        //workoutsTV.text="Workout exercises"

        val recyclerView = programsRV as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        val adapter = AdapterWorkouts(Workouts.workoutsList)
        recyclerView.adapter = adapter

    }
}



