package com.lunna.mixjarimpl

import android.app.Activity
import android.app.Application
import android.os.Bundle
import androidx.multidex.MultiDexApplication
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MixjarApp: MultiDexApplication() {
 override fun onCreate() {
  super.onCreate()
  startKoin {
   androidLogger(Level.INFO)
   androidContext(applicationContext)
   modules(
    listOf(
     databaseModule,
     repositoryModule,
     viewModelModule
    )
   )
  }
 }

}