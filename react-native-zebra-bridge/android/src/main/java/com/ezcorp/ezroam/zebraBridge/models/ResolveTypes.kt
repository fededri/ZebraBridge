package com.ezcorp.ezroam.zebraBridge.models

//Messages sent to javascript code
enum class ResolveTypes(val value: Int) {
    SUCCESS(1),//for void methods
    PRINT_SENT(2),
    SEARCHING_PRINTERS(3), //sent when app started searching for printers
    SEARCHING_PRINTERS_FINISHED(4) //discovery has finished
}