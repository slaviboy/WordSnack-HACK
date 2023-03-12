package com.slaviboy.worldsnackhack.extensions

fun CharArray.hasSameChars(array: CharArray): Boolean {
    if (this.size > array.size) return false
    for (c in this) {
        for (i in array.indices) {
            if (c == array[i]) {
                array[i] = '-'
                break
            }
            if (i == array.size - 1) return false
        }
    }
    return true
}