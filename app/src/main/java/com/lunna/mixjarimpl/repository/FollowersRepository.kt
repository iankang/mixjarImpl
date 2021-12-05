package com.lunna.mixjarimpl.repository

import androidx.paging.*
import com.lunna.mixjarimpl.db.MixjarImplDB
import com.lunna.mixjarimpl.db.entities.FollowersEntity
import com.mixsteroids.mixjar.models.UserFollowersData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class FollowersRepository(
    private val db:MixjarImplDB
) {
    suspend fun addFollower(followersEntity: FollowersEntity?){
        db.followersDAO.insertFollowers(followersEntity)
    }

   suspend fun addManyFollowers(followersEntity: List<FollowersEntity>){
        db.followersDAO.insertManyFollowers(followersEntity)
    }

    fun getAllFollowers(): List<FollowersEntity?> {
        return db.followersDAO.getAllFollowers()
    }

    fun getAllFollowersByMainUser(mainUser:String?):PagingSource<Int,FollowersEntity>{
        return db.followersDAO.getAllFollowersByMainUser(mainUser)
    }

    fun getFollowerByUsername(username:String):FollowersEntity?{
        return db.followersDAO.getFollowerByUsername(username)
    }

//    fun getFollowersByUsernamePaged(username: String): PagingSource<Int, FollowersEntity> {
//        return db.followersDAO.getFollowerByUsernamePaged(username)
//    }

   suspend fun deleteAllFollowers(){
        db.followersDAO.deleteAll()
    }

    suspend fun deleteAllByUserName(username: String){
        db.followersDAO.deleteAllFromUsername(username)
    }

    fun getTotalCount():Int?{
        return db.followersDAO.count()
    }

    fun getTotalCountByMainUser(mainUser: String?):Int?{
        return db.followersDAO.countByMainUser(mainUser)
    }
}

fun UserFollowersData.toFollowersEntity(mainUser: String?): FollowersEntity? {
    return key?.let {
        FollowersEntity(
        key = it,
        mainUser = mainUser,
        url = url,
        name = name,
        username =  username,
        pictureUrl =  pictures?.extra_large
    )
    }
}
