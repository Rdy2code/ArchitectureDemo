package com.example.android.notetakingapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.android.notetakingapp.model.NoteEntity;
import com.example.android.notetakingapp.repository.AppRepository;
import com.example.android.notetakingapp.utilities.SampleData;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    public List<NoteEntity> mNotes;
    private AppRepository mRepository;

    //ViewModel constructor initializes the Repository class and gets the data from this class
    public MainViewModel(@NonNull Application application) {
        super(application);

        //ViewModel gets data from the Repository
        mRepository = AppRepository.getInstance();
        mNotes = mRepository.mNotes;
    }
}
