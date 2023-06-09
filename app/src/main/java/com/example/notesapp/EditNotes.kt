package com.example.notesapp

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import com.example.notesapp.Models.Note
import com.example.notesapp.databinding.ActivityEditNotesBinding
import java.text.SimpleDateFormat

class EditNotes : AppCompatActivity() {

      private lateinit var binding : ActivityEditNotesBinding
      private lateinit var note: Note
      private lateinit var old_note: Note
      var isUpdate = false
      private lateinit var saveButton: ImageButton
      private lateinit var backbtn: ImageButton
      private lateinit var tittle: EditText
      private lateinit var description: EditText



      @SuppressLint("MissingInflatedId")
      override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            binding = ActivityEditNotesBinding.inflate(layoutInflater)
            setContentView(R.layout.activity_edit_notes)
            backbtn = findViewById(R.id.backButton)
            tittle = findViewById(R.id.editTextTitle)
            description = findViewById(R.id.editTextDescription)
            saveButton = findViewById<ImageButton>(R.id.saveButton)


            try {
                  note = intent.getSerializableExtra("Current_note") as Note
                  binding.editTextTitle.setText(note.title)
                  binding.editTextDescription.setText(note.description)
                  isUpdate = true
            } catch (log : Exception) {
                  Toast.makeText(this, "Please enter", Toast.LENGTH_SHORT).show()
            }

            backbtn.setOnClickListener {
                  onBackPressed()
            }


            saveButton.setOnClickListener {
                  var titleStr  = tittle.text.toString()
                  var  descriptionStr = description.text.toString()
                  val date = System.currentTimeMillis()
                  val priority = 1

                  if (titleStr.isNotEmpty() || descriptionStr.isNotEmpty()) {

                        val formatter = SimpleDateFormat("EEE, d MMM yyyy HH:mm aaa")
                        val dateString = formatter.format(date)
                        if (isUpdate){
                              note = Note(
                                    old_note.id,titleStr,descriptionStr,dateString,priority
                              )
                        }
                            else {
                                   note = Note(
                                         1,titleStr,descriptionStr,dateString,priority
                                   )
                            }
                        val intent = Intent()
                        intent.putExtra("note", note)
                        setResult(RESULT_OK, intent)
                        finish()


                  }
                  else {
                        Toast.makeText(this, "Please enter title and description", Toast.LENGTH_SHORT).show()
                        return@setOnClickListener
                  }

            }



      }


}