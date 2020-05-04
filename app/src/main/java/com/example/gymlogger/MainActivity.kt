package com.example.gymlogger

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import kotlinx.android.synthetic.main.activity_main.*
import android.app.Application
import android.content.Context
import android.util.Log
import android.view.Gravity
import android.widget.*
import com.example.gymlogger.Programs.programList
import com.example.gymlogger.R.layout
import com.example.gymlogger.Workouts.activeWorkout
import kotlinx.android.synthetic.main.dialog_ask_yes_no.view.no_btn
import kotlinx.android.synthetic.main.dialog_ask_yes_no.view.yes_btn
import kotlinx.android.synthetic.main.dialog_ask_yes_no_stop.view.*
import java.io.File
import java.time.LocalDateTime

var s= Selection()
var dataLoaded:Boolean=false

// timedifference variables used in onPause onStop events
var writeTime:Long=0
val timeDelta=4000

class MainActivity() : AppCompatActivity() {

    //TODO ADD dialog to add workout manually
    //TODO FEATURE workoutsviewworkout editor view, pickers/edit items names/times/workouts
    // todo add log s to everything
    // todo add try catch to everything

    /* TODO feature rate your workouts?
        Strong! Power!
        Nuttin but a peanut / Aint nuttin to it but to do it
        Meh
        Weak
        stars/dumbells/5?
     */

    /* TODO BUTTON workset history for activeWorkout exercise
        IF previous SAME training, IF previous SAME exercise
        IF  worksets,
         show worksets,
         BUTTON to copy worksets to current activeWorkout ?
         8/4/20 no change.
     */

    /* TODO Feature REST TIME TIMER https://www.youtube.com/watch?v=xtElLuzjA0U
        8/4/20 no change.
     */

    /* TODO Feature implement rearranging list. <- necessary?
        8/4/20 no change. Is necessary to arrange training order
     */

    /* TODO Feature Workouts sorting -> show workouts as list
        sort by program
        sort by training
        8/4/20 no change
     */

    /* TODO Feature Workouts activity -> show workouts in calendar -> show workouts in calendar -> color code different trainings
        8/4/20 no change.
     */

    /* TODO Feature Workouts activity -> show workouts -> longclick to continue workout?
        8/4/20 no change. necessary?
     */

    /* TODO Feature activeworkoutExerciseView -> show last workout sets in active workout?
        8/4/20 no change. Useful
     */

    /* TODO Feature Settings
        Settings kgs-pds
        Settings light-dark mode
        8/4/20 no change.
     */
    /* TODO deny button double clicks
        8/4/20 no change.
     */

    /* TODO Feature Export workouts as csv?
        8/4/20 no change.
     */

    /* TODO ADD Feature Notes to Workout()
        8/4/20 DONE
     */

    /* TODO ADD 'rp' rest-pause reps to workoutSet
        8/4/20 no change.
     */

    /* TODO DATA WRITE activeWorkout -> CASE app close with activeWorkout!=null
        programName
        trainingName
        workoutExerciseList
        worksets
        time
        8/4/20 I think this is done? App loads with activeworkout!=null. Check datawriteread
    */
    /*
       TODO DATA READ activeWorkout -> CASE app closed with activeWorkout!=null
        8/4/20 I think this is done? App loads with activeworkout!=null. Check datawriteread
    */
    /* TODO set SELECTORs NULL
        8/4/20 what? App functions expectedly
     */

    /* TODO 8/4/20 ask to save activeworkout exercises
        to programs/trainings/exerciselist on activeworkout exit
        if activeworkout/exercises != programs/trainings/exerciselist
    */

    /* todo 8/4/20
        BUGFIX
        status: unconfirmed
        description: App crashes with activeworkout==true if user changes training programs name, then  exits activeworkout
    */

override fun onPause() {
    super.onPause()
    Log.d("myTag", "Main onPAUSE")
    if(writeTime+timeDelta<System.currentTimeMillis()){
        DataWrite(this, DataChangeLogger.check())
        writeTime=System.currentTimeMillis()
        DataChangeLogger.setFalse()
    }

}
override fun onStop() {
    super.onStop()
    Log.d("myTag", "Main onSTOP")
    if(writeTime+timeDelta<System.currentTimeMillis()){
        DataWrite(this, DataChangeLogger.check())
        writeTime=System.currentTimeMillis()
        DataChangeLogger.setFalse()
    }
}
/* unuseful events
override fun onResume() {
    super.onResume()
    Log.d("myTag", "Main onRESUME")
}
override fun onStart() {
    super.onStart()
    Log.d("myTag", "Main onSTART")
}
override fun onRestart() {
    super.onRestart()
    Log.d("myTag", "Main onRESTART")
}
 */



override fun onDestroy() {
    super.onDestroy()
    Log.d("myTag", "Main onDestroy")

}



override fun onBackPressed() {
/* todo dialog if active wo, save/discard wo /no
    8/4/20 no change. App works fine without.
 */
    Log.d("myTag", "Main onbackpress")
    finishAffinity()
}

override fun onCreate(savedInstanceState: Bundle?) { // ONCREATE
    super.onCreate(savedInstanceState)
    setContentView(layout.activity_main)

    Log.d("myTag", "Main onCREATE")

    if(dataLoaded==false){
        Log.d("myTag", "DataRead()")
        DataRead(this)
        dataLoaded=true
    }
    else{
        Log.d("myTag", "data already read!")
    }
    // DEBUG flavor build
    if(Constant.type==Constant.Type.FLAVOR){

        timer_btn.setOnClickListener { // Btn -> TimerActivity
            var intent = Intent(this, TimerActivity::class.java)
            startActivity(intent)
        }

        save_test_btn.text="SAVE WO&&programs"
        save_test_btn.setOnClickListener {
            // SAVE DATA TEST / JSON GSON TEST
            DataWrite(this, DataChangeLogger.check())
        }

        read_test_btn.setOnClickListener { // READ DATA TEST / JSON GSON TEST
            DataRead(this)

        }
        button2.setOnClickListener { // DELETE SAMPLE DATA
            activeWorkout = null
            Workouts.workoutPlan=null
            Workouts.workoutsList.clear()
            programList.clear()

            Log.d("myTag", "Deleting data...")
            val myFile1 = File(this.filesDir, "workoutsFile")
            val myFile2 = File(this.filesDir, "programsFile")
            val myFile3 = File(this.filesDir, "workoutPlanFile")
            val myFile4 = File(this.filesDir, "activeWorkoutFile")
            if (myFile1.exists()) {
                Log.d("myTag", "DELETING workoutsFile")
                myFile1.delete()
            }
            if (myFile2.exists()){
                Log.d("myTag", "DELETING programsFile")
                myFile2.delete()
            }
            if (myFile3.exists()){
                Log.d("myTag", "DELETING workoutPlanFile")
                myFile3.delete()
            }
            if (myFile4.exists()){
                Log.d("myTag", "DELETING workoutPlanFile")
                myFile4.delete()
            }
            Log.d("myTag", "Deleted")
            var intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        // debugging text field thingy
        textView.text="activeWorkout=   ${activeWorkout}\n" +
                "programname    =   ${activeWorkout?.programName}\n" +
                "trainingname   =   ${activeWorkout?.trainingName}\n" +
                "woPlan         =   ${Workouts.workoutPlan}\n" +
                "woprogram      =   ${Workouts.workoutPlan?.programName}\n" +
                "last training  =   ${Workouts.workoutPlan?.lastTrainingName}\n" +
                "next training  =   ${Workouts.workoutPlan?.nextTrainingName}\n" +
                "dataLoaded     =   ${dataLoaded}\n" +
                "workoutsFile   =   ${File(this.filesDir, "workoutsFile").exists()}\n"+
                "workoutPlanFile   =   ${File(this.filesDir, "workoutPlanFile").exists()}\n"+
                "programsFile   =   ${File(this.filesDir, "programsFile").exists()}\n"

        btn_add_sample_data.setOnClickListener { // Btn LOAD SAMPLEDATA
            /*
            programList.add(Program("Push/pull"))
            programList[0].trainingList.add(Training("1Training1"))
            programList[0].trainingList.add(Training("2Training2"))
            programList[0].trainingList[0].exerciseList.add(Exercise("row"))

            val wo = Workout()
            val wo2 = Workout()
            wo.programName="P1"
            wo.trainingName="T1"

            wo2.programName="Program2"
            wo2.trainingName="training2"

            wo.workoutStopTime()
            wo2.workoutStopTime()

            Workouts.workoutsList.add(wo)
            Workouts.workoutsList.add(wo2)
             */
        }

    }
    else if(Constant.type==Constant.Type.PHONE){
        val now=LocalDateTime.now()
        textView.text=now.dayOfMonth.toString()+"/"+now.monthValue+"\n"+now.dayOfWeek
    }


    btn_programs.setOnClickListener { // Btn -> ProgramsActivity
        var intent = Intent(this, ProgramsActivity::class.java)
        startActivity(intent)
    }

    workouts_btn.setOnClickListener { // Bnt -> WorkoutsActivity
        var intent = Intent(this, WorkoutsActivity::class.java)
        startActivity(intent)
    }


    if (Workouts.activeWorkout != null) { // Btn text
        btn_start_straining.text = "Continue training"
    }
    btn_start_straining.setOnClickListener { // Btn -> Start workout -> WorkoutStartActivity
        val intent = Intent(this, WorkoutStartActivity::class.java)

        if (Workouts.activeWorkout == null) { //case no started workout

            s.setNull() // reset selector/navigator/etc

            var newWorkout = Workout()
            //Workouts.activeWorkout = newWorkout

            Log.d("myTag","workoutPlan              ${Workouts.workoutPlan}")
            Log.d("myTag","workoutPlan?.programName ${Workouts.workoutPlan?.programName}")


            if(Workouts.workoutPlan!=null){ // If WorkoutPlan ie. finished workouts

                // get indexes of workoutPlan program && training
                val progIndex=getProgramIndex(Workouts.workoutPlan!!.programName!!)


                // if program exists ie. hasn't been renamed/deleted
                if (progIndex>=0){

                    val nextTrainIndex= getTrainingIndex(progIndex, Workouts.workoutPlan!!.nextTrainingName!!)
                    // if training exists ie. hasn't been renamed/deleted
                    if(nextTrainIndex>=0){

                        val title = "Start next workout " + Workouts.workoutPlan?.nextTrainingName
                        val mDialog = LayoutInflater.from(this)
                            .inflate(layout.dialog_ask_yes_no, null)
                        val mBuilder = AlertDialog.Builder(this)
                            .setView(mDialog)
                            .setTitle(title)
                        val mAlertDialog = mBuilder.show()

                        mDialog.yes_btn.setOnClickListener {
                            Log.d("myTag", "'Start next workout' YES ")
                            mAlertDialog.dismiss()

                            Workouts.activeWorkout = newWorkout
                            /*todo WATCH
                                8/4/20 what?
                             */

                            setActiveWorkout(Workouts.workoutPlan!!.programName!!, Workouts.workoutPlan!!.nextTrainingName!!)

                            Log.d("myTag","STARTING WORKOUTPLAN NEXTWORKOUT") // TEST
                            startActivity(intent)
                        }
                        mDialog.no_btn.setOnClickListener {
                            Workouts.workoutPlan=null
                            Log.d("myTag", "'Start next workout' NO ")
                            Log.d("myTag", "Workouts.workoutPlan = null --> RUNNING selectAndStartWorkout(this)")
                            selectAndStartWorkout(this) // START WORKOUT

                            mAlertDialog.dismiss()
                        }
                    }
                    // If workoutPlan nexttraining not in programList[progIndex].trainingList
                    else{
                        /* todo fix this regarding starting null value program/training
                            8/4/20 Think this is fixed. No null value starts anymore.
                         */
                        Log.d("myTag", "11workoutPlan.programName not in programList --> RUN selectAndStartWorkout(this)")
                        Workouts.workoutPlan=null
                        activeWorkout=null
                        Log.d("myTag", "12workoutPlan.programName not in programList --> RUN selectAndStartWorkout(this)")
                        selectAndStartWorkout(this)

                    }
                }
                // If workoutPlan program not in programList
                else{
                    /* todo fix this regarding starting null value program/training
                        8/4/20 Think this is fixed. No null value starts anymore.
                    */
                    Log.d("myTag", "21workoutPlan.programName not in programList --> RUN selectAndStartWorkout(this)")
                    Workouts.workoutPlan=null
                    activeWorkout=null
                    Log.d("myTag", "22workoutPlan.programName not in programList --> RUN selectAndStartWorkout(this)")
                    selectAndStartWorkout(this) // Start workout selection

                }
            }
            else if(Workouts.workoutPlan==null){
                Log.d("myTag", "Workouts.workoutPlan==null --> RUNNING selectAndStartWorkout(this)")
                selectAndStartWorkout(this) // Start workout selection
            }

        } else if (activeWorkout != null) { // case workout is active

            /* todo fix this regarding starting null value program/training
                8/4/20 Think this is fixed. No null value starts anymore.
            */
            if(activeWorkout!!.programName.isNullOrEmpty()){
                selectAndStartWorkout(this)
            }
            else if(activeWorkout!!.trainingName.isNullOrEmpty()){
                selectAndStartWorkout(this)
            }


            val title = "Continue with started workout?"
            val mDialog = LayoutInflater.from(this)
                .inflate(layout.dialog_ask_yes_no_stop, null)
            /* TODO change inflater layout -> 2 button Y/N('continue'/'stop') layout?
                8/4/20 current yes/no/stop layout is working. altough misclick to "stop workout" can happen
             */
            val mBuilder = AlertDialog.Builder(this)
                .setView(mDialog)
                .setTitle(title)
            val mAlertDialog = mBuilder.show()
            mDialog.yes_btn.setOnClickListener {
                Log.d("myTag", "-->CONTINUING WORKOUT")
                startActivity(intent)
                mAlertDialog.dismiss()
            }
            mDialog.stop_btn.setOnClickListener {
                mAlertDialog.dismiss()


                //
                if(Workouts.workoutPlan==null){

                    Workouts.workoutPlan=WorkoutPlan()

                }
                if(!(activeWorkout!!.programName.isNullOrEmpty())) {
                    if (!(activeWorkout!!.trainingName.isNullOrEmpty())) {

                        activeWorkout!!.workoutStopTime()
                        saveWorkout(this)

                        updateWorkoutPlan()
                    }
                }
                else{
                    Workouts.workoutPlan=null
                }

                activeWorkout=null

                finish()
                startActivity(Intent(this, MainActivity::class.java))
            }
        }
    }
} // ONCREATE END
} //APP END


fun toastCenterShort(context: Context, msg: String) {
    val toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT)
    toast.setGravity(Gravity.CENTER, 0, 0)
    toast.show()
}

class Selection :
    Application() { // Tracks which object clicked. Used in page navigation as list indexes

    var selProgram: Int? = null
    var selTraining: Int? = null
    var selexercise: Int? = null
    var selWorkout: Int? = null

    fun setNull() {
        this.selProgram = null
        this.selTraining = null
        this.selexercise = null
        this.selWorkout = null
    }

    fun show() {
        Log.d(
            "myTag", "s.selProgram: ${this.selProgram}\n" +
                    "s.selTraining: ${this.selTraining}\n" +
                    "s.selexercise: ${this.selexercise}\n" +
                    "s.selWorkout:  ${this.selWorkout}"
        )
    }

}




