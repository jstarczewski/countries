package com.clakestudio.pc.countries.util

import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers

object TestSchedulersProvider : SchedulersProvider {

    override fun uiScheduler(): Scheduler = Schedulers.trampoline()

    override fun ioScheduler(): Scheduler = Schedulers.trampoline()
}