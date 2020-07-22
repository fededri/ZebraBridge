package com.zebra.ezroam.zebraBridge.interfaces

import com.facebook.react.bridge.Promise

interface FileSender {

    /**
     * if @param isAbsolutePath is false, the string will be taken as an URI
     */
    fun sendFile(path: String, isAbsolutePath: Boolean, promise: Promise)
}