package com.rojer_ko.notes.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.rojer_ko.notes.data.model.Color
import com.rojer_ko.notes.data.model.Note
import com.rojer_ko.notes.data.provider.FireStoreProvider
import com.rojer_ko.notes.data.provider.RemoteDataProvider

object Repository {

    private val remoteProvider: RemoteDataProvider = FireStoreProvider()

    fun getNotes() = remoteProvider.subscribeToAllNotes()
    fun saveNote(note: Note) = remoteProvider.saveNote(note)
    fun getNoteById(id: String) = remoteProvider.getNoteById(id)
    fun getCurrentUser() = remoteProvider.getCurrentUser()

}