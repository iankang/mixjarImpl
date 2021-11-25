package com.lunna.mixjarimpl.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.lunna.mixjarimpl.db.dao.ProfileDAO
import com.lunna.mixjarimpl.db.entities.ProfileEntity

@Database(entities = arrayOf(ProfileEntity::class), version = 2, exportSchema = false)
abstract class MixjarImplDB: RoomDatabase() {
    abstract val profileDAO:ProfileDAO
}