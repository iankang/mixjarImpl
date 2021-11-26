package com.lunna.mixjarimpl.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.lunna.mixjarimpl.db.dao.ProfileDAO
import com.lunna.mixjarimpl.db.entities.ProfileEntity

@Database(entities = [ProfileEntity::class], version = 2, exportSchema = false)
abstract class MixjarImplDB: RoomDatabase() {
    abstract val profileDAO:ProfileDAO
}