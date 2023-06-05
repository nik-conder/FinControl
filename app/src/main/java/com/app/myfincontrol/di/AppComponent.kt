package com.app.myfincontrol.di

import android.content.Context
import com.app.myfincontrol.MainActivity
import com.app.myfincontrol.di.modules.AppModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun inject(activity: MainActivity)

    val appModule: AppModule
}