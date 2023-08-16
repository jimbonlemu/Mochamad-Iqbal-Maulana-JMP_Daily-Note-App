package com.jimbonlemu.aplikasicatatanharian.getHelper

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.jimbonlemu.aplikasicatatanharian.adapter.NoteData


class GetDBhelp(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "notes.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "notes"
        private const val COLUMN_ID = "id"
        private const val COLUMN_TITLE = "title"
        private const val COLUMN_CONTENT = "content"
        private const val COLUMN_CREATED_AT = "created_at"
        private const val COLUMN_UPDATED_AT = "updated_at"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTableQuery = """
            CREATE TABLE $TABLE_NAME (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_TITLE TEXT,
                $COLUMN_CONTENT TEXT,
                $COLUMN_CREATED_AT TEXT,
                $COLUMN_UPDATED_AT TEXT
            )
        """.trimIndent()
        db.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        val dropTableQuery = "DROP TABLE IF EXISTS $TABLE_NAME"
        db.execSQL(dropTableQuery)
        onCreate(db)
    }

    fun deleteNoteData(id: String) {
        val db = writableDatabase
        val selection = "$COLUMN_ID = ?"
        val selectionArgs = arrayOf(id)
        db.delete(TABLE_NAME, selection, selectionArgs)
    }

    fun addNoteData(noteData: NoteData) {
        val db = writableDatabase
        db.use {
            val contentValues = ContentValues().apply {
                put(COLUMN_TITLE, noteData.noteTitle)
                put(COLUMN_CONTENT, noteData.noteContent)
                put(COLUMN_CREATED_AT, noteData.noteCreatedAt)
                put(COLUMN_UPDATED_AT, noteData.noteUpdatedAt)
            }
            db.insert(TABLE_NAME, null, contentValues)
        }
    }

    fun getAllNoteData(): List<NoteData> {
        val noteList = mutableListOf<NoteData>()
        val selectQuery = "SELECT * FROM $TABLE_NAME ORDER BY $COLUMN_CREATED_AT DESC"
        val db = readableDatabase
        db.use {
            val cursor = db.rawQuery(selectQuery, null)
            if (cursor.moveToFirst()) {
                val idIndex = cursor.getColumnIndexOrThrow(COLUMN_ID)
                val titleIndex = cursor.getColumnIndexOrThrow(COLUMN_TITLE)
                val contentIndex = cursor.getColumnIndexOrThrow(COLUMN_CONTENT)
                val createdAtIndex = cursor.getColumnIndexOrThrow(COLUMN_CREATED_AT)
                val updatedAtIndex = cursor.getColumnIndexOrThrow(COLUMN_UPDATED_AT)

                do {
                    val id = cursor.getInt(idIndex)
                    val title = cursor.getString(titleIndex)
                    val content = cursor.getString(contentIndex)
                    val createdAt = cursor.getString(createdAtIndex)
                    val updatedAt = cursor.getString(updatedAtIndex)
                    val noteData = NoteData(id.toString(), title, content, createdAt, updatedAt)
                    noteList.add(noteData)
                } while (cursor.moveToNext())
            }
            cursor.close()
        }
        return noteList
    }

    fun updateNoteData(id: String, updatedNoteData: NoteData) {
        val db = writableDatabase
        val contentValues = ContentValues().apply {
            put(COLUMN_TITLE, updatedNoteData.noteTitle)
            put(COLUMN_CONTENT, updatedNoteData.noteContent)
            put(COLUMN_UPDATED_AT, updatedNoteData.noteUpdatedAt)
        }
        val selection = "$COLUMN_ID = ?"
        val selectionArgs = arrayOf(id)
        db.update(TABLE_NAME, contentValues, selection, selectionArgs)
    }



}

