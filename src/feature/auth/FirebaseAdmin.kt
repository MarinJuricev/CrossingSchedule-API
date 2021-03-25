package com.example.feature.auth

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import java.io.InputStream

object FirebaseAdmin {
    //TODO Inject the serviceAccount depending on the development flag
//    private val serviceAccount: InputStream = Base64.getDecoder().decode(System.getenv("FIREBASE_CONFIG"))
//        .run {
//            String(this).byteInputStream()
//        }

    private val serviceAccount: InputStream? =
        this::class.java.classLoader.getResourceAsStream("crossingschedule-firebase-adminsdk.json")

    private val options: FirebaseOptions = FirebaseOptions.builder()
        .setCredentials(GoogleCredentials.fromStream(serviceAccount))
        .build()

    fun init(): FirebaseApp = FirebaseApp.initializeApp(options)
}
