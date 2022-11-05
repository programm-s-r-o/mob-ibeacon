package cz.programm.mobalarm.di

import cz.programm.mobalarm.ui.presenters.MainActivityPresenter
import org.koin.dsl.module

val appModule = module {
    factory { MainActivityPresenter() }
}