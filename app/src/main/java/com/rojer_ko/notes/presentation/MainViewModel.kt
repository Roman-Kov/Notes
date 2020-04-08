package com.rojer_ko.notes.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rojer_ko.notes.data.repository.NoteRepository

class MainViewModel: ViewModel() {

    private val viewStateLiveData: MutableLiveData<MainViewState> = MutableLiveData()

    init {
        viewStateLiveData.value = MainViewState(NoteRepository.notes)
    }

    fun viewState(): LiveData<MainViewState> = viewStateLiveData
}