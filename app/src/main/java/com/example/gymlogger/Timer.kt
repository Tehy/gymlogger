package com.example.gymlogger

import java.time.LocalDateTime


/* TODO REST TIMER
    8/4/20 no change.
*/
class Timer (var startTime: LocalDateTime =LocalDateTime.now()){ // TODO remove unused class

    //val startTime = LocalDateTime.now()

    var stopTime: LocalDateTime? = null
}