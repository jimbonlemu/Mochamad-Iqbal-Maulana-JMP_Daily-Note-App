// NoteAdapter.kt

package com.jimbonlemu.aplikasicatatanharian.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textview.MaterialTextView
import com.jimbonlemu.aplikasicatatanharian.R

class NoteAdapter(private var notes: List<NoteData>, private val itemClickListener: OnItemClickListener) :
    RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    fun updateData(newNotes: List<NoteData>) {
        notes = newNotes
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycle_note_list, parent, false)
        return NoteViewHolder(view)
    }

    interface OnItemClickListener {
        fun onItemClick(note: NoteData)
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
        }

        fun bind(note: NoteData) {
            titleNoteList.text = note.noteTitle
            noteCreatedDate.text = note.noteCreatedAt
            noteCreatedTime.text = note.noteUpdatedAt
        }
    }
}
