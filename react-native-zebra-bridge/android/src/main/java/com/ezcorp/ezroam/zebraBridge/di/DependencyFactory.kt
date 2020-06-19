package com.ezcorp.ezroam.zebraBridge.di

import com.ezcorp.ezroam.zebraBridge.ZebraBridgeModule
import com.ezcorp.ezroam.zebraBridge.implementations.ConnectorImplementation
import com.facebook.react.bridge.ReactApplicationContext

object DependencyFactory{

    fun getZebraModule(context : ReactApplicationContext) : ZebraBridgeModule{
        return ZebraBridgeModule(context, ConnectorImplementation())
    }
}