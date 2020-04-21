package com.rojer_ko.notes.presentation.ui.note

import NoteViewModel
import android.content.Context
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.view.MenuItem
import androidx.lifecycle.ViewModelProviders
import com.rojer_ko.notes.R
import com.rojer_ko.notes.data.model.Note
import com.rojer_ko.notes.presentation.extensions.SAVE_DELAY
import com.rojer_ko.notes.presentation.extensions.format
import com.rojer_ko.notes.presentation.extensions.getColorInt
import com.rojer_ko.notes.presentation.ui.base.BaseActivity
import com.rojer_ko.notes.presentation.ui.common.MyTextWatcher
import kotlinx.android.synthetic.main.activity_note.*
import java.util.*

class NoteActivity : BaseActivity<Note?, NoteViewState>() {

    companion object {
        private val EXTRA_NOTE = NoteActivity::class.java.name + "extra.NOTE"
        fun getStartIntent(context: Context, noteId: String?): Intent {
            val intent = Intent(context, NoteActivity::class.java)
            intent.putExtra(EXTRA_NOTE, noteId)
            return intent
        }
    }

    override val viewModel: NoteViewModel by lazy { ViewModelProviders.of(this).get(NoteViewModel::class.java) }
    override val layoutRes: Int = R.layout.activity_note
    private var note: Note? = null
    private var noteId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        noteId = intent.getStringExtra(EXTRA_NOTE)
        noteId?.let {
            viewModel.loadNote(it)
        }
        initActionBar()
        titleEt.addTextChangedListener(textChangeListener)
        bodyEt.addTextChangedListener(textChangeListener)
    }

    override fun renderData(data: Note?) {
        this.note = data
        initView()
}

    private fun initActionBar(){
        setSupportActionBar(note_toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        if (noteId == null ) supportActionBar?.title = getString(R.string.new_note_title)
    }

    private fun initView() {
        note?.run {
            supportActionBar?.title = lastChanged.format()
            titleEt.setText(this.title)
            bodyEt.setText(this.note)
            supportActionBar?.setBackgroundDrawable(ColorDrawable(color.getColorInt(this@NoteActivity)))
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when(item.itemId) {
        android.R.id.home -> {
            onBackPressed()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    private val textChangeListener = object : MyTextWatcher {
        override fun afterTextChanged(s: Editable?) {
            triggerSaveNote()
        }
    }

    private fun createNewNote(): Note = Note(
            title = titleEt.text.toString(),
            note = bodyEt.text.toString()
        )

    private fun triggerSaveNote() {
        if (titleEt.text!!.length < 3) return
        Handler().postDelayed({
            note = note?.copy(title = titleEt.text.toString(),
                note = bodyEt.text.toString(),
                lastChanged = Date())
                ?: createNewNote()
            if (note != null) viewModel.saveChanges(note!!)
        }, SAVE_DELAY)
    }
}

