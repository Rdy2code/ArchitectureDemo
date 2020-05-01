package com.example.android.notetakingapp;

import android.content.Intent;
import android.os.Bundle;

import com.example.android.notetakingapp.model.NoteEntity;
import com.example.android.notetakingapp.viewmodel.EditorViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EditorActivity extends AppCompatActivity {

    private EditorViewModel mViewmodel;

    @BindView(R.id.note_text)
    EditText mEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ButterKnife.bind(this);

        //Set Editor View so that softkeyboard is hidden with done button
        mEditText.setImeOptions(EditorInfo.IME_ACTION_DONE);
        mEditText.setHorizontallyScrolling(false);
        mEditText.setMaxLines(Integer.MAX_VALUE);

        initViewModel();
    }

    private void initViewModel() {
        mViewmodel = new ViewModelProvider(this).get(EditorViewModel.class);
        mViewmodel.mLiveNote.observe(this, new Observer<NoteEntity>() {
            @Override
            public void onChanged(NoteEntity noteEntity) {
                mEditText.setText(noteEntity.getText());
            }
        });


    }
}
