package me.xaanit.artemis.internal.events.pojo

abstract class Makeable<T> : Handleable() {
    abstract fun make(): T
}