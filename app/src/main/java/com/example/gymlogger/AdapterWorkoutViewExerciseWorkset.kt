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

        init {
                /*TODO
                   longclick askToDel or confDel ?
                   click to open dialog -> spinners sets|reps|weight
                   dialog to edit workset?
                    8/4/20 no change, need to rethink data modify functionality of finished workouts
                 */

            itemView.setOnLongClickListener {
                confDel(itemView.context, "workset", adapterPosition)
                true
            }
            itemView.setOnClickListener{
                worksetEditPicker(itemView.context, adapterPosition)

            }
        }

    }
}