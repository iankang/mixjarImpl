package com.lunna.mixjarimpl.db

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

abstract class MixjarImplDB: RoomDatabase() {

    companion object {
        private var INSTANCE: MixjarImplDB? = null

        fun getInstance(context:Context):MixjarImplDB{
            synchronized(this){
                var instance = INSTANCE

                if(instance == null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        MixjarImplDB::class.java,
                        "mixjar_db"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                }
                return  instance
            }
        }
    }
}