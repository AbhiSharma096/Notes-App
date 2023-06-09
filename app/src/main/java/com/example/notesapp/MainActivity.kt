package com.example.notesapp

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.PopupMenu
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.cardview.widget.CardView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.notesapp.Adaptors.NotesAdaptor
import com.example.notesapp.Database.NoteDatabase
import com.example.notesapp.Models.Note
import com.example.notesapp.Models.NoteViewModel
import com.example.notesapp.databinding.ActivityMainBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity(), NotesAdaptor.NoteClickListner, PopupMenu.OnMenuItemClickListener {

      private lateinit var binding: ActivityMainBinding
      private lateinit var database: NoteDatabase
      private lateinit var button: FloatingActionButton
      private lateinit var searchView: SearchView
      private lateinit var recyclerview : RecyclerView
      lateinit var viewModel: NoteViewModel
      lateinit var adapter: NotesAdaptor
      lateinit var selectNote: Note
      private val updateNote =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                  if (result.resultCode == Activity.RESULT_OK) {
                        Toast.makeText(this, "Note Updated", Toast.LENGTH_SHORT).show()
                        val note = result.data?.getSerializableExtra("note") as Note
                        if (note.id == -1) {
                              viewModel.insert(note)
                        } else {
                              viewModel.updateNotes(note)
                        }
                  }
            }

      override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            binding = ActivityMainBinding.inflate(layoutInflater)
            setContentView(R.layout.activity_main)
            recyclerview = findViewById(R.id.recyclerView)
            searchView = findViewById(R.id.searchView)
            button = findViewById(R.id.floatingActionButton)




            initUI()

            viewModel = ViewModelProvider(
                  this,
                  ViewModelProvider.AndroidViewModelFactory.getInstance(application)
            ).get(NoteViewModel::class.java)

            viewModel.allNotes.observe(this,) { list ->
                  list?.let {
                        adapter.updateList(it)
                  }
            }

            database = NoteDatabase.getDatabase(this)


      }

      private fun initUI() {
            recyclerview.setHasFixedSize(true)
            recyclerview.layoutManager =
                  StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
            adapter = NotesAdaptor(this, this)
            recyclerview.adapter = adapter

            val getContent =
                  registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                        if (result.resultCode == Activity.RESULT_OK) {
                              val note = result.data?.getSerializableExtra("note") as Note
                              if (note.id == -1) {
                                    viewModel.insert(note)
                              }

                        }

                  }

            button.setOnClickListener {
                  val intent = Intent(this, EditNotes::class.java)
                  getContent.launch(intent)

            }
//            val database: NoteDatabase = NoteDatabase.getDatabase(this)
//            val noteDao: NoteDao = database.getNoteDao()
//
//            lifecycleScope.launch {
//                  val noteCount = noteDao.getNoteCount()
//                  if (noteCount > 0) {
//                        Toast.makeText(this@MainActivity, "Database is not empty $noteCount", Toast.LENGTH_SHORT).show()
//                  } else {
//                            Toast.makeText(this@MainActivity, "Database is empty", Toast.LENGTH_SHORT).show()
//                  }
//            }


            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                  override fun onQueryTextSubmit(query: String?): Boolean {
                        return false
                  }

                  override fun onQueryTextChange(newText: String?): Boolean {
                        if (newText != null)
                              adapter.filter(newText)

                        return true
                  }

            })


      }

      override fun onNoteClick(note: Note) {
            val intent = Intent(this, EditNotes::class.java)
            intent.putExtra("Current_note", note)
            updateNote.launch(intent)
      }

      override fun onNoteLongClick(note: Note, cardview: CardView) {
            selectNote = note
            popupDisplay(cardview)
      }

      private fun popupDisplay(cardview: CardView) {
            val popup = PopupMenu(this, cardview)
            popup.setOnMenuItemClickListener(this)
            popup.inflate(R.menu.popup_menu)
            popup.show()
      }

      override fun onMenuItemClick(item: MenuItem?): Boolean {
            if (item != null) {
                  when (item.itemId) {
                        R.id.delete -> {
                              viewModel.deleteNotes(selectNote)
                                   return true
                        }
                  }
            }
            return false
      }

}