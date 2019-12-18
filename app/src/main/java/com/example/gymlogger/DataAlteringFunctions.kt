package com.example.gymlogger

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import com.example.gymlogger.Programs.programList
import com.example.gymlogger.Workouts.activeWorkout
import kotlinx.android.synthetic.main.dialog_add_note.*
import kotlinx.android.synthetic.main.dialog_add_note.view.*
import kotlinx.android.synthetic.main.dialog_ask_yes_no.view.*
import kotlinx.android.synthetic.main.dialog_rename_layout.view.*
import kotlinx.android.synthetic.main.dialog_workset_picker_layout.view.*
import java.io.File

val maxNameLength=40
val maxNoteLength=500


// TODO edit worksets
// TODO edit workset spinners

// TODO edit/delete worksets in activeWorkout

fun worksetPicker(con: Context, name:String,list:ArrayList<Workset>){ //TODO REDO name variables with enums?
    val mDialog1 = LayoutInflater.from(con)
        .inflate(R.layout.dialog_workset_picker_layout, null)
    //mDialog1.add_btn.text = "Add"
    val mBuilder1 = AlertDialog.Builder(con)
        .setView(mDialog1)
        .setTitle("Add workset")

    mDialog1.numberPicker_set.minValue=0;   mDialog1.numberPicker_set.maxValue=50
    mDialog1.numberPicker_rep.maxValue=100; mDialog1.numberPicker_rep.minValue=0
    mDialog1.numberPicker_weight.minValue=0;mDialog1.numberPicker_weight.maxValue=1000
    mDialog1.numberPicker_weight.setDisplayedValues(arrayOf("a", "b", "c"))

    mDialog1.numberPicker_set.value = 1 // default val 1

    if(!(list.isNullOrEmpty())){ // reload previous workset values to numberpickervalues
        // todo <-- this doesnt work-> if clicked 'ok', copies last workset
        // TODO REMOVE? commented out for now
        //
        val previousWorksetIndex=list.size-1
        mDialog1.numberPicker_set.value     = list[previousWorksetIndex].set!!
        mDialog1.numberPicker_rep.value     = list[previousWorksetIndex].rep!!
        mDialog1.numberPicker_weight.value  = list[previousWorksetIndex].weight!!
    }



    val mAlertDialog1 = mBuilder1.show()
    mAlertDialog1.setOnDismissListener{

        var tempSet: Int = mDialog1.numberPicker_set.value  // read input

        var tempRep: Int = mDialog1.numberPicker_rep.value

        var tempWeight: Int = mDialog1.numberPicker_weight.value

        if (tempSet!=0||tempRep!=0||tempWeight!=0) { // filter input // TODO rethink workset filter?
            var newWorkset=Workset(tempSet,tempRep,tempWeight)
            newWorkset.set=tempSet.toInt()
            newWorkset.weight=tempWeight.toInt()
            newWorkset.rep=tempRep.toInt()
            list.add(newWorkset)
            Log.d("myTag", "Workset ${newWorkset.set} ${newWorkset.rep} ${newWorkset.weight} added to exercise")


            //val msgSuccessfullAdd=name+" added successfully!"
            //toastCenterShort(this, msgSuccessfullAdd)
            if(name=="workoutsWorkset"){
                DataChangeLogger.alteredData["workoutList"]=true
                val intent=Intent(con, WorkoutsViewWorkoutWorksetsActivity::class.java)
                intent.putExtra("trainingName",Workouts.workoutsList[s.selWorkout!!].trainingName)
                intent.putExtra("adapterpos",s.selWorkout!!)
                intent.putExtra("wot",Workouts.workoutsList[s.selWorkout!!].workoutTime["hours"].toString()+":"+Workouts.workoutsList[s.selWorkout!!].workoutTime["minutes"].toString())
                // intent.putExtra("wot",con.) // todo intent put extra -> workoutTime
                //intent.putExtra("workoutName",Workouts.workoutsList[s.selWorkout!!].workoutExerciseList[s.selexercise!!].name)
                con.startActivity(intent)
            }
            else{  // todo
                DataChangeLogger.alteredData["activeWorkout"]=true
                val intent=Intent(con, WorkoutViewExerciseActivity::class.java)
                intent.putExtra("workoutName",activeWorkout!!.workoutExerciseList[s.selexercise!!].name)
                con.startActivity(intent)
            }
        }
    }

}

fun worksetEditPicker(con: Context, adapterPosition: Int){

    val mDialog1 = LayoutInflater.from(con)
        .inflate(R.layout.dialog_workset_picker_layout, null)
    val mBuilder1 = AlertDialog.Builder(con)
        .setView(mDialog1)
        .setTitle("Edit workset")

    mDialog1.numberPicker_set.minValue=0;   mDialog1.numberPicker_set.maxValue=50
    mDialog1.numberPicker_rep.minValue=0;   mDialog1.numberPicker_rep.maxValue=100
    mDialog1.numberPicker_weight.minValue=0;mDialog1.numberPicker_weight.maxValue=1000

    // Read edited workset values to picker
    val ws=activeWorkout!!.workoutExerciseList[s.selexercise!!].worksetList[adapterPosition] // workset
    mDialog1.numberPicker_set.value     = ws.set!!
    mDialog1.numberPicker_rep.value     = ws.rep!!
    mDialog1.numberPicker_weight.value  = ws.weight!!

    val mAlertDialog1 = mBuilder1.show()
    mAlertDialog1.setOnDismissListener {

        var tempSet: Int = mDialog1.numberPicker_set.value  // read input
        var tempRep: Int = mDialog1.numberPicker_rep.value
        var tempWeight: Int = mDialog1.numberPicker_weight.value
        if(ws.set!=tempSet||ws.rep!=tempRep||ws.weight!=tempWeight) { // if workset is changed
            Log.d(
                "myTag",
                "Editing activeWorkout exercise Workset ${activeWorkout!!.workoutExerciseList[s.selexercise!!].name} ${tempSet} ${tempRep} ${tempWeight}"
            )
            activeWorkout!!.workoutExerciseList[s.selexercise!!].worksetList[adapterPosition].set =
                tempSet
            activeWorkout!!.workoutExerciseList[s.selexercise!!].worksetList[adapterPosition].rep =
                tempRep
            activeWorkout!!.workoutExerciseList[s.selexercise!!].worksetList[adapterPosition].weight =
                tempWeight
            Log.d(
                "myTag",
                "Workset edit ${activeWorkout!!.workoutExerciseList[s.selexercise!!].worksetList[adapterPosition]}"
            )
            DataChangeLogger.alteredData["activeWorkout"] = true

            val intent = Intent(con, WorkoutViewExerciseActivity::class.java)
            intent.putExtra(
                "workoutName",
                activeWorkout!!.workoutExerciseList[s.selexercise!!].name
            )
            con.startActivity(intent)
        }
    }
}

fun renameItem(itemView: View, name: String, adapterPosition: Int) { // todo renameItem()<- this is stoopid
    if (name == "program") {
        dialogRename(itemView, name, adapterPosition)
    } else if (name == "training") {
        dialogRename(itemView, name, adapterPosition)
    } else if (name == "exercise") {
        dialogRename(itemView, name, adapterPosition)
    } else if (name == "workoutExercise") {
        dialogRename(itemView, name, adapterPosition)
    }
}

fun deleteItem(itemView: View, name: String, adapterPosition: Int) {
    if (name == "program") {
        if (!(Programs.programList[adapterPosition].trainingList.isNullOrEmpty())) { // if item has child items
            confDel(itemView.context, name, adapterPosition) // confirm deletion
        } else {
            Log.d("myTag","Deleting Program '${Programs.programList[adapterPosition].programName}'")
            Programs.programList.removeAt(adapterPosition)
            Log.d("myTag","...deleted")

            DataChangeLogger.alteredData["programList"]=true

            val intent = Intent(itemView.context, ProgramsActivity::class.java)
            itemView.context.startActivity(intent)
        }
    }
    else if (name == "training") {
        if (!(Programs.programList[s.selProgram!!].trainingList[adapterPosition].exerciseList.isNullOrEmpty())) {
            confDel(itemView.context, name, adapterPosition)
        } else {
            Log.d("myTag","Deleting Training '${Programs.programList[s.selProgram!!].trainingList[adapterPosition].name}'")
            Programs.programList[s.selProgram!!].trainingList.removeAt(adapterPosition)
            Log.d("myTag","...deleted")

            DataChangeLogger.alteredData["programList"]=true

            val intent = Intent(itemView.context, TrainingsActivity::class.java)
            itemView.context.startActivity(intent)
        }
    }
    else if (name == "exercise") {
        Log.d("myTag","Deleting Exercise '${Programs.programList[s.selProgram!!].trainingList[s.selTraining!!].exerciseList[adapterPosition].name}'")
        Programs.programList[s.selProgram!!].trainingList[s.selTraining!!].exerciseList.removeAt(adapterPosition)
        Log.d("myTag","...deleted")

        DataChangeLogger.alteredData["programList"]=true

        val intent = Intent(itemView.context, exercisesActivity::class.java)
        itemView.context.startActivity(intent)
    }
    else if (name == "workoutExercise") {

        Log.d("myTag","Deleting activeWorkoutExercise '${Workouts.activeWorkout!!.workoutExerciseList[adapterPosition].name}'")
        Workouts.activeWorkout!!.workoutExerciseList.removeAt(adapterPosition)
        Log.d("myTag","...deleted")

        DataChangeLogger.alteredData["activeWorkout"]=true

        val intent = Intent(itemView.context, WorkoutStartActivity::class.java)
        itemView.context.startActivity(intent)
    }

    /*
    else if (name == "workouts") {
        Workouts.workoutsList.removeAt(adapterPosition)

        DataChangeLogger.alteredData["workoutList"]=true

        val intent = Intent(itemView.context, WorkoutsActivity::class.java)
        itemView.context.startActivity(intent)
    }

     */

}

fun dialogRename(itemView: View, name: String, adapterPosition: Int) {
    val title = "Rename " + name

    val mDialog = LayoutInflater.from(itemView.context)
        .inflate(R.layout.dialog_rename_layout, null)

    val mBuilder = AlertDialog.Builder(itemView.context)
        .setView(mDialog)
        .setTitle(title)
    val mAlertDialog = mBuilder.show()


    mDialog.insert_name_btn.setOnClickListener {
        mAlertDialog.dismiss()

        val objName: String = mDialog.insert_name.text.toString() //read input
        var intent: Intent
        if (objName.length > 0 && objName.length < maxNameLength) {
            if (name == "program") {
                if(Programs.programList.indexOf(Program(objName))==-1) {
                    // Log.d("myTag", "programList.indexOf(Program(objName)) == ${programList.indexOf(Program(objName))}")
                    Log.d("myTag","Program '${Programs.programList[adapterPosition].programName}' renamed as '${objName}'")

                    Programs.programList[adapterPosition].programName = objName

                    intent = Intent(itemView.context, ProgramsActivity::class.java)
                    itemView.context.startActivity(intent)
                    toastCenterShort(itemView.context,"Renamed successfully!")

                    DataChangeLogger.alteredData["programList"]=true
                }
                else{
                    toastCenterShort(itemView.context, "Name has to be unique")
                }
            } else if (name == "training") {
                if(Programs.programList[s.selProgram!!].trainingList.indexOf(Training(objName))==-1) {
                    Log.d("myTag","Training '${Programs.programList[s.selProgram!!].trainingList[adapterPosition].name}' renamed as '${objName}'")

                    Programs.programList[s.selProgram!!].trainingList[adapterPosition].name = objName

                    intent = Intent(itemView.context, TrainingsActivity::class.java)
                    itemView.context.startActivity(intent)
                    toastCenterShort(itemView.context,"Renamed successfully!")

                    DataChangeLogger.alteredData["programList"]=true

                }
                else{
                    toastCenterShort(itemView.context, "Name has to be unique")
                }

            } else if (name == "exercise") {
                if(Programs.programList[s.selProgram!!].trainingList[s.selTraining!!].exerciseList.indexOf(Exercise(objName))==-1) {

                    Log.d("myTag","Exercise '${Programs.programList[s.selProgram!!].trainingList[s.selTraining!!].exerciseList[adapterPosition].name}' renamed as '${objName}'")

                    Programs.programList[s.selProgram!!].trainingList[s.selTraining!!].exerciseList[adapterPosition].name =
                        objName

                    intent = Intent(itemView.context, exercisesActivity::class.java)
                    itemView.context.startActivity(intent)
                    toastCenterShort(itemView.context,"Renamed successfully!")

                    DataChangeLogger.alteredData["programList"]=true

                }
                else {
                    toastCenterShort(itemView.context, "Name has to be unique")
                }
            } else if (name == "workoutExercise") {
                if(Workouts.activeWorkout!!.workoutExerciseList.indexOf(Exercise(objName))==-1) {

                    Log.d("myTag","workoutExercise '${Workouts.activeWorkout!!.workoutExerciseList[adapterPosition].name}' renamed as '${objName}'")

                    Workouts.activeWorkout!!.workoutExerciseList[adapterPosition].name =
                        objName

                    DataChangeLogger.alteredData["activeWorkout"]=true

                    intent = Intent(itemView.context, WorkoutStartActivity::class.java)
                    itemView.context.startActivity(intent)
                    toastCenterShort(itemView.context,"Renamed successfully!")

                }
                else {
                    toastCenterShort(itemView.context, "Name has to be unique")
                }
            }

        } else {
            toastCenterShort(itemView.context,"Name has to be 1-${maxNameLength} characters long")
        }
    }

}

fun dialogAskToDelOrRename(itemView: View, name: String, adapterPosition: Int) {
    val title = "Rename or delete " + name

    val mDialog = LayoutInflater.from(itemView.context)
        .inflate(R.layout.dialog_ask_yes_no, null)
    mDialog.yes_btn.text = "Rename"
    mDialog.no_btn.text = "Delete"

    val mBuilder = AlertDialog.Builder(itemView.context)
        .setView(mDialog)
        .setTitle(title)
    val mAlertDialog = mBuilder.show()
    mDialog.yes_btn.setOnClickListener {

        renameItem(itemView, name, adapterPosition)

        mAlertDialog.dismiss()
    }
    mDialog.no_btn.setOnClickListener {
        deleteItem(itemView, name, adapterPosition)
        mAlertDialog.dismiss()
    }
}

fun confDel(itemView: Context, name: String, adapterPosition: Int) { // TODO REDO REFACTOR deleteItem() confDel()
    var title:String
    if (name == "workouts"){
        title ="Delete workout?"
    }
    else if(name=="exercise from workout") {
        title = "Delete "+name+" ?"
    }
    else if(name=="workset") {
        title = "Delete "+name+" ?"
    }
    else{
        title = "Item not empty, delete all?"
    }

    val mDialog = LayoutInflater.from(itemView)
        .inflate(R.layout.dialog_ask_yes_no, null)
    mDialog.yes_btn.text = "Yes"
    mDialog.no_btn.text = "No"
    val mBuilder = AlertDialog.Builder(itemView)
        .setView(mDialog)
        .setTitle(title)
    val mAlertDialog = mBuilder.show()

    mDialog.yes_btn.setOnClickListener {
        mAlertDialog.dismiss()
        if (name == "program") {
            Log.d("myTag","Deleting Program '${Programs.programList[adapterPosition].programName}'")
            Programs.programList.removeAt(adapterPosition)
            Log.d("myTag","...deleted")

            DataChangeLogger.alteredData["programList"] = true

            val intent = Intent(itemView, ProgramsActivity::class.java)
            itemView.startActivity(intent)
        }
        else if (name == "training") {
            Log.d("myTag","Deleting Training '${Programs.programList[s.selProgram!!].trainingList[adapterPosition].name}'")
            Programs.programList[s.selProgram!!].trainingList.removeAt(adapterPosition)
            Log.d("myTag","...deleted")

            DataChangeLogger.alteredData["programList"] = true

            val intent = Intent(itemView, TrainingsActivity::class.java)
            itemView.startActivity(intent)
        }
        else if (name == "exercise") {
            Log.d("myTag","Deleting Exercise '${Programs.programList[s.selProgram!!].trainingList[s.selTraining!!].exerciseList[adapterPosition].name}'")
            Programs.programList[s.selProgram!!].trainingList[s.selTraining!!].exerciseList.removeAt(
                adapterPosition
            )
            Log.d("myTag","...deleted")

            DataChangeLogger.alteredData["programList"] = true

            val intent = Intent(itemView, exercisesActivity::class.java)
            itemView.startActivity(intent)
        }
        else if (name == "workouts") {
            Log.d("myTag","Deleting from workoutsList at adapterPosition -> '${adapterPosition}'")
            Workouts.workoutsList.removeAt(adapterPosition)
            Log.d("myTag","...deleted")

            DataChangeLogger.alteredData["workoutList"] = true

            val intent = Intent(itemView, WorkoutsActivity::class.java)
            itemView.startActivity(intent)
        }
        else if (name == "exercise from workout") {
            Log.d("myTag","Deleting Exercise from workoutsList[s.selWorkout] -> '${Workouts.workoutsList[s.selWorkout!!].workoutExerciseList[adapterPosition].name}'")
            Workouts.workoutsList[s.selWorkout!!].workoutExerciseList.removeAt(adapterPosition)
            Log.d("myTag","...deleted")

            DataChangeLogger.alteredData["workoutList"] = true

            val intent = Intent(itemView, WorkoutsViewWorkoutWorksetsActivity::class.java)
            intent.putExtra("returnadapterpos",s.selWorkout!!)
            intent.putExtra("trainingName",Workouts.workoutsList[s.selWorkout!!].trainingName)
            intent.putExtra("wot",Workouts.workoutsList[s.selWorkout!!].workoutTime["hours"].toString()+":"+Workouts.workoutsList[s.selWorkout!!].workoutTime["minutes"].toString())
            itemView.startActivity(intent)
        }
        else if (name == "workset") {

            Log.d("myTag","Deleting workset from activeworkout exercise'${activeWorkout!!.workoutExerciseList[s.selexercise!!].name}'")
            activeWorkout!!.workoutExerciseList[s.selexercise!!].worksetList.removeAt(adapterPosition)
            Log.d("myTag","...deleted")

            DataChangeLogger.alteredData["activeWorkout"] = true

            val intent = Intent(itemView, WorkoutViewExerciseActivity::class.java)
            //intent.putExtra("returnadapterpos",s.selexercise!!)
            intent.putExtra("workoutName",activeWorkout!!.workoutExerciseList[s.selexercise!!].name)

            itemView.startActivity(intent)
        }
    }
    mDialog.no_btn.setOnClickListener {
        mAlertDialog.dismiss()
    }
}

// ADD
fun addProgram(name: String) {
    Programs.programList.add(Program(name))
    DataChangeLogger.alteredData["programList"]=true
}

fun addTraining(name: String, programListIndex: Int) {
    Programs.programList[programListIndex].trainingList.add(Training(name))
    DataChangeLogger.alteredData["programList"]=true
}

fun addexercise(name: String, programListIndex: Int, trainingListIndex: Int) {
    Programs.programList[programListIndex].trainingList[trainingListIndex].exerciseList.add(
        Exercise(name)
    )
    DataChangeLogger.alteredData["programList"]=true
}

fun addWorkoutNote(workout:Workout, con: Context) {
    //Dialog to add note Workout().notes
    val title = "Add note"
    val mDialog = LayoutInflater.from(con)
        .inflate(R.layout.dialog_add_note, null)
    val mBuilder = AlertDialog.Builder(con)
        .setView(mDialog)
        .setTitle(title)

    val mAlertDialog = mBuilder.show()



    var note=workout.notes



    if(!(workout.notes.isNullOrBlank())){

        mDialog.noteET.setText(note)
    }

    mAlertDialog.setOnDismissListener(){

        note=mDialog.noteET.text.toString()

        if(!(note.isNullOrBlank())){
            workout.notes=note
        }

    }
    mAlertDialog.addBtn.setOnClickListener {
        mAlertDialog.dismiss()
    }

}

// functions regarding Workouts / WorkoutPlan

fun saveWorkout(con: Context){ // Stops workoutTime, adds activeworkout to Workouts.workoutList, deletes activeWorkoutFile //todo should set activeWorkout null ?
    try {
        Log.d("myTag","Workouts.activeWorkout!!.workoutStopTime() ERROR?")
        Workouts.activeWorkout!!.workoutStopTime()
        Log.d("myTag","Workouts.activeWorkout!!.workoutStopTime() NO ERROR")

        Workouts.workoutsList.add(0, Workouts.activeWorkout!!)
        Log.d("myTag","Workouts.workoutsList.add(0, Workouts.activeWorkout!!)")
        Log.d("myTag","activeWorkout SAVED")

        DataChangeLogger.alteredData["workoutList"]=true

        File(con.filesDir,"activeWorkoutFile").delete() // DELETE activeWorkoutFile so it does not load on app start
        Log.d("myTag","activeWorkoutFile DELETED")


    }
    catch (e: Exception) {
        Log.d("myTag","ERROR saveWorkout(): ${e}")
    }
}

fun updateWorkoutPlan(){
    setWorkoutPlanLastWorkout()
    setWorkoutPlanNextWorkout()
    DataChangeLogger.alteredData["workoutPlan"]=true
}

fun setWorkoutPlanLastWorkout() {
    Workouts.workoutPlan!!.programName= Workouts.activeWorkout!!.programName
    Workouts.workoutPlan!!.lastTrainingName= Workouts.activeWorkout!!.trainingName
}

fun setWorkoutPlanNextWorkout(){

    if(Workouts.workoutPlan!!.lastTrainingName!=null){
        var indexProg= Programs.programList.indexOf(Program(Workouts.workoutPlan!!.programName!!))
        var indexTrain=
            Programs.programList[indexProg].trainingList.indexOf(Training(Workouts.workoutPlan!!.lastTrainingName!!))
        var nextIndex=indexTrain+1
        val trainingListSize = Programs.programList[indexProg].trainingList.size

        //Log.d("myTag","setNextWorkout() trainingListSize  ${trainingListSize}")

        if(trainingListSize>1){

            if(nextIndex<trainingListSize) {

                Workouts.workoutPlan!!.nextTrainingName =
                    Programs.programList[indexProg].trainingList[nextIndex].name

                Log.d("myTag","setNextWorkout() nextTrainingName  ${ Workouts.workoutPlan!!.nextTrainingName}")
            }
            else{
                Workouts.workoutPlan!!.nextTrainingName= Programs.programList[indexProg].trainingList[0].name
            }
        }
        else{
            Workouts.workoutPlan!!.nextTrainingName= Programs.programList[indexProg].trainingList[0].name
        }

    }
}


// addToList used in FAB-button to add items to list shown in activity
fun addToList(name: String, con:Context, adapterpos:Int?){ //

    //val toastUniqueAlert=toastCenterShort(con, "Name has to be unique")
    //val toastSuccessfullAdd=toastCenterShort(con,"Added successfully!")

    val mDialog = LayoutInflater.from(con)
        .inflate(R.layout.dialog_rename_layout, null)    //dialog to add Program
    mDialog.insert_name_btn.text="Add"
    val mBuilder = AlertDialog.Builder(con)
        .setView(mDialog)
        .setTitle("Add "+name)
    val mAlertDialog = mBuilder.show()
    mDialog.insert_name_btn.setOnClickListener {
        mAlertDialog.dismiss()
        val objName: String = mDialog.insert_name.text.toString()   // read input

        if (objName.length > 0 && objName.length < maxNameLength) { // filter input

            when (name) {
                "program" -> {
                    if (programList.indexOf(Program(objName)) == -1) {
                        // Log.d("myTag", "programList.indexOf(Program(objName)) == ${programList.indexOf(Program(objName))}")
                        programList.add(Program(objName))

                        //toastCenterShort(con,"Added successfully!")

                        DataChangeLogger.alteredData["programList"]=true

                        val intent = Intent(con, ProgramsActivity::class.java)
                        con.startActivity(intent)

                    }
                    else{
                        toastCenterShort(con, "Name has to be unique")
                    }
                    s.selProgram=null // TODO is s.selProgram=null correct ??
                }
                "training" -> {
                    if(programList[s.selProgram!!].trainingList.indexOf(Training(objName))==-1) {
                        programList[s.selProgram!!].trainingList.add(Training(objName))

                        //toastCenterShort(con,"Added successfully!")

                        DataChangeLogger.alteredData["programList"]=true

                        val intent = Intent(con, TrainingsActivity::class.java)
                        con.startActivity(intent)

                    }
                    else{
                        toastCenterShort(con, "Name has to be unique")
                    }
                    s.selTraining=null
                }
                "exercise" -> {
                    if(programList[s.selProgram!!].trainingList[s.selTraining!!].exerciseList.indexOf(Exercise(objName))==-1) {
                        programList[s.selProgram!!].trainingList[s.selTraining!!].exerciseList.add(Exercise(objName))

                        //toastCenterShort(con,"Added successfully!")

                        DataChangeLogger.alteredData["programList"]=true

                        val intent = Intent(con, exercisesActivity::class.java)
                        con.startActivity(intent)

                    }
                    else{
                        toastCenterShort(con, "Name has to be unique")
                    }
                    s.selexercise=null // TODO refactor rename s.selexercise -> s.selExercise
                }
                "exercise to workout" -> {
                    if(Workouts.workoutsList[adapterpos!!].workoutExerciseList.indexOf(Exercise(objName))==-1&&objName!=null&&objName!="") {
                        // Log.d("myTag","${Workouts.workoutsList[adapterpos].workoutExerciseList}")
                        Workouts.workoutsList[adapterpos].workoutExerciseList.add(Exercise(objName))
                        // Log.d("myTag","${Workouts.workoutsList[adapterpos].workoutExerciseList}")
//
                        DataChangeLogger.alteredData["workoutList"]=true

                        val intent = Intent(con, WorkoutsViewWorkoutWorksetsActivity::class.java)
                        intent.putExtra("returnadapterpos",adapterpos)
                        intent.putExtra("trainingName",Workouts.workoutsList[adapterpos].trainingName)
                        intent.putExtra("wot",Workouts.workoutsList[adapterpos].workoutTime["hours"].toString()+":"+Workouts.workoutsList[adapterpos].workoutTime["minutes"].toString())
                        con.startActivity(intent)

                    }
                    else{
                        toastCenterShort(con, "Name has to be unique")
                    }
                }

                /*
                "workset to exercise" -> {
                    var newWorkset=Workset(null,null,null)

                    val mDialog1 = LayoutInflater.from(con)
                        .inflate(R.layout.dialog_add_workset, null)
                    mDialog1.add_btn.text = "Add"
                    val mBuilder1 = AlertDialog.Builder(con)
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

                            Workouts.workoutsList..add(newWorkset)

                            val msgSuccessfullAdd=name+" added successfully!"
                            toastCenterShort(this, msgSuccessfullAdd)

                            finish()
                            startActivity(intent)

                        }
                    Log.d("myTag","${Workouts.workoutsList[adapterpos!!].workoutExerciseList}")
                    Workouts.workoutsList[adapterpos!!].workoutExerciseList.add(Exercise(objName))
                    Log.d("myTag","${Workouts.workoutsList[adapterpos!!].workoutExerciseList}")

                    DataChangeLogger.alteredData["workoutList"]=true

                    val intent = Intent(con, WorkoutsViewWorkoutWorksetsActivity::class.java)
                    intent.putExtra("returnadapterpos",adapterpos)
                    con.startActivity(intent)
                    toastCenterShort(con,"Added successfully!")

                }

                 */

            }

        } else {
            var alertDialog =
                AlertDialog.Builder(con).create()    // alert if invalid name given
            alertDialog.setTitle("Alert")
            alertDialog.setMessage("Invalid programName")
            toastCenterShort(con,"Name has to be 1-${maxNameLength} characters long")
        }
    }
}
