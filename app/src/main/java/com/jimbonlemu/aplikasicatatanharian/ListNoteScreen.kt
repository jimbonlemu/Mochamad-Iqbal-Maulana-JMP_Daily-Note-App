package com.jimbonlemu.aplikasicatatanharian

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jimbonlemu.aplikasicatatanharian.adapter.NoteAdapter
import com.jimbonlemu.aplikasicatatanharian.adapter.NoteData
import com.jimbonlemu.aplikasicatatanharian.getHelper.Get

class ListNoteScreen : AppCompatActivity(), NoteAdapter.OnItemClickListener {

    private lateinit var recyclerViewNotes: RecyclerView
    private lateinit var noteAdapter: NoteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.history_note_screen)

        recyclerViewNotes = findViewById(R.id.noteListRecyclerView)
        recyclerViewNotes.layoutManager = LinearLayoutManager(this)

        val notes = listOf(
            NoteData("1", "Catatan 1", "Isi Catatan 1", "2022-01-01", "2022-01-01"),
            NoteData("2", "Catatan 2", "Isi Catatan 2", "2022-01-02", "2022-01-02"),
            NoteData("3", "Catatan 3", "Isi Catatan 3", "2022-01-03", "2022-01-03")
        )

        noteAdapter = NoteAdapter(notes, this)
        recyclerViewNotes.adapter = noteAdapter
    }

    override fun onItemClick(note: NoteData) {
        // Implementasikan perubahan yang diinginkan saat item diklik
        // Contoh: Navigasi ke NoteScreen dengan membawa data catatan yang diklik
        Get.to(this, NoteScreen::class.java)
    }
}
