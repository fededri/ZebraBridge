package com.ezcorp.ezroam.zebraBridge.implementations

import android.graphics.BitmapFactory
import com.ezcorp.ezroam.zebraBridge.di.ApplicationSingleton
import com.ezcorp.ezroam.zebraBridge.dispatchNewTask
import com.ezcorp.ezroam.zebraBridge.interfaces.ImagePrinterProtocol
import com.ezcorp.ezroam.zebraBridge.models.ResolveTypes
import com.facebook.react.bridge.Promise
import com.zebra.sdk.graphics.internal.ZebraImageAndroid
import com.zebra.sdk.printer.ZebraPrinterFactory

private const val KEEP_WIDTH = 0
private const val KEEP_HEIGHT = 0
private const val STARTING_X_POS = 0
private const val STARTING_Y_POS = 0

class ImagePrinterImplementation : ImagePrinterProtocol {


    override fun printImage(path: String, promise: Promise) {
        dispatchNewTask(Runnable {
            try {
                val bitmap = BitmapFactory.decodeFile(path)
                val zebraImage = ZebraImageAndroid(bitmap)
                val printer = ZebraPrinterFactory.getInstance(ApplicationSingleton.printerConnection)
                printer.printImage(zebraImage, STARTING_X_POS, STARTING_Y_POS, KEEP_WIDTH, KEEP_HEIGHT, false)
                promise.resolve(ResolveTypes.PRINT_SENT)
            } catch (e: Exception) {
                e.printStackTrace()
                promise.reject(e)
            }
        })
    }
}