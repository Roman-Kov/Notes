import androidx.lifecycle.Observer
import com.rojer_ko.notes.data.model.Note
import com.rojer_ko.notes.data.model.NoteResult
import com.rojer_ko.notes.data.model.NoteResult.*
import com.rojer_ko.notes.data.repository.Repository
import com.rojer_ko.notes.presentation.ui.base.BaseViewModel
import com.rojer_ko.notes.presentation.ui.note.NoteViewState

class NoteViewModel(val repository: Repository = Repository) : BaseViewModel<Note?, NoteViewState>() {

    private var pendingNote: Note? = null

    fun saveChanges(note: Note) {
        pendingNote = note
    }

    override fun onCleared() {
        if (pendingNote != null) {
            repository.saveNote(pendingNote!!)
        }
    }

    fun loadNote(noteId: String) {
        repository.getNoteById(noteId).observeForever(object : Observer<NoteResult> {
            override fun onChanged(t: NoteResult?) {
                if (t == null) return

                when (t) {
                    is Success<*> ->
                        viewStateLiveData.value = NoteViewState(note = t.data as? Note)
                    is Error ->
                        viewStateLiveData.value = NoteViewState(error = t.error)
                }
            }
        })
    }
}
