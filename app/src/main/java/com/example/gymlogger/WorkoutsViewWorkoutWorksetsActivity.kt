package com.example.gymlogger

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


import kotlinx.android.synthetic.main.activity_programs.fabAddBtn
import kotlinx.android.synthetic.main.activity_programs.programsRV
import kotlinx.android.synthetic.main.activity_workouts_view_workout_worksets.*


class WorkoutsViewWorkoutWorksetsActivity : AppCompatActivity() {
    // Check data altered
    override fun onStop() {
        super.onStop()
        Log.d("myTag", "WorkoutsViewWorkoutWorksets onSTOP")
        if(writeTime+timeDelta<System.currentTimeMillis()){
            DataWrite(this, DataChangeLogger.check())
            writeTime=System.currentTimeMillis()
            DataChangeLogger.setFalse()
        }
    }
    override fun onPause() {
        super.onPause()
        Log.d("myTag", "WorkoutsViewWorkoutWorksetsActivity onPAUSE")
        if(writeTime+timeDelta<System.currentTimeMillis()){
            DataWrite(this, DataChangeLogger.check())
            writeTime=System.currentTimeMillis()
            DataChangeLogger.setFalse()
        }
    }
    //

    override fun onBackPressed() {
        val intent= Intent(this, WorkoutsActivity::class.java)
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_workouts_view_workout_worksets)

        var adapterpos=intent.getIntExtra("adapterpos",-1) // if last item of list was deleted -> returns -1 -> read "returnadapterpos" extra
        if(adapterpos==-1){
            adapterpos=intent.getIntExtra("returnadapterpos",-1)
        }
        s.setNull()
        s.selWorkout=adapterpos

        if(!(Workouts.workoutsList[adapterpos].notes.isNullOrBlank())){
            noteTV.text="notes:\n"+Workouts.workoutsList[adapterpos].notes
        }

        val workoutTime=intent.getStringExtra("wot")//workoutTime
        workout_time.text=workoutTime
        val trainingName=intent.getStringExtra("trainingName")
        trainingTV.text=trainingName

        val recyclerView = programsRV as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        val adapter = AdapterWorkoutsViewWorkoutWorksets(Workouts.workoutsList[adapterpos].workoutExerciseList)
        recyclerView.adapter = adapter


        fabAddBtn.setOnClickListener{
            addToList("exercise to workout", this, adapterpos)

        }





    }
}



