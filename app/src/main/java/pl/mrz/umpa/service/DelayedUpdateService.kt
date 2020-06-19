package pl.mrz.umpa.service

import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.subjects.PublishSubject
import pl.mrz.umpa.model.StationConfig
import java.util.*


object DelayedUpdateService {

    private val resultPublisher: PublishSubject<StationConfig> = PublishSubject.create()
    private var stationConfig: StationConfig? = null

    private var wasRecentlyScheduled = false



    private val timer = Timer()

    fun notifyChange(stationConfig: StationConfig) {

        if(wasRecentlyScheduled)
            timer.cancel()

        timer.schedule(object : TimerTask() {
            override fun run() {
                DelayedUpdateService.stationConfig?.let {
                    ApiService.updateConfiguration(it)
                        .doFinally{ wasRecentlyScheduled = false}
                        .subscribe(
                            {
                                ApiService.getConfiguration().subscribe(
                                    { resultPublisher.onNext(it) },
                                    { resultPublisher.onError(it) }
                                )
                            },
                            { resultPublisher.onError(it) }
                        )
                        .also { DisposableService.add(it) }
                }
            }

        }, 5000)

        wasRecentlyScheduled = true

    }

    fun getResults(): Flowable<StationConfig> {
        return resultPublisher.toFlowable(BackpressureStrategy.LATEST)
    }
}