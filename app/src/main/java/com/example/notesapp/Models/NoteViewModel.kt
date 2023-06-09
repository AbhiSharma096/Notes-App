package com.example.notesapp.Models

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.notesapp.Database.NoteDatabase
import com.example.notesapp.Database.NotesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteViewModel(application: Application): AndroidViewModel(application) {
      private val repository : NotesRepository
       val allNotes : LiveData<List<Note>>

       init {
               val noteDao = NoteDatabase.getDatabase(application).getNoteDao()
               repository = NotesRepository(noteDao)
               allNotes = repository.allNotes
       }

      fun deleteNotes(note: Note) = viewModelScope.launch(Dispatchers.IO){
            repository.delete(note)
      }

      fun insert(note: Note) = viewModelScope.launch(Dispatchers.IO){
            repository.insert(note)
      }

      fun updateNotes(note: Note) = viewModelScope.launch(Dispatchers.IO){
            repository.update(note)
      }
}