package com.mathtech.quickmath.di

import com.mathtech.quickmath.data.datastore.DataStoreManager
import com.mathtech.quickmath.data.repository.IRepository
import com.mathtech.quickmath.domain.interactors.GetMathScoreStateUseCase
import com.mathtech.quickmath.domain.interactors.SaveMathScoreWithKeyUseCase
import com.mathtech.quickmath.domain.repository.RepositoryImpl
import com.mathtech.quickmath.presentation.ui.home.HomeViewModel
import com.mathtech.quickmath.presentation.ui.quickmathgame.QuickMathGameViewModel
import kotlinx.coroutines.Dispatchers
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val viewModelModule = module {
    singleOf(::HomeViewModel)
    factoryOf(::QuickMathGameViewModel)
}

val dispatcherModule = module {
    factory { Dispatchers.Default }
}

val dataSourceModule = module {
    single { DataStoreManager(get()) }
}

val useCaseModule = module {
    factory { GetMathScoreStateUseCase(get(), get()) }
    factory { SaveMathScoreWithKeyUseCase(get(), get()) }
}

val repositoryModule = module {
    single<IRepository> { RepositoryImpl(get()) }
}
