package com.zebra.ezroam.zebraBridge.interfaces

import com.facebook.react.bridge.Promise

interface ImagePrinterProtocol {
    fun printImage(path: String, promise: Promise)
}