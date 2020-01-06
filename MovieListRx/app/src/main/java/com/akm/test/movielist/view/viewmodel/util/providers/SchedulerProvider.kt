package com.akm.test.movielist.view.viewmodel.util.providers

import io.reactivex.Scheduler

interface SchedulerProvider {

    fun io(): Scheduler

    fun ui(): Scheduler
}