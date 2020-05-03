package com.example.android.notetakingapp.ui;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.notetakingapp.EditorActivity;
import com.example.android.notetakingapp.R;
import com.example.android.notetakingapp.model.NoteEntity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.android.notetakingapp.constants.Constants.NOTE_ID;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder> {

    private final List<NoteEntity> mNotes;
    private final Context mContext;

    public NotesAdapter(List<NoteEntity> mNotes, Context mContext) {
        this.mNotes = mNotes;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.note_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //Get the current note
        NoteEntity note = mNotes.get(position);
        holder.mTextView.setText(note.getText());
        holder.mFab.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, EditorActivity.class);
            intent.putExtra(NOTE_ID, note.getId());
            mContext.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return mNotes.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.note_text)
        TextView mTextView;

        @BindView(R.id.fab)
        FloatingActionButton mFab;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            //Bind the views
            ButterKnife.bind(this, itemView);
        }
    }
}
