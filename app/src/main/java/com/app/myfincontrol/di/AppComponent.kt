package com.app.myfincontrol.di

import android.content.Context
import com.app.myfincontrol.MainActivity
import com.app.myfincontrol.di.modules.AppModule
import dagger.BindsInstance
import dagger.Component
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance @ApplicationContext context: Context): AppComponent
    }

    fun inject(activity: MainActivity)
}