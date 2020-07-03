package com.ezcorp.ezroam.zebraBridge.implementations

import android.content.ContentResolver
import android.net.Uri
import com.ezcorp.ezroam.zebraBridge.di.ApplicationSingleton
import com.ezcorp.ezroam.zebraBridge.dispatchNewTask
import com.ezcorp.ezroam.zebraBridge.getConnection
import com.ezcorp.ezroam.zebraBridge.interfaces.FileSender
import com.ezcorp.ezroam.zebraBridge.models.ResolveTypes
import com.facebook.react.bridge.Promise
import com.zebra.sdk.printer.ZebraPrinterFactory
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream

class FileSenderImplementation : FileSender {

    override fun sendFile(path: String, isAbsolutePath: Boolean, promise: Promise) {
        dispatchNewTask(Runnable {
            try {
                if (isAbsolutePath) {
                    val printer = ZebraPrinterFactory.getInstance(getConnection())
                    printer.sendFileContents(path)
                } else {
                    val byteArray = ApplicationSingleton.contentResolver?.openInputStream(Uri.parse(path))?.readAllBytes()
                    getConnection()?.write(byteArray)
                }
                promise.resolve(ResolveTypes.PRINT_SENT.value)
            } catch (e: Exception) {
                promise.reject(e)
            }
        })
    }
}

@Throws(IOException::class)
fun InputStream.readAllBytes(): ByteArray {
    val bufLen = 4 * 0x400
    val buf = ByteArray(bufLen)
    var readLen: Int = 0

    ByteArrayOutputStream().use { outputStream ->
        this.use { inputStream ->
            while (inputStream.read(buf, 0, bufLen).also { readLen = it } != -1)
                outputStream.write(buf, 0, readLen)
        }

        return outputStream.toByteArray()
    }
}