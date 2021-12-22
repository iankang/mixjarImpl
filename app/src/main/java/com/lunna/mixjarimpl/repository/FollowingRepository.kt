package com.lunna.mixjarimpl.repository

import androidx.paging.PagingSource
import com.lunna.mixjarimpl.db.MixjarImplDB
import com.lunna.mixjarimpl.db.entities.FollowingEntity
import com.mixsteroids.mixjar.models.UserFollowingResponseData

class FollowingRepository(
    val db:MixjarImplDB
){

    suspend fun addFollower(FollowingEntity: FollowingEntity?){
        db.followingDAO.insertFollowing(FollowingEntity)
    }

    suspend fun addManyFollowers(FollowingEntity: List<FollowingEntity>){
        db.followingDAO.insertManyFollowing(FollowingEntity)
    }

    fun getAllFollowers(): List<FollowingEntity?> {
        return db.followingDAO.getAllFollowing()
    }

    fun getAllFollowersByMainUser(mainUser:String): PagingSource<Int, FollowingEntity> {
        return db.followingDAO.getAllFollowingByMainUser(mainUser)
    }

    fun getFollowerByUsername(username:String): FollowingEntity?{
        return db.followingDAO.getFollowingUser(username)
    }

//    fun getFollowersByUsernamePaged(username: String): PagingSource<Int, FollowingEntity> {
//        return db.followingDAO.getFollowerByUsernamePaged(username)
//    }

    suspend fun deleteAllFollowers(){
        db.followingDAO.deleteAll()
    }

    suspend fun deleteAllByUserName(username: String){
        db.followingDAO.deleteAllFromMainUser(username)
    }

    fun getTotalCount():Int?{
        return db.followingDAO.count()
    }

    fun getTotalCountByMainUser(mainUser: String):Int?{
        return db.followingDAO.countByMainUser(mainUser)
    }

}

fun UserFollowingResponseData.toFollowingEntity(mainUser: String?): FollowingEntity? {
    return key?.let {
        FollowingEntity(
            key = it,
            mainUser = mainUser,
            url = url,
            name = name,
            username = username,
            pictureUrl = pictures?.extra_large
        )
    }
}