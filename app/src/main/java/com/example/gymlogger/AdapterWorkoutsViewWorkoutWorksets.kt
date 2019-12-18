package com.example.gymlogger

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.workouts_view_worksets_card.view.*


class AdapterWorkoutsViewWorkoutWorksets(val workoutExerciseList: ArrayList<Exercise>) : RecyclerView.Adapter<AdapterWorkoutsViewWorkoutWorksets.ViewHolder>(){

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {


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

                //addWorksetToExercise("workset to exercise", itemView.context, adapterPosition)

                //addToList("workset to exercise",itemView.context,adapterPosition)

                s.selexercise=adapterPosition

                worksetPicker(itemView.context, "workoutsWorkset",Workouts.workoutsList[s.selWorkout!!].workoutExerciseList[adapterPosition].worksetList)

                //val intent= Intent(itemView.context, WorkoutsWorsksets::class.java) //TODO WorkoutsWorsksets activity/adapter

                //s.selProgram=adapterPosition    // set clicked item/adapter position(Int) -> navigation/index tracker

                //itemView.context.startActivity(intent)

            }

        }

    }
}
/*
fun addWorksetToExercise(name: String, context: Context , adapterPos: Int) {

    //val msgClickToAdd="Click to add "+programName

    //create Workset()
    var newWorkset=Workset(null,null,null)

    val mDialog1 = LayoutInflater.from(context)
        .inflate(R.layout.dialog_add_workset, null)
    mDialog1.add_btn.text = "Add"
    val mBuilder1 = AlertDialog.Builder(context)
        .setView(mDialog1)
        .setTitle("Add " + name)


    val mAlertDialog1 = mBuilder1.show()
    mDialog1.add_btn.setOnClickListener {
        mAlertDialog1.dismiss()

        // TODO add default 1 to 'sets' ? or sets can be null ??
        // todo add try block?? input type number, can anything else be input ??
        val tempSet: String = mDialog1.setET.text.toString()  // read input

        val tempRep: String = mDialog1.repET.text.toString()

        val tempWeight: String = mDialog1.weightET.text.toString()



        if (tempSet.length>0&&tempRep.length>0&&tempWeight.length>0) { // filter input
            newWorkset.set=tempSet.toInt()
            newWorkset.weight=tempWeight.toInt()
            newWorkset.rep=tempRep.toInt()

            Workouts.workoutsList[s.selWorkout!!].workoutExerciseList[adapterPos].worksetList.add(newWorkset)

            DataChangeLogger.alteredData["workoutList"]=true

            val msgSuccessfullAdd=name+" added successfully!"
            toastCenterShort(context, msgSuccessfullAdd)

            val intent = Intent(context, WorkoutsViewWorkoutWorksetsActivity::class.java)
            intent.putExtra("returnadapterpos",s.selWorkout!!)
            intent.putExtra("trainingName",Workouts.workoutsList[s.selWorkout!!].trainingName)
            context.startActivity(intent)
            toastCenterShort(context,"Added successfully!")

        } else {
            var alertDialog =
                AlertDialog.Builder(context).create()    // alert if invalid programName given
            alertDialog.setTitle("Alert")
            alertDialog.setMessage("Invalid programName")

            toastCenterShort(context, "Name has to be 1-20 characters long")
        }
    }



}

 */
