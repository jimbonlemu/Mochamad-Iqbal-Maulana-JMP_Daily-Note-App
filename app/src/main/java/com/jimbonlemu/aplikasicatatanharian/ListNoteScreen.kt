// ListNoteScreen.kt

package com.jimbonlemu.aplikasicatatanharian

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jimbonlemu.aplikasicatatanharian.adapter.NoteAdapter
import com.jimbonlemu.aplikasicatatanharian.adapter.NoteData
import com.jimbonlemu.aplikasicatatanharian.getHelper.Get
import com.jimbonlemu.aplikasicatatanharian.getHelper.GetDBhelp

class ListNoteScreen : AppCompatActivity(), NoteAdapter.OnItemClickListener {

    private lateinit var recyclerViewNotes: RecyclerView
    private lateinit var noteAdapter: NoteAdapter
    private lateinit var databaseHelper: GetDBhelp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.history_note_screen)

        recyclerViewNotes = findViewById(R.id.noteListRecyclerView)
        recyclerViewNotes.layoutManager = LinearLayoutManager(this)

        // Inisialisasi objek DatabaseHelper
        databaseHelper = GetDBhelp(this)

        // Mendapatkan semua catatan dari database
        val notes = databaseHelper.getAllNoteData()
        noteAdapter = NoteAdapter(notes, this)
        recyclerViewNotes.adapter = noteAdapter
    }

    override fun onResume() {
        super.onResume()
        updateNoteList()
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.list_note_menu, menu)
        return true
    }

    override fun onItemClick(note: NoteData) {
        // Implementasikan perubahan yang diinginkan saat item diklik
        // Contoh: Navigasi ke NoteScreen dengan membawa data catatan yang diklik
        Get.to(this, NoteScreen::class.java)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_add_note -> {
                Get.to(this, NoteScreen::class.java)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // Jangan lupa untuk menutup DatabaseHelper saat Activity dihancurkan
        databaseHelper.close()
    }

    private fun addNewNoteToDatabase(noteData: NoteData) {
        // Tambahkan catatan baru ke database menggunakan databaseHelper
        databaseHelper.addNoteData(noteData)

        // Dapatkan semua catatan dari database setelah penambahan data
        val updatedNotes = databaseHelper.getAllNoteData()

        // Perbarui data pada adapter dan beri tahu RecyclerView untuk diperbarui
        noteAdapter.updateData(updatedNotes)
        noteAdapter.notifyDataSetChanged()
    }

    private fun updateNoteList() {
        val updatedNotes = databaseHelper.getAllNoteData()
        println(updatedNotes)
        noteAdapter.updateData(updatedNotes)
        noteAdapter.notifyDataSetChanged()
    }


}
