package fr.iutlens.dubois.list

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Element(
    @PrimaryKey @ColumnInfo(name = "description") val description: String,
                @ColumnInfo(name = "num") val num: Int = 1

)