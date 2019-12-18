package com.example.gymlogger

import java.time.LocalDateTime

class Timer (var startTime: LocalDateTime =LocalDateTime.now()){ // TODO remove unused class

    //val startTime = LocalDateTime.now()

    var stopTime: LocalDateTime? = null
}