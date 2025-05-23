package com.example.pantallas

import android.app.Application
import android.util.Log
import com.google.firebase.FirebaseApp

class CiudadActivaApp : Application() {
    override fun onCreate() {
        super.onCreate()
        try {
            if (!FirebaseApp.getApps(this).any()) {
                FirebaseApp.initializeApp(this)
                Log.d("CiudadActivaApp", "Firebase inicializado correctamente")
            }
        } catch (e: Exception) {
            Log.e("CiudadActivaApp", "Error al inicializar Firebase", e)
        }
    }
} 