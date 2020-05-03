package com.example.android.notetakingapp;

import android.content.Intent;
import android.os.Bundle;

import com.example.android.notetakingapp.constants.Constants;
import com.example.android.notetakingapp.model.NoteEntity;
import com.example.android.notetakingapp.viewmodel.EditorViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.text.InputType;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.android.notetakingapp.constants.Constants.*;

public class EditorActivity extends AppCompatActivity {

    private EditorViewModel mViewmodel;

    @BindView(R.id.note_text)
    EditText mEditText;
    private boolean mNewNote, mEditing;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_check);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ButterKnife.bind(this);

        //Set Editor View so that softkeyboard is hidden with done button
        mEditText.setImeOptions(EditorInfo.IME_ACTION_DONE);
        mEditText.setHorizontallyScrolling(false);
        mEditText.setMaxLines(Integer.MAX_VALUE);

        if (savedInstanceState != null) {
            mEditing = savedInstanceState.getBoolean(EDIT_TEXT_KEY);
        }

        initViewModel();

        Intent intent = getIntent();
        if (intent.getExtras() == null) {
            setTitle(getString(R.string.title_new_note));
            mNewNote = true;
        } else {
            setTitle(getString(R.string.title_edit_note));
            int noteId = intent.getIntExtra(NOTE_ID, 0);
            mViewmodel.loadNoteText(noteId);
        }
    }

    private void initViewModel() {
        mViewmodel = new ViewModelProvider(this).get(EditorViewModel.class);
        mViewmodel.mLiveNote.observe(this, new Observer<NoteEntity>() {

            //If we are in the EditorActivity and the Activity is recreated w/o navigation
            //do not call the onChanged method again. This way the onChanged method is called
            //only once, when we navigate into the EditorActivity
            @Override
            public void onChanged(NoteEntity noteEntity) {
                if (noteEntity != null && !mEditing) {
                    mEditText.setText(noteEntity.getText());
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNewNote) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_editor, menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //If the user presses the check mark, save the edittext note to persistent storage
        if (item.getItemId() == android.R.id.home) {
            saveAndReturn();
            return true;
        } else if (item.getItemId() == R.id.action_delete) {
            deleteNote();
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        saveAndReturn();
    }

    private void saveAndReturn() {
        //Call into the ViewModel and save the edited text in the database
        mViewmodel.saveNote(mEditText.getText().toString());
        finish();
    }

    private void deleteNote() {
        //ViewModel knows which note is being worked on
        mViewmodel.deleteNote();
        finish();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putBoolean(EDIT_TEXT_KEY, true);
        super.onSaveInstanceState(outState);
    }
}
