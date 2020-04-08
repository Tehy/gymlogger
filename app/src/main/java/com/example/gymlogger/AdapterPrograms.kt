package com.example.gymlogger

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.programs_layout.view.*


class AdapterPrograms(val programList: ArrayList<Program>) : RecyclerView.Adapter<AdapterPrograms.ViewHolder>(){

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val program: Program = programList[position]
        holder.textName.text = program.programName

        val trainingCount: Int = programList[position].trainingList.size
        val trainingCountConcat= trainingCount.toString()+" trainings"
        if(trainingCount > 0){
            holder.trainings.text=trainingCountConcat
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
        val view = v.inflate(R.layout.programs_layout, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return programList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textName = itemView.textName as TextView
        val trainings = itemView.program_trainingsTV as TextView

        init {
            itemView.setOnLongClickListener {
                dialogAskToDelOrRename(itemView, "program", adapterPosition)

                itemView.context
                /*todo what is this?
                    8/4/20 no change.
                 */
                true
            }

            itemView.setOnClickListener{

                val intent= Intent(itemView.context, TrainingsActivity::class.java)
                intent.putExtra("adapterpos",adapterPosition)
                s.selProgram=adapterPosition    // set clicked item/adapter position(Int) -> navigation/index tracker
                itemView.context.startActivity(intent)

            }

        }
    }
}
