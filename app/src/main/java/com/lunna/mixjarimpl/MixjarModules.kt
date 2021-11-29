package com.lunna.mixjarimpl

import android.content.Context
import androidx.room.Room
import com.lunna.mixjarimpl.db.MixjarImplDB
import com.lunna.mixjarimpl.db.dao.ProfileDAO
import com.lunna.mixjarimpl.repository.ProfileRepository
import com.lunna.mixjarimpl.viewmodels.FeedViewModel
import com.lunna.mixjarimpl.viewmodels.MixjarViewModel
import com.lunna.mixjarimpl.viewmodels.ProfileViewModel
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
    single {provideDB(androidApplication())}
    single {provideProfileDAO(get())}
}

val repositoryModule = module {

    fun provideProfileRepository(db: MixjarImplDB):ProfileRepository{
        return ProfileRepository(db)
    }
    single { provideProfileRepository(get()) }
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
}