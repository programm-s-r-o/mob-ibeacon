package cz.programm.mobalarm.di

import cz.programm.mobalarm.services.BeaconService
import cz.programm.mobalarm.services.PermissionService
import cz.programm.mobalarm.ui.presenters.MainActivityPresenter
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val appModule = module {
    factory { MainActivityPresenter(get()) }
    factory { PermissionService(androidContext()) }
    factory { BeaconService(androidContext()) }
}