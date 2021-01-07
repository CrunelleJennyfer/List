package fr.iutlens.dubois.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope

class ListViewModel() : ViewModel() {

    fun insert(element: Element) =  viewModelScope.launch {
        AppDatabase.getDatabase()?.elementDao()?.insertAll(element)
    }

    fun delete(element: Element) = viewModelScope.launch {
        AppDatabase.getDatabase()?.elementDao()?.delete(element)
    }

    fun allElements(): LiveData<List<Element>>? {
        return AppDatabase.getDatabase()?.elementDao()?.getAll()?.asLiveData()
    }
}