package com.lunna.mixjarimpl.viewmodels

import androidx.lifecycle.ViewModel
import com.lunna.mixjarimpl.repository.FollowingPagingRepository
import com.lunna.mixjarimpl.repository.FollowingRepository

class FollowingViewModel(
    followingRepository: FollowingRepository,
    followingPagingRepository: FollowingPagingRepository
) : ViewModel(){

}