package com.kiran.evoketest.android.network

interface APICallback<T> {
    fun onSuccess(t: T)
    fun onError(throwable: Throwable)
}