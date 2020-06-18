package pl.mrz.umpa.service

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

object DisposableService {

    private val composite = CompositeDisposable()

    fun add(disposable: Disposable) {
        composite.add(disposable)
    }

    fun clear() {
        composite.clear()
    }
}