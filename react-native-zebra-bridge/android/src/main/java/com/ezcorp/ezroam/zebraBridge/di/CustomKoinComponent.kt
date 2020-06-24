package com.ezcorp.ezroam.zebraBridge.di

import com.ezcorp.ezroam.zebraBridge.ZebraBridgeModule
import org.koin.core.Koin
import org.koin.core.KoinComponent

interface CustomKoinComassponent : KoinComponent {
    override fun getKoin(): Koin {
        return ApplicationSingleton.koinApplication?.koin!!
    }
}