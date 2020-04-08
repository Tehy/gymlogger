package com.example.gymlogger

import android.app.AlertDialog
import android.app.PendingIntent.getActivity
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_programs.*
import kotlinx.android.synthetic.main.programs_layout.view.*
import kotlinx.android.synthetic.main.workouts_card.view.*
import java.security.AccessController.getContext



class AdapterWorkouts(val workoutsList: ArrayList<Workout>) : RecyclerView.Adapter<AdapterWorkouts.ViewHolder>(){

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        // workout list position
        val wlp = Workouts.workoutsList[position]

        val training    = wlp.trainingName!!
        val dayOfWeek   = wlp.workoutTime.get("dayofweek")
        val date        = wlp.workoutTime.get("date")       // TODO redo year '2019'->'19'

        // TODO 8/4/20 not in use?
        val workoutTime =   "${wlp.workoutTime["hours"]}:" +
                            "${wlp.workoutTime["minutes"]}:" +
                            "${wlp.workoutTime["seconds"]} " // todo remove TEST seconds

        if(!(wlp.notes.isNullOrBlank())){
            holder.noteImg.setImageResource(R.drawable.ic_insert_comment_blue)
        }

        val startTime="${wlp.startTimeHour.toString()}:" +
                "${wlp.startTimeMinute.toString()}:" +
                "${wlp.startTimeSecond.toString()} "
        holder.startTime.text   = startTime

        holder.dayofweek.text   = dayOfWeek.toString()
        holder.date.text        = date.toString()
        holder.training.text    = training

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
        val view = v.inflate(R.layout.workouts_card, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return Workouts.workoutsList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val date        = itemView.dateTV as TextView
        val dayofweek   = itemView.dayofweekTV as TextView
        val training    = itemView.trainingTV as TextView
        val startTime = itemView.start_timeTV as TextView
        val noteImg= itemView.noteImg as ImageView

        init {
            itemView.setOnLongClickListener {
                //itemView.context
                /* todo add feature : ask dialog-> continue / delete / change data -> select data etc..
                    8/4/20 no change
                 */
                confDel(itemView.context, "workouts",adapterPosition)
                true
            }

            itemView.setOnClickListener{
                val intent= Intent(itemView.context, WorkoutsViewWorkoutWorksetsActivity::class.java)
                intent.putExtra("adapterpos",adapterPosition)
                intent.putExtra("trainingName",training.text)
                intent.putExtra("wot",Workouts.workoutsList[adapterPosition].workoutTime["hours"].toString()+":"+Workouts.workoutsList[adapterPosition].workoutTime["minutes"].toString())

                s.selWorkout=adapterPosition
                itemView.context.startActivity(intent)
            }

        }
    }
}
