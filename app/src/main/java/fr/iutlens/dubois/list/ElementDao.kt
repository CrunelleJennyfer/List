package fr.iutlens.dubois.list

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ElementDao {
    @Query("SELECT * FROM element ORDER BY description")
    fun getAll(): Flow<List<Element>>

    @Query("SELECT * FROM element WHERE description LIKE :description LIMIT 1")
    suspend fun findByDescription(description: String): Element

    @Insert
    suspend fun insertAll(vararg elements: Element)

    @Delete
    suspend fun delete(element: Element)
}