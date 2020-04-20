import androidx.lifecycle.Observer
import com.rojer_ko.notes.data.model.Note
import com.rojer_ko.notes.data.model.NoteResult
import com.rojer_ko.notes.data.model.NoteResult.*
import com.rojer_ko.notes.data.repository.Repository
import com.rojer_ko.notes.presentation.ui.base.BaseViewModel
import com.rojer_ko.notes.presentation.ui.main.MainViewState

class MainViewModel(val repository: Repository = Repository) : BaseViewModel<List<Note>?, MainViewState>() {

    private val notesObserver = object : Observer<NoteResult> {//Стандартный обсервер LiveData
    override fun onChanged(t: NoteResult?) {
        if (t == null) return

        when(t) {
            is Success<*> -> {
// Может понадобиться вручную импортировать класс data.model.NoteResult.Success
                viewStateLiveData.value = MainViewState(notes = t.data as? List<Note>)
            }
            is Error -> {
// Может понадобиться вручную импортировать класс data.model.NoteResult.Error
                viewStateLiveData.value = MainViewState(error = t.error)
            }
        }
    }
    }

    private val repositoryNotes = repository.getNotes()

    init {
        viewStateLiveData.value = MainViewState()
        repositoryNotes.observeForever(notesObserver)
    }

    override fun onCleared() {
        repositoryNotes.removeObserver(notesObserver)
    }
}
