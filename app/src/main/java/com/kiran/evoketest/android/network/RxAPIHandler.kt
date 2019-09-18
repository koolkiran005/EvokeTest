package com.kiran.evoketest.android.network

import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class RxAPIHandler<T> {

    fun handle(observable: Flowable<T>, apiCallBack: APICallback<T>): Disposable {
        return observable.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { success -> apiCallBack.onSuccess(success) },
                { throwable -> apiCallBack.onError(throwable) })
    }
}