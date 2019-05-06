package com.clakestudio.pc.countries.util

import io.reactivex.Scheduler

interface SchedulersProvider {

    fun ioScheduler() : Scheduler

    fun uiScheduler() : Scheduler

}