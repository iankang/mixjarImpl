package com.lunna.mixjarimpl.db

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

abstract class MixjarImplDB: RoomDatabase() {

    companion object {
        private var INSTANCE: MixjarImplDB? = null


    }
}