package com.example.android.notetakingapp.viewmodel;

import android.app.Application;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.android.notetakingapp.model.NoteEntity;
import com.example.android.notetakingapp.repository.AppRepository;

import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class EditorViewModel extends AndroidViewModel {

    public MutableLiveData<NoteEntity> mLiveNote =
            new MutableLiveData<>();

    private AppRepository mRepository;
    private Executor executor = Executors.newSingleThreadExecutor();

    public EditorViewModel(@NonNull Application application) {
        super(application);
        mRepository = AppRepository.getInstance(application);
    }

    public void loadNoteText(int noteId) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                NoteEntity note = mRepository.getNoteById(noteId);
                mLiveNote.postValue(note);
            }
        });
    }

    public void saveNote(String noteText) {
        //Create a NoteEntity object from the current mLiveNote object
        NoteEntity note = mLiveNote.getValue();
        if (note == null) {
            if (TextUtils.isEmpty(noteText.trim())){
                return;
            }
            note = new NoteEntity(new Date(), noteText.trim());

        } else {
            //Update the text field with the current text
            note.setText(noteText.trim());
        }

        NoteEntity finalNote = note;
        executor.execute(new Runnable() {
            @Override
            public void run() {
                mRepository.saveNoteEntity(finalNote);
            }
        });
    }

    public void deleteNote() {
        //No need to pass note object because the viewmodel already knows the note in the field
        executor.execute(new Runnable() {
            @Override
            public void run() {
                mRepository.deletNote(mLiveNote.getValue());
            }
        });
    }
}
