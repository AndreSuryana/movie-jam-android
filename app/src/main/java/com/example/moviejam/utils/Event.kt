package com.example.moviejam.utils

open class Event<out T>(private val data: T) {

    var handled = false
        private set // This is for external read only

    fun getDataIfNotHandledYet(): T? {
        return if (handled) {
            null
        } else {
            handled = true
            data
        }
    }

    fun peekData(): T = data
}