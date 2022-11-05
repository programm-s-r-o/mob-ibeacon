package cz.programm.mobalarm.di

import cz.programm.mobalarm.services.PermissionService
import cz.programm.mobalarm.ui.presenters.MainActivityPresenter
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val appModule = module {
    factory { MainActivityPresenter() }
    factory { PermissionService(androidContext()) }
}