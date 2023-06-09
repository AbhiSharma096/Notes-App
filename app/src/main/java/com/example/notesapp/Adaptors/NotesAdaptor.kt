package com.example.notesapp.Adaptors

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.notesapp.Models.Note
import com.example.notesapp.R

class NotesAdaptor(private val context: Context, val listner : NoteClickListner) : RecyclerView.Adapter<NotesAdaptor.NoteHolder>() {

      private var NotesList = ArrayList<Note>()
      private var fullList = ArrayList<Note>()


      override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteHolder {
                return NoteHolder(
                      LayoutInflater.from(parent.context).inflate(
                            R.layout.list_item,
                            parent,
                            false
                      )
                )
      }

      override fun getItemCount(): Int {
                return NotesList.size
      }

      override fun onBindViewHolder(holder: NoteHolder, position: Int) {
            val currentNote = NotesList[position]
            holder.note_title.text = currentNote.title
            holder.note_description.text = currentNote.description
            holder.note_date.text = currentNote.date
            holder.note_date.isSelected = true

            holder.note_layout.setBackgroundColor(context.resources.getColor(R.color.primarry_dark))
                holder.note_title.setTextColor(context.resources.getColor(R.color.white))
                     holder.note_description.setTextColor(context.resources.getColor(R.color.white))
                             holder.note_date.setTextColor(context.resources.getColor(R.color.white))

            holder.itemView.setOnClickListener {
                  listner.onNoteClick(NotesList[holder.adapterPosition])

            }

            holder.itemView.setOnLongClickListener {
                  listner.onNoteLongClick( NotesList[holder.adapterPosition], holder.note_layout as CardView)
                  true
            }


      }

      inner class NoteHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

            var note_layout = itemView.findViewById<View>(R.id.NoteItem)
            var note_title = itemView.findViewById<TextView>(R.id.Title)
            var note_description = itemView.findViewById<TextView>(R.id.Description)
            var note_date = itemView.findViewById<TextView>(R.id.Date)


      }

       fun setNotes(notes: List<Note>) {
              this.NotesList = notes as ArrayList<Note>
              this.fullList = ArrayList(notes)
              notifyDataSetChanged()
       }

       fun getNoteAt(position: Int): Note {
             return NotesList[position]
       }

      fun updateList(list: List<Note>) {
            fullList.clear()
            fullList.addAll(list)

            NotesList.clear()
            NotesList.addAll(list)
            notifyDataSetChanged()

      }

      fun filter(text: String) {
              NotesList.clear()
              if (text.isEmpty()) {
                     NotesList.addAll(fullList)
              } else {
                     val filteredList = ArrayList<Note>()
                     for (note in fullList) {
                            if (note.title!!.toLowerCase().contains(text.toLowerCase()) || note.description!!.toLowerCase().contains(text.toLowerCase())) {
                                   filteredList.add(note)
                            }
                     }
                     NotesList = filteredList
              }
              notifyDataSetChanged()
       }

       interface NoteClickListner {
              fun onNoteClick(note: Note)
              fun onNoteLongClick(note: Note, cardview: CardView)
       }
}