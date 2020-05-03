package com.example.android.notetakingapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.android.notetakingapp.model.NoteEntity;
import com.example.android.notetakingapp.repository.AppRepository;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MainViewModel extends AndroidViewModel {

    public LiveData<List<NoteEntity>> mNotes;
    private AppRepository mRepository;
    private Executor executor = Executors.newSingleThreadExecutor();

    //ViewModel constructor initializes the Repository class and gets the data from this class
    public MainViewModel(@NonNull Application application) {
        super(application);

        //ViewModel gets data from the Repository
        mRepository = AppRepository.getInstance(application.getApplicationContext());
        mNotes = mRepository.mNotes;
    }

    public void addSampleData() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                mRepository.addSampleData();
            }
        });
    }

    public void deleteData() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                mRepository.deleteAllNotes();
            }
        });
    }
}
