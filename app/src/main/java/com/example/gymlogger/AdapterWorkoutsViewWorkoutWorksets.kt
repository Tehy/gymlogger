package com.example.gymlogger

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.workouts_view_worksets_card.view.*


class AdapterWorkoutsViewWorkoutWorksets(val workoutExerciseList: ArrayList<Exercise>) : RecyclerView.Adapter<AdapterWorkoutsViewWorkoutWorksets.ViewHolder>(){

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        // workoutexerciselist position
        val welp:Exercise = workoutExerciseList[position]
        var worksets   = ""
        val exercise   = welp.name

        if(exercise.isNullOrEmpty()){
            worksets="no exercises"
        }
        else{
            if(!(welp.worksetList.isNullOrEmpty())){
                for(i in welp.worksetList){
                    var sets="${i.set} x ${i.rep} x ${i.weight}\n"
                    worksets=worksets.plus(sets)
                }
                worksets = worksets.substring(0, worksets.length-1) // delete the last linebreak
            }
            else{
                worksets="no worksets"
            }
        }
        holder.worksets.text   = worksets
        holder.exercise.text   = exercise
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
        val view = v.inflate(R.layout.workouts_view_worksets_card, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return workoutExerciseList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val worksets = itemView.worksetsTV as TextView
        val exercise = itemView.exerciseTV as TextView


        init {
            itemView.setOnLongClickListener {

                confDel(itemView.context, "exercise from workout", adapterPosition)
                true
            }

            itemView.setOnClickListener{

                s.selexercise=adapterPosition
                worksetPicker(itemView.context, "workoutsWorkset",Workouts.workoutsList[s.selWorkout!!].workoutExerciseList[adapterPosition].worksetList)

            }

        }

    }
}