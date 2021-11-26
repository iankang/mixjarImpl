package com.lunna.mixjarimpl.db.dao

import androidx.compose.runtime.MutableState
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.lunna.mixjarimpl.db.entities.ProfileEntity

/**
 * Describes profile entity database actions
 */
@Dao
interface ProfileDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun insertProfile(profile:ProfileEntity?)

    @Query("SELECT * FROM profileentity WHERE `username`=:key ")
     fun getProfileByUsername(key: String): ProfileEntity?

    @Query("DELETE FROM profileentity")
     fun deleteAllProfiles()

    @Query("DELETE FROM profileentity WHERE `username`=:key ")
     fun deleteSingleProfileByKey(key:String)
}