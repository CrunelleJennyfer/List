package fr.iutlens.dubois.list

import androidx.lifecycle.MutableLiveData

object Status {
    var result = MutableLiveData<Result>()

    fun update(r : Result){
            result.value = r
    }
}