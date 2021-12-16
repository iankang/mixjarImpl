package com.lunna.mixjarimpl

import android.content.Context
import androidx.room.Room
import com.lunna.mixjarimpl.db.MixjarImplDB
import com.lunna.mixjarimpl.db.dao.*
import com.lunna.mixjarimpl.db.entities.FollowersEntity
import com.lunna.mixjarimpl.pagingSources.FollowersSource
import com.lunna.mixjarimpl.repository.*
import com.lunna.mixjarimpl.viewmodels.*
import com.mixsteroids.mixjar.MixCloud
import com.mixsteroids.mixjar.utils.Mixcloud
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val databaseModule = module {
    fun provideDB(context: Context): MixjarImplDB {
               return Room.databaseBuilder(
                    context.applicationContext,
                    MixjarImplDB::class.java,
                    "mixjar_db"
                )
                    .fallbackToDestructiveMigration()
                    .build()
    }

    fun provideProfileDAO(db:MixjarImplDB): ProfileDAO{
        return db.profileDAO
    }
    fun provideFollowingDAO(db:MixjarImplDB):FollowingDAO{
        return db.followingDAO
    }

    fun provideFollowingPagingDAO(db:MixjarImplDB):FollowingPagingDAO{
        return db.followingPagingDAO
    }
    fun provideFollowersDAO(db:MixjarImplDB):FollowersDAO{
        return db.followersDAO
    }
    fun provideFollowersPagingDAO(db:MixjarImplDB):FollowersPagingDAO{
        return db.followersPagingDAO
    }

    single {provideDB(androidApplication())}
    single {provideProfileDAO(get())}
    single { provideFollowingDAO(get()) }
    single { provideFollowingPagingDAO(get()) }
    single { provideFollowersDAO(get()) }
    single { provideFollowersPagingDAO(get()) }
}

val repositoryModule = module {

    fun provideProfileRepository(db: MixjarImplDB):ProfileRepository{
        return ProfileRepository(db)
    }

    fun provideFollowerRepository(db:MixjarImplDB): FollowersRepository{
        return FollowersRepository(db)
    }

    fun provideFollowingRepository(db:MixjarImplDB):FollowingRepository{
        return FollowingRepository(db)
    }

    fun provideFollowingPagingRepsitory(db:MixjarImplDB):FollowingPagingRepository{
        return FollowingPagingRepository(db)
    }

    fun provideFollowersPagingRepository(db:MixjarImplDB):FollowersPagingRepository{
        return FollowersPagingRepository(db)
    }

    single { provideProfileRepository(get()) }
    single { provideFollowerRepository(get()) }
    single { provideFollowingRepository(get()) }
    single {provideFollowingPagingRepsitory(get())}
    single {provideFollowersPagingRepository(get())}
}

val viewModelModule = module {
    viewModel {
        ProfileViewModel(profileRepository = get())

    }
    viewModel {
        MixjarViewModel()
    }
    viewModel {
        FeedViewModel()
    }
    viewModel {
        FollowingViewModel(get(),get())
    }
    viewModel {
        FollowersViewModel(get(),get())
    }

}
