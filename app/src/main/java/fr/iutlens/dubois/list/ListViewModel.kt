package fr.iutlens.dubois.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope

class ListViewModel() : ViewModel() {

    fun insert(database: AppDatabase, element: Element) =  viewModelScope.launch {
        database.elementDao().insertAll(element)
    }

    fun delete(database: AppDatabase, element: Element) = viewModelScope.launch {
        database.elementDao().delete(element)
    }

    fun allElements(database: AppDatabase): LiveData<List<Element>> {
        return database.elementDao().getAll().asLiveData()
    }
}