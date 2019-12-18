package com.example.gymlogger

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.workout_view_exercise_card.view.*


class AdapterWorkoutViewExerciseWorkset(val worksetList: ArrayList<Workset>) : RecyclerView.Adapter<AdapterWorkoutViewExerciseWorkset.ViewHolder>(){

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val workset: Workset = worksetList[position]
        holder.sets.text = workset.set.toString()
        holder.reps.text = workset.rep.toString()
        holder.weight.text = workset.weight.toString()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterWorkoutViewExerciseWorkset.ViewHolder {
        val v = LayoutInflater.from(parent.context)
        val view = v.inflate(R.layout.workout_view_exercise_card, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return worksetList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val sets = itemView.setsTV as TextView
        val reps = itemView.repsTV as TextView
        val weight = itemView.weightTV as TextView

        init { //TODO does this list have clicklisteners ?? YES
                //todo longclick askToDel/confDel
                // todo click to open dialog -> spinners sets|reps|weight
            itemView.setOnLongClickListener {
                confDel(itemView.context, "workset", adapterPosition) // TODO dialog to edit workset??
                true
            }
            itemView.setOnClickListener{

                //val intent= Intent(itemView.context, WorkoutViewExerciseActivity::class.java)
                //s.selWorkout=adapterPosition
                //intent.putExtra("workoutName", textName.text)
                //itemView.context.startActivity(intent) // GOTO ProgramsActivity for now
                worksetEditPicker(itemView.context, adapterPosition)

            }
        }

    }
}