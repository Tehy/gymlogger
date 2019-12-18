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


class TimerActivity : AppCompatActivity() {
    override fun onStop() {
        super.onStop()
        Log.d("myTag", "Timer onSTOP")


    }
    override fun onPause() {
        super.onPause()
        Log.d("myTag", "Timer onPAUSE")
    }

    override fun onBackPressed() {
        //val intent = Intent(this, ProgramsActivity::class.java)
        //startActivity(intent)
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timer)

        trainingName.text="Rest timer"

        fabAddBtn.setOnClickListener{

        }

    }
    // onCreate END

}
