package com.example.gymlogger

import com.example.gymlogger.Programs.programList

// functions to get index of Program/Training in programList/trainingList
// used in SelectAndStartWorkout()

fun getProgramIndex(programName:String):Int{
    return programList.indexOf(Program(programName))
}

fun getTrainingIndex(programIndex:Int,trainingName:String):Int{
    return programList[programIndex].trainingList.indexOf(Training(trainingName))
}