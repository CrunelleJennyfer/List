package fr.iutlens.dubois.list.util

import androidx.lifecycle.MutableLiveData

object Status {
    var result = MutableLiveData<Result>()

    fun update(r : Result){
            result.value = r
    }
}