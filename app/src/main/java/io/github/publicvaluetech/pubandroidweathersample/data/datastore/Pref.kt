package io.github.publicvaluetech.pubandroidweathersample.data.datastore

sealed class Pref<T>(val key: String)

class IntPref(key: String) : Pref<Int>(key)
class StringPref(key: String) : Pref<String>(key)
class BooleanPref(key: String) : Pref<Boolean>(key)
class FloatPref(key: String) : Pref<Float>(key)
class ObjectPref<T>(key: String, val clazz: Class<T>) : Pref<T>(key)
