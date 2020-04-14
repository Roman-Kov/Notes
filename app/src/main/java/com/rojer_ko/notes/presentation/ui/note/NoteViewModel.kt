package com.rojer_ko.notes.presentation.ui.note

import androidx.lifecycle.ViewModel
import com.rojer_ko.notes.data.model.Note
import com.rojer_ko.notes.data.repository.NoteRepository


class NoteViewModel(private val repository: NoteRepository = NoteRepository) : ViewModel() {

    private var pendingNote: Note? = null

    fun saveChanges(note: Note) {
        pendingNote = note
    }

    override fun onCleared() {
        if (pendingNote != null) {
            repository.saveNote(pendingNote!!)
        }
    }
}
