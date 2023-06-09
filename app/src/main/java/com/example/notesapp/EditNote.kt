package com.example.notesapp

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton

class EditNote : DialogFragment() {
       override fun onCreate (savedInstanceState: Bundle?){
             super.onCreate(savedInstanceState)


       }


      override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
      ): View? {
            var view = inflater.inflate(R.layout.edit_note, container, false)
            var title = view.findViewById(R.id.editTextTitle) as EditText
            var content = view.findViewById<EditText>(R.id.editTextTextDescription) as EditText
            var button = view.findViewById<FloatingActionButton>(R.id.button)


                button.setOnClickListener {

                      var titleText = title.text.toString()
                      var contentText = content.text.toString()
                      var text = titleText

                      dismiss()


                }

            return view
      }
}