package com.zebra.ezroam.zebraBridge.models

/**
 * Errors that wasn't caused due to exceptions
 */
enum class ErrorTypes(val value: String) {
    NO_CONNECTION_FOUND("no_connection_found"),
    SEARCH_PRINTER_ERROR("search_printer_error")
}

