package com.example.filmfinder

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class ConnectivityActionBroadcastReceiver:BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        Toast.makeText(context,"Произошло изменение в сетевом подключении!",Toast.LENGTH_SHORT).show()
    }
}