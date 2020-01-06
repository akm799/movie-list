package com.akm.test.movielist.domain.model

/**
 * An indexed movie in our list. This is to keep track of the position in the list.
 * It is faster to update the list if we know the item position, compared to locating
 * the item in the list using the item's ID.
 */
data class MovieRow(val movie: Movie, val index: Int)