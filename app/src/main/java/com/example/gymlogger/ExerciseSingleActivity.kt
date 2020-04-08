package com.example.gymlogger

import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gymlogger.Programs.programList


import kotlinx.android.synthetic.main.activity_programs.*

class exerciseSingleActivity : AppCompatActivity() {
        /*TODO is this used?
            8/4/20 no change.
         */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_programs)

        val recyclerViewProgram = programsRV as RecyclerView
        recyclerViewProgram.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        var adapterPos = intent.getStringExtra("adapterPos").toInt()
        var trainingNameExtra = intent.getStringExtra("trainingName")

        s.selexercise=adapterPos

        //var progIndex= programList.indexOf(Program(programList[adapterPos].programName))
        //var trainIndex= programList[progIndex].trainingList.indexOf(Training(trainingNameExtra))

        //programName.text = programList[s.selProgram].programName
        //trainingName.text = programList[s.selProgram].trainingList[s.selTraining].programName
        //exerciseName.text = programList[s.selProgram].trainingList[s.selTraining].exerciseList[s.selexercise].programName

        s.selexercise
        s.selTraining
        s.selProgram

        //val adapter = AdapterExercises(programList[s.selProgram].trainingList[s.selTraining].exerciseList)
        //recyclerViewProgram.adapter = adapter



    }
}
