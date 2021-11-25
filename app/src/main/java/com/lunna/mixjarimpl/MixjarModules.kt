package com.lunna.mixjarimpl

import android.content.Context
import androidx.room.Room
import com.lunna.mixjarimpl.db.MixjarImplDB
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val databaseModule = module {
    fun provideDB(context: Context): MixjarImplDB {
        synchronized(this){
               return Room.databaseBuilder(
                    context.applicationContext,
                    MixjarImplDB::class.java,
                    "mixjar_db"
                )
                    .fallbackToDestructiveMigration()
                    .build()
        }
    }

    single { provideDB(androidApplication()) }
}