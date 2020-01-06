package com.akm.test.movielist.view.viewmodel.util

import com.akm.test.movielist.view.viewmodel.util.providers.SchedulerProvider
import io.reactivex.Scheduler

class TestSchedulerProvider(private val io: Scheduler, private val ui: Scheduler) :
    SchedulerProvider {

    override fun io(): Scheduler = io

    override fun ui(): Scheduler = ui
}