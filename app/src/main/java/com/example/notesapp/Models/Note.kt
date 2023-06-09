package com.example.notesapp.Models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes_table")
data class Note(

      @PrimaryKey(autoGenerate = true) val id: Int? ,
      @ColumnInfo(name = "title") val title: String?,
      @ColumnInfo(name = "description") val description: String?,
      @ColumnInfo(name ="date") val date: String?,
      @ColumnInfo(name = "priority") val priority: Int?

) : java.io.Serializable
