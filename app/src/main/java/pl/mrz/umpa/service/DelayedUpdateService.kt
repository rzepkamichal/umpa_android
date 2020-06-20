package pl.mrz.umpa.service

import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.subjects.PublishSubject
import pl.mrz.umpa.model.StationConfig
import java.util.*


object DelayedUpdateService {

    private val resultPublisher: PublishSubject<Any> = PublishSubject.create()

    private var wasRecentlyScheduled = false


    private var timer = Timer()

    fun notifyChange(stationConfig: StationConfig) {

        if (wasRecentlyScheduled) {
            timer.cancel()
            timer = Timer()
        }


        timer.schedule(object : TimerTask() {
            override fun run() {
                ApiService.updateConfiguration(stationConfig)
                    .doFinally { wasRecentlyScheduled = false }
                    .subscribe(
                        { resultPublisher.onNext(it) },
                        { resultPublisher.onError(it) }
                    )
                    .also { DisposableService.add(it) }
            }
        }, 500)

        wasRecentlyScheduled = true

    }

    fun getResults(): Flowable<Any> {
        return resultPublisher.toFlowable(BackpressureStrategy.LATEST)
    }
}