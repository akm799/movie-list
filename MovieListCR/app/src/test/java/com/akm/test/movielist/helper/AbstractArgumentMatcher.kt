package com.akm.test.movielist.helper

import org.mockito.ArgumentMatcher
import org.mockito.Mockito

/**
 * Cannot use Mockito argument matchers (e.g. Mockito.any(), Mockito.eq(value) or Mockito.argThat(matcher))
 * with Kotlin because they can return null at some points during the testing process. Whereas Java
 * is OK with such nulls, Kotlin complains if the associated reference is defined as non-null.
 */
abstract class AbstractArgumentMatcher<T> : ArgumentMatcher<T> {

    /**
     * Returns a non-null dummy instance used to keep Kotlin happy when method arguments (used to
     * define mock behaviour) are defined as non-null. It does not matter what value is returned
     * by this method since such value will never be used in any tests. It will be used only to keep
     * the Kotlin runtime happy.
     */
    abstract fun dummyInstance(): T

    /**
     * Returns a non-null reference that can be safely used in defining mock behaviour.
     */
    fun mockArgument(): T = (Mockito.argThat(this) ?: dummyInstance())
}