package com.example.gymlogger

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.programs_layout.view.*


class AdapterWorkoutStart(val workoutExerciseList: ArrayList<Exercise>) : RecyclerView.Adapter<AdapterWorkoutStart.ViewHolder>(){

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val exercise: Exercise = workoutExerciseList[position]
        holder.exerciseName.text = exercise.name
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterWorkoutStart.ViewHolder {
        val v = LayoutInflater.from(parent.context)
        val view = v.inflate(R.layout.workout_start_layout, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return workoutExerciseList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val exerciseName = itemView.textName as TextView


        init {
            itemView.setOnLongClickListener {
                dialogAskToDelOrRename(itemView, "workoutExercise", adapterPosition)
                true
            }
            itemView.setOnClickListener{

                val intent= Intent(itemView.context, WorkoutViewExerciseActivity::class.java)

                s.selexercise=adapterPosition
                intent.putExtra("workoutName", exerciseName.text)

                itemView.context.startActivity(intent) // GOTO ProgramsActivity for now

            }
        }
    }
}