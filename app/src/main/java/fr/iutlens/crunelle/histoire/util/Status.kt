package fr.iutlens.crunelle.histoire.util

import androidx.lifecycle.MutableLiveData

object Status {
    var result = MutableLiveData<Result>()

    fun update(r : Result){
            result.value = r
    }
}