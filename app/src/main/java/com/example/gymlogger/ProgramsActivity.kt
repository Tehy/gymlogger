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

    /*


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
            //TODO SHOULD ADD DIALOG BE MANDATORY IF LIST EMPTY??
            //addToList("program",this)
        }

        fabAddBtn.setOnClickListener{
            addToList("program",this,null) // add to list and reload activity
        }



    }
    //onCreate END

    /*
    fun addToList(name: String, con:Context){ // TODO make addToList as class

        //val msgClickToAdd="Click to add "+programName

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

            if (objName.length > 0 && objName.length < 20) {                  // filter input

                when (name) {
                    "program" -> {
                        if (programList.indexOf(Program(objName)) == -1) {
                            // Log.d("myTag", "programList.indexOf(Program(objName)) == ${programList.indexOf(Program(objName))}")
                            programList.add(Program(objName))

                            intent = Intent(con, ProgramsActivity::class.java)
                            con.startActivity(intent)
                            toastCenterShort(con, "Added successfully!")
                        }
                        else{
                            toastCenterShort(con, "Name has to be unique")
                        }
                        s.selProgram=null // TODO is s.selProgram=null right ??
                    }
                    "training" -> {
                        if(programList[s.selProgram!!].trainingList.indexOf(Training(objName))==-1) {
                            programList[s.selProgram!!].trainingList.add(Training(objName))

                            intent = Intent(con, TrainingsActivity::class.java)
                            con.startActivity(intent)
                            toastCenterShort(con,"Added successfully!")
                        }
                        else{
                            toastCenterShort(con, "Name has to be unique")
                        }
                        s.selTraining=null
                    }
                    "exercise" -> {
                        if(programList[s.selProgram!!].trainingList[s.selTraining!!].exerciseList.indexOf(Exercise(objName))==-1) {
                            programList[s.selProgram!!].trainingList[s.selTraining!!].exerciseList.add(Exercise(objName))

                            intent = Intent(con, exercisesActivity::class.java)
                            con.startActivity(intent)
                            toastCenterShort(con,"Added successfully!")
                        }
                        else{
                            toastCenterShort(con, "Name has to be unique")
                        }
                        s.selexercise=null // TODO refactor rename s.selexercise -> s.selExercise
                    }
                }


            } else {
                var alertDialog =
                    AlertDialog.Builder(this).create()    // alert if invalid programName given
                alertDialog.setTitle("Alert")
                alertDialog.setMessage("Invalid programName")
                Toast.makeText(
                    this,
                    "Name has to be 1-20 characters long",
                    Toast.LENGTH_LONG
                ).show()
            }
        }



    }
    */
}
