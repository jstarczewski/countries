package com.clakestudio.pc.countries.util

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class AppSchedulersProvider : SchedulersProvider {

    override fun uiScheduler() : Scheduler = AndroidSchedulers.mainThread()

    override fun ioScheduler() : Scheduler = Schedulers.io()

}