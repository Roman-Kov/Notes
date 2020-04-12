package com.rojer_ko.notes.presentation

import android.content.Context
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.rojer_ko.notes.R
import com.rojer_ko.notes.data.model.Color
import com.rojer_ko.notes.data.model.Note
import com.rojer_ko.notes.presentation.extensions.DATE_TIME_FORMAT
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note)

        note = intent.getParcelableExtra(EXTRA_NOTE)

        setSupportActionBar(note_toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        supportActionBar?.title = if (note != null) {
            SimpleDateFormat(DATE_TIME_FORMAT,
                Locale.getDefault()).format(note!!.lastChanged)
        } else {
            getString(R.string.new_note_title)
        }

        initView()
    }
    private fun initView() {
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
}

