package com.rojer_ko.notes.presentation.ui.note

import android.content.Context
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.rojer_ko.notes.R
import com.rojer_ko.notes.data.model.Color
import com.rojer_ko.notes.data.model.Note
import com.rojer_ko.notes.presentation.extensions.DATE_TIME_FORMAT
import com.rojer_ko.notes.presentation.extensions.SAVE_DELAY
import com.rojer_ko.notes.presentation.ui.common.MyTextWatcher
import kotlinx.android.synthetic.main.activity_note.*
import java.text.SimpleDateFormat
import java.util.*

class NoteActivity : AppCompatActivity() {

    companion object {
        private val EXTRA_NOTE = NoteActivity::class.java.name + "extra.NOTE"

        fun getStartIntent(context: Context, note: Note?): Intent {
            val intent = Intent(context, NoteActivity::class.java)
            intent.putExtra(EXTRA_NOTE, note)
            return intent
        }
    }

    private var note: Note? = null
    private lateinit var viewModel: NoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note)

        note = intent.getParcelableExtra(EXTRA_NOTE)

        initActionBar()
        initView()
    }

    private fun initActionBar(){
        setSupportActionBar(note_toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = if (note != null) {
            SimpleDateFormat(DATE_TIME_FORMAT,
                Locale.getDefault()).format(note!!.lastChanged)
        } else {
            getString(R.string.new_note_title)
        }
    }

    private fun initView() {
        viewModel = ViewModelProviders.of(this).get(NoteViewModel::class.java)
        titleEt.addTextChangedListener(textChangeListener)
        bodyEt.addTextChangedListener(textChangeListener)

        if (note != null) {
            titleEt.setText(note?.title ?: "")
            bodyEt.setText(note?.note ?: "")
            val color = when(note!!.color) {
                Color.WHITE -> this.resources.getColor(R.color.color_white)
                Color.VIOLET -> this.resources.getColor(R.color.color_violet)
                Color.YELLOW -> this.resources.getColor(R.color.color_yello)
                Color.RED -> this.resources.getColor(R.color.color_red)
                Color.PINK -> this.resources.getColor(R.color.color_pink)
                Color.GREEN -> this.resources.getColor(R.color.color_green)
                Color.BLUE -> this.resources.getColor(R.color.color_blue)
            }

            this.supportActionBar?.setBackgroundDrawable(ColorDrawable(color))
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

