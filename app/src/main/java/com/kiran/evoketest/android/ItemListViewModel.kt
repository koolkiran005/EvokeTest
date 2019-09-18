package com.kiran.evoketest.android

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kiran.evoketest.android.model.User
import com.kiran.evoketest.android.network.APICallback
import com.kiran.evoketest.android.network.EvokeTestAPIService
import com.kiran.evoketest.android.network.RxAPIHandler
import javax.inject.Inject

class ItemListViewModel : ViewModel() {

    @Inject
    lateinit var apiService: EvokeTestAPIService
    private var usersList: MutableLiveData<ArrayList<User>> = MutableLiveData()

    private fun handleError(throwable: Throwable) {
    }

    fun getUsers(): MutableLiveData<ArrayList<User>> {
        RxAPIHandler<ArrayList<User>>().handle(
            this.apiService.getUsers(),
            getAPICallback()
        )
        return usersList
    }

    private fun getAPICallback(): APICallback<ArrayList<User>> {
        return object : APICallback<ArrayList<User>> {
            override fun onSuccess(t: ArrayList<User>) {
                usersList.value = t
            }

            override fun onError(throwable: Throwable) {
                handleError(throwable)
            }
        }
    }
}