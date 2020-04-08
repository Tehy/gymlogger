package com.example.gymlogger

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.programs_layout.view.*


class AdapterExercises(val exerciseList: ArrayList<Exercise>) : RecyclerView.Adapter<AdapterExercises.ViewHolder>(){

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val exercise: Exercise = exerciseList[position]
        holder.textName.text = exercise.name
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterExercises.ViewHolder {
        val v = LayoutInflater.from(parent.context)
        val view = v.inflate(R.layout.programs_layout, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return exerciseList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val textName = itemView.textName as TextView
        init {
            itemView.setOnLongClickListener {
                //dialogRename(itemView, "exercise", adapterPosition)
                dialogAskToDelOrRename(itemView, "exercise", adapterPosition)
                true
            }
            itemView.setOnClickListener{

                val intent= Intent(itemView.context, ProgramsActivity::class.java)

                // reset navigation indexes
                s.selTraining=null
                s.selexercise=null
                s.selProgram=null

                itemView.context.startActivity(intent) // GOTO ProgramsActivity for now
                //TODO single exercise activity? / 8/4/20 what does this mean?
            }
        }
    }
}