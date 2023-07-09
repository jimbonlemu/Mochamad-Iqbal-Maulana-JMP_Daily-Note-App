package com.jimbonlemu.aplikasicatatanharian

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import com.google.android.material.button.MaterialButton
import com.jimbonlemu.aplikasicatatanharian.adapter.NoteData
import com.jimbonlemu.aplikasicatatanharian.getHelper.Get
import com.jimbonlemu.aplikasicatatanharian.getHelper.GetDBhelp
import java.text.SimpleDateFormat
import java.util.*

class NoteScreen : AppCompatActivity() {

    private lateinit var titleHead: String
    private lateinit var databaseHelper: GetDBhelp

    private lateinit var noteTitle : EditText
    private lateinit var noteContent:EditText
    private lateinit var btnSaveNote:MaterialButton


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.note_screen)

        titleHead = "Tambah Catatan Baru"

        noteTitle = findViewById(R.id.titleNote)
        noteContent = findViewById(R.id.contentNote)
        btnSaveNote = findViewById(R.id.btnSaveNote)


        supportActionBar?.apply {
            title = titleHead
            setDisplayHomeAsUpEnabled(true)
        }
        databaseHelper = GetDBhelp(this)

        btnSaveNote.setOnClickListener {
            saveNote()
        }

    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun saveNote() {
        val title = noteTitle.text.toString()
        val content = noteContent.text.toString()
        val createdAt = getCurrentDateTime()

        val noteData = NoteData("", title, content, createdAt, createdAt)
        databaseHelper.addNoteData(noteData)

        Get.back(this)
    }

    private fun onSubmitNote(){

    }

    private fun getCurrentDateTime(): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val date = Date()
        return dateFormat.format(date)
    }
}
