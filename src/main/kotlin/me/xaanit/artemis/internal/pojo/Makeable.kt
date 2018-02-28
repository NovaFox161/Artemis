package me.xaanit.artemis.internal.pojo

abstract class Makeable<T> : Handleable() {
    abstract fun make(): T
}