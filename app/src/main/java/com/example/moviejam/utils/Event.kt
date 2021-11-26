package com.example.moviejam.utils

open class Event<out T>(private val data: T) {

    private var handled = false

    fun getDataIfNotHandledYet(): T? {
        return if (handled) {
            null
        } else {
            handled = true
            data
        }
    }
}