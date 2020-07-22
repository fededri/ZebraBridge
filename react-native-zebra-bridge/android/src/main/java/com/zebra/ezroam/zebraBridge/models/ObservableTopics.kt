package com.zebra.ezroam.zebraBridge.models

//This enum class lists all of the observable topics that react-native code can subscribe to
enum class ObservableTopics(val value: String) {
    PRINTER_STATUS("ZebraPrinter"),
    PRINTERS_SEARCH("PrintersSearch")
}