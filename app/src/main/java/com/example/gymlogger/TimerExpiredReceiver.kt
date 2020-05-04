package com.example.gymlogger

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.gymlogger.util.PrefUtil
import util.NotificationUtil

class TimerExpiredReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        NotificationUtil.showTimerExpired(context)

        PrefUtil.setTimerState(TimerActivity.TimerState.Stopped, context)
        PrefUtil.setAlarmSetTime(0, context)
    }
}

