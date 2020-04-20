package com.rojer_ko.notes.presentation.ui.note

import com.rojer_ko.notes.data.model.Note
import com.rojer_ko.notes.presentation.ui.base.BaseViewState

class NoteViewState(note: Note? = null, error: Throwable? = null) : BaseViewState<Note?>(note, error)