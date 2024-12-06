package com.example.lotrwiki.utils

//sealed class MyFilter<T> {
//    data object All : MyFilter<Any>()
//    data class ByTag(val tag: String) : MyFilter<Any>()
//
//    fun filter(t: T): Boolean {
//        return when (this) {
//            All -> true
//            is ByTag -> (t as? Taggable)?.tags?.contains(tag) == true
//        }
//    }
//}