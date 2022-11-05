package cz.programm.mobalarm

import android.app.Application
import cz.programm.mobalarm.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MobAlarmApp : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@MobAlarmApp)
            modules(appModule)
        }
    }
}