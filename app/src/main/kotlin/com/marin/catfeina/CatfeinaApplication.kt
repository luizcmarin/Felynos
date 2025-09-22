/*
 * Arquivo: com.marin.catfeina.CatfeinaApplication.kt
 * @project Catfeina
 * @description
 * Classe Application personalizada para inicializar o Hilt na aplicação Catfeina.
 */
package com.marin.catfeina

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class CatfeinaApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // Local para inicializações globais, se necessário (ex: Timber)
        // if (BuildConfig.DEBUG) {
        //     Timber.plant(Timber.DebugTree())
        // }
    }
}