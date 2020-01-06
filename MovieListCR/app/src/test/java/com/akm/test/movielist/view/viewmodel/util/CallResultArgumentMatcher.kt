package com.akm.test.movielist.view.viewmodel.util

import com.akm.test.movielist.helper.AbstractArgumentMatcher

abstract class CallResultArgumentMatcher<T> : AbstractArgumentMatcher<CallResult<T>>() {

    final override fun matches(argument: CallResult<T>?): Boolean {
        if (argument == null) {
            return false
        }

        if (argument.hasError()) {
            return false
        }

        return match(argument.result())
    }

    abstract fun match(result: T): Boolean
}