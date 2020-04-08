package com.example.gymlogger

import android.app.AlertDialog
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.gymlogger.Programs.programList
import kotlinx.android.synthetic.main.programs_layout.view.*


class AdapterTrainings(val trainingList: ArrayList<Training>) : RecyclerView.Adapter<AdapterTrainings.ViewHolder>(){

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val training: Training = trainingList[position]
        holder.textName.text = training.name

        val exerciseCount: Int = trainingList[position].exerciseList.size

        if(exerciseCount > 0){
            holder.exercises.text=exerciseCount.toString()+" exercises"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterTrainings.ViewHolder {
        val v = LayoutInflater.from(parent.context)
        val view = v.inflate(R.layout.programs_layout, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return trainingList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val textName = itemView.textName as TextView
        val exercises = itemView.program_trainingsTV as TextView

        init {
            itemView.setOnLongClickListener {
                dialogAskToDelOrRename(itemView, "training", adapterPosition)
                true
            }

            itemView.setOnClickListener{

                val intent= Intent(itemView.context, exercisesActivity::class.java)
                s.selTraining=adapterPosition     // set clicked item/adapter position(Int) -> navigation/index tracker
                itemView.context.startActivity(intent)

            }
        }
    }
}