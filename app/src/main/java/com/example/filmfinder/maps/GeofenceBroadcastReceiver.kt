package com.example.filmfinder.maps

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

public class GeofenceBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        Toast.makeText(context, "Кинотеатр находится рядом с вами!", Toast.LENGTH_SHORT).show()
    }
}