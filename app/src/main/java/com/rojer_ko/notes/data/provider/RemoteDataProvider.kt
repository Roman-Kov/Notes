package com.rojer_ko.notes.data.provider

import androidx.lifecycle.LiveData
import com.rojer_ko.notes.data.model.Note
import com.rojer_ko.notes.data.model.NoteResult

interface RemoteDataProvider {

    fun subscribeToAllNotes(): LiveData<NoteResult>
    fun getNoteById(id: String): LiveData<NoteResult>
    fun saveNote(note: Note) : LiveData<NoteResult>
}