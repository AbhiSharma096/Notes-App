package com.example.notesapp.Database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.notesapp.Models.Note

@Dao
interface NoteDao {

      @Insert(onConflict = OnConflictStrategy.REPLACE)
      suspend fun insert(note : Note)

      @Query("UPDATE notes_table SET title = :title, description = :description, priority = :priority WHERE id = :id")
      suspend fun update(id: Int?, title: String?, description: String?,date : String?, priority: Int?)

      @Delete
      suspend fun delete(note : Note)

      @Query("DELETE FROM notes_table")
      suspend fun deleteAllNotes()

      @Query("SELECT COUNT(*) FROM notes_table")
      suspend fun getNoteCount(): Int

      @Query("SELECT * FROM notes_table ORDER BY Priority DESC")
      fun getAllNotes() : LiveData<List<Note>>

}