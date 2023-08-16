package com.jimbonlemu.aplikasicatatanharian

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import com.google.android.material.button.MaterialButton
import com.jimbonlemu.aplikasicatatanharian.adapter.NoteData
import com.jimbonlemu.aplikasicatatanharian.getHelper.Get
import com.jimbonlemu.aplikasicatatanharian.getHelper.GetDBhelp
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class NoteScreen : AppCompatActivity() {

    private lateinit var titleHead: String
    private lateinit var databaseHelper: GetDBhelp

    private lateinit var noteTitle: EditText
    private lateinit var noteContent: EditText
    private lateinit var btnSaveNote: MaterialButton

    private var isEdit: String = "false"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.note_screen)

        findViewById<MaterialButton>(R.id.btnAppInfo).setOnClickListener {
            Get.to(this, AppInfo::class.java)
        }

        noteTitle = findViewById(R.id.titleNote)
        noteContent = findViewById(R.id.contentNote)
        btnSaveNote = findViewById(R.id.btnSaveNote)
        isEdit = "${Get.arguments("edit")}"

        databaseHelper = GetDBhelp(this)



        if (isEdit == "true") {
            titleHead = "Ubah Catatan"
            noteTitle.setText(Get.arguments("title"))
            noteContent.setText(Get.arguments("content"))

            btnSaveNote.text = "Simpan Perubahan"
            btnSaveNote.setOnClickListener {
                updateNote()
            }

        } else {
            titleHead = "Tambah Catatan Baru"
            btnSaveNote.text = "Simpan catatan"
            noteTitle.setText("")
            noteContent.setText("")
            btnSaveNote.setOnClickListener {
                saveNote()
            }

        }

        supportActionBar?.apply {
            title = titleHead
            setDisplayHomeAsUpEnabled(true)
        }


    }


    override fun onSupportNavigateUp(): Boolean {
        if (isEdit == "true") {
            updateNote()
            resetNoteData()
        }
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

    private fun updateNote() {
        val noteId = "${Get.arguments("id")}"

        val updatedNoteData = NoteData(
            noteId,
            noteTitle.text.toString(),
            noteContent.text.toString(),
            getCurrentDateTime(),
            getCurrentDateTime(),
        )
        databaseHelper.updateNoteData(noteId, updatedNoteData)
        Get.back(this)
    }


    private fun getCurrentDateTime(): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val date = Date()
        return dateFormat.format(date)
    }


    private fun resetNoteData() {
        titleHead = ""
        noteTitle.text.clear()
        noteContent.text.clear()
        isEdit = "false"
    }
}
