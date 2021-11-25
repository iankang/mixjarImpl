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

    @Insert(onConflict = OnConflictStrategy.IGNORE)
     fun insertProfile(profile:ProfileEntity)

    @Query("SELECT * FROM profileentity WHERE `key`=:key ")
     fun getProfileByKey(key: String): ProfileEntity

    @Query("DELETE FROM PROFILEENTITY")
     fun deleteAllProfiles()

    @Query("DELETE FROM profileentity WHERE `key`=:key ")
     fun deleteSingleProfileByKey(key:String)
}