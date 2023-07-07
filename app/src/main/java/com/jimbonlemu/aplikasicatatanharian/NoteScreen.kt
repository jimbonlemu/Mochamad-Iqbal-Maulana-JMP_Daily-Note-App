package com.jimbonlemu.aplikasicatatanharian

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.jimbonlemu.aplikasicatatanharian.getHelper.Get

class NoteScreen : AppCompatActivity() {

    private lateinit var titleHead: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.note_screen)

        titleHead = "Tambah Catatan Baru"

        supportActionBar?.apply {
            title = titleHead
            setDisplayHomeAsUpEnabled(true)
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        Get.back(this)
        return true
    }

    private fun saveNoteWhileBack() {

    }
}