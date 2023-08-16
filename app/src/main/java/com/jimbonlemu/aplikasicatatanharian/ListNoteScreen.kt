package com.jimbonlemu.aplikasicatatanharian

import android.app.AlertDialog
import android.content.DialogInterface
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

        databaseHelper = GetDBhelp(this)

        val notes = databaseHelper.getAllNoteData().toMutableList()
        noteAdapter = NoteAdapter(notes, this)
        recyclerViewNotes.adapter = noteAdapter
    }

    override fun onItemLongPress(note: NoteData) {
        AlertDialog.Builder(this)
            .setTitle("Peringatan")
            .setMessage("Apakah anda yakin ingin menghapus catatan ?")
            .setPositiveButton("Hapus") { dialog, _ ->
                deleteNoteFromDatabase(note)
                dialog.dismiss()
            }
            .setNegativeButton("Batal") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
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
        Get.to(
            this,
            NoteScreen::class.java,
            Pair("id", note.noteId),
            Pair("title", note.noteTitle),
            Pair("prior", note.notePrior),
            Pair("content", note.noteContent),
            Pair("edit", "true")
        )
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_add_note -> {
                Get.to(this, NoteScreen::class.java, Pair("edit","false"))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        databaseHelper.close()
    }

    private fun deleteNoteFromDatabase(note: NoteData) {
        databaseHelper.deleteNoteData(note.noteId)
        val updatedNotes = databaseHelper.getAllNoteData().toMutableList()
        noteAdapter.updateData(updatedNotes)
        noteAdapter.notifyDataSetChanged()
    }


    private fun addNewNoteToDatabase(noteData: NoteData) {
        databaseHelper.addNoteData(noteData)

        val updatedNotes = databaseHelper.getAllNoteData()

        noteAdapter.updateData(updatedNotes)
        noteAdapter.notifyDataSetChanged()
    }

    private fun updateNoteList() {
        val updatedNotes = databaseHelper.getAllNoteData()
        println(updatedNotes)
        noteAdapter.updateData(updatedNotes)
        noteAdapter.notifyDataSetChanged()
    }

    override fun onBackPressed() {
        showExitConfirmationDialog()
    }

    private fun showExitConfirmationDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Peringatan")
            .setMessage("Apakah anda yakin ingin keluar?")
            .setPositiveButton("Keluar") { _: DialogInterface, _: Int ->
                finish()
            }
            .setNegativeButton("Batal") { dialog: DialogInterface, _: Int ->
                dialog.dismiss()
            }
        val dialog = builder.create()
        dialog.show()
    }


}
