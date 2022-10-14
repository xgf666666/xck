package com.example.xck.extensions

/**
 * author: Gubr
 * date: 2018/5/23
 * describe:
 */
/**
 * Returns a list containing first elements satisfying the given [predicate].
 */
public inline fun <T> Iterable<T>.takeFirstOneOrNull(predicate: (T) -> Boolean): T? {

    for (item in this) {
        if (!predicate(item))
            continue
        return item
    }
    return null
}