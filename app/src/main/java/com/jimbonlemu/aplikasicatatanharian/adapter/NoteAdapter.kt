package com.jimbonlemu.aplikasicatatanharian.adapter

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textview.MaterialTextView
import com.jimbonlemu.aplikasicatatanharian.R
import com.jimbonlemu.aplikasicatatanharian.getHelper.GetDBhelp


class NoteAdapter(
    private var notes: MutableList<NoteData>,
    private val itemClickListener: OnItemClickListener
) : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    fun updateData(newNotes: List<NoteData>) {
        notes.clear()
        notes.addAll(newNotes)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycle_note_list, parent, false)
        return NoteViewHolder(view)
    }

    interface OnItemClickListener {
        fun onItemClick(note: NoteData)
        fun onItemLongPress(note: NoteData)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = notes[position]
        holder.bind(note)
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    inner class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleNoteList: MaterialTextView = itemView.findViewById(R.id.noteTitleList)
        private val noteCreatedDate: MaterialTextView = itemView.findViewById(R.id.createdAtDate)
        private val noteCreatedTime: MaterialTextView = itemView.findViewById(R.id.createdAtHour)

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val clickedNote = notes[position]
                    itemClickListener.onItemClick(clickedNote)
                }
            }

            itemView.setOnLongClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val clickedNote = notes[position]
                    val alertDialog = AlertDialog.Builder(itemView.context)
                        .setTitle("Delete Note")
                        .setMessage("Are you sure you want to delete this note?")
                        .setPositiveButton("Delete") { _, _ ->

                            deleteNoteAtPosition(position)
                        }
                        .setNegativeButton("Cancel", null)
                        .create()
                    alertDialog.show()
                }
                true
            }

        }

        fun bind(note: NoteData) {
            titleNoteList.text = note.noteTitle
            noteCreatedDate.text = note.noteCreatedAt.substring(0, 10)
            noteCreatedTime.text = note.noteCreatedAt.substring(11, 16)
        }

        fun deleteNoteAtPosition(position: Int) {
            val dbHelper = GetDBhelp(itemView.context)
            dbHelper.deleteNoteData(notes[position].noteId)
            notes.removeAt(position)
            notifyItemRemoved(position)
        }
    }
}
