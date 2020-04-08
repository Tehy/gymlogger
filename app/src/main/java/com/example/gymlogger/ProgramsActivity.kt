package com.example.gymlogger

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gymlogger.Programs.programList
import com.example.gymlogger.MainActivity


import kotlinx.android.synthetic.main.activity_programs.*
import kotlinx.android.synthetic.main.dialog_rename_layout.view.*




class ProgramsActivity : AppCompatActivity() {

    /* todo  8/4/20 remove unused onAction functions
    override fun onResume() {
        super.onResume()
        Log.d("myTag", "Programs onRESUME")
    }
    override fun onStart() {
        super.onStart()
        Log.d("myTag", "Programs onSTART")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d("myTag", "Programs onRESTART")
    }
    override fun onDestroy() {
        super.onDestroy()
        Log.d("myTag", "Programs onDESTROY")
    }
     */

    override fun onStop() {
        super.onStop()
        Log.d("myTag", "Programs onSTOP")
        if(writeTime+timeDelta<System.currentTimeMillis()){
            DataWrite(this, DataChangeLogger.check())
            writeTime=System.currentTimeMillis()
            DataChangeLogger.setFalse()
        }
    }
    override fun onPause() {
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
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_programs)

        val recyclerViewProgram = programsRV as RecyclerView
        recyclerViewProgram.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        val adapter = AdapterPrograms(programList)
        recyclerViewProgram.adapter = adapter

        programName.text="Training programs"

        // if programList empty
        if(programList.isNullOrEmpty()){

            // display toast
            val toast = Toast.makeText(this, "Click + to add program", Toast.LENGTH_SHORT)
            toast.setGravity(Gravity.CENTER,0,0)
            toast.show()
            /*TODO SHOULD ADD DIALOG BE MANDATORY IF LIST EMPTY?
                8/4/20 no change. probably not.
            */
            //addToList("program",this)
        }

        fabAddBtn.setOnClickListener{
            addToList("program",this,null) // add to list and reload activity
        }
    }
}
