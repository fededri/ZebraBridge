package com.zebra.ezroam.zebraBridge

import android.content.Context
import android.net.Uri
import com.zebra.ezroam.zebraBridge.di.ApplicationSingleton
import com.zebra.ezroam.zebraBridge.interfaces.*
import com.zebra.ezroam.zebraBridge.models.ObservableTopics
import com.zebra.ezroam.zebraBridge.models.ResolveTypes
import com.facebook.react.bridge.*
import com.facebook.react.modules.core.DeviceEventManagerModule
import com.zebra.sdk.comm.Connection
import com.zebra.sdk.comm.ConnectionException
import com.zebra.sdk.comm.TcpConnection
import com.zebra.sdk.printer.PrinterLanguage
import com.zebra.sdk.printer.ZebraPrinterFactory
import kotlinx.coroutines.*

internal const val STATUS_UPDATE_INTERVAL = 3000L // in milliseconds

class ZebraBridgeModule(private val reactContext: ReactApplicationContext,
                        private val connectionProtocol: ConnectionProtocol,
                        private val fileSender: FileSender,
                        private val lifecycleEventListener: LifecycleEventListener,
                        private val discoveryProtocol: DiscoveryProtocol,
                        private val imagePrinter: ImagePrinterProtocol
) : ReactContextBaseJavaModule(reactContext),
        ZebraProtocol,
        ConnectionProtocol by connectionProtocol,
        LifecycleEventListener by lifecycleEventListener,
        DiscoveryProtocol by discoveryProtocol,
        ImagePrinterProtocol by imagePrinter {

    private lateinit var eventEmitter: DeviceEventManagerModule.RCTDeviceEventEmitter
    private var printerStatusListenerJob: Job? = null

    override fun initialize() {
        super.initialize()
        eventEmitter = reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter::class.java)
        reactContext.addLifecycleEventListener(this)
    }

    override fun getName(): String {
        return "ZebraBridge"
    }

    //region Connection protocols
    @ReactMethod
    override fun connect(ip: String, port: Int, promise: Promise) {
        val uri: Uri = Uri.parse("")
        connectionProtocol.connect(ip, port, promise)
    }

    @ReactMethod
    override fun disconnect(promise: Promise) {
        connectionProtocol.disconnect(promise)
    }

    @ReactMethod
    override fun isConnectedToPrinter(promise: Promise) {
        connectionProtocol.isConnectedToPrinter(promise)
    }
    //endregion

    //region File Sender Protocols
    @ReactMethod
    override fun sendFile(path: String, isAbsolutePath: Boolean, promise: Promise) {
        if (isConnected()) {
            fileSender.sendFile(path, isAbsolutePath, promise)
        } else {
            promise.reject(Throwable("First you must be connected to the printer"))
        }
    }

    @ReactMethod
    override fun printImage(path: String, promise: Promise) {
        imagePrinter.printImage(path, promise)
    }
    //endregion


    //region Printer status
    @ReactMethod
    override fun startListeningPrinterStatus(promise: Promise) {
        if (isConnected()) {
            promise.resolve(ResolveTypes.SUCCESS.value)
            runBlocking(Dispatchers.IO) {
                printerStatusListenerJob = launch {
                    val connection = getConnection()
                    while (connection != null && connection.isConnected) {
                        try {
                            updatePrinterStatus(connection)
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                        delay(STATUS_UPDATE_INTERVAL)
                    }
                }
            }
        } else {
            promise.reject(Throwable("You are not connected to the printer"))
        }
    }

    @Throws(ConnectionException::class)
    private fun updatePrinterStatus(connection: Connection) {
        val printer = ZebraPrinterFactory.getInstance(connection)
        val map = printer.currentStatus.asMap()
        //Here we send the event to react native code
        eventEmitter.emit(ObservableTopics.PRINTER_STATUS.value, map)
    }

    @ReactMethod
    override fun stopListeningPrinterStatus() {
        printerStatusListenerJob?.cancel()
    }
    //endregion

    //region Discovery
    @ReactMethod
    fun discoverPrintersOnNetwork(promise: Promise) {
        discoveryProtocol.discoverPrintersOnNetwork(eventEmitter, promise)
    }
    //endregion

    //region Testing functions
    //Test method, makes al the corresponding actions to connect to the printer with the specified IP, and prints some dummy data
    @ReactMethod
    fun testPrinter(ip: String, promise: Promise) {
        runBlocking(Dispatchers.IO) {
            ApplicationSingleton.jobs.add(launch {
                try {
                    val connection = TcpConnection(ip, TcpConnection.DEFAULT_ZPL_TCP_PORT)
                    connection.open()
                    val printer = ZebraPrinterFactory.getInstance(connection)
                    val file = reactContext.getFileStreamPath("TEST.LBL")
                    val outputStream = reactContext.openFileOutput("TEST.LBL", Context.MODE_PRIVATE)
                    var configLabel: ByteArray? = null

                    val pl = printer.printerControlLanguage
                    if (pl == PrinterLanguage.ZPL) {
                        configLabel = "^XA^FO17,16^GB379,371,8^FS^FT65,255^A0N,135,134^FDTEST^FS^XZ".toByteArray()
                    } else if (pl == PrinterLanguage.CPCL) {
                        val cpclConfigLabel = """
                ! 0 200 200 406 1
                ON-FEED IGNORE
                BOX 20 20 380 380 8
                T 0 6 137 177 TEST
                PRINT
                
                """.trimIndent()
                        configLabel = cpclConfigLabel.toByteArray()
                    }
                    outputStream.write(configLabel) //we dont care if thread is blocked for this test
                    outputStream.flush()
                    outputStream.close()
                    printer.sendFileContents(file.absolutePath)
                    promise.resolve(ResolveTypes.SUCCESS.value)

                } catch (e: Exception) {
                    promise.reject(e)
                }

            })

        }

    }
    //endregion
}