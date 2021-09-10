package polukhin.apps.anotes.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.annotation.NonNull;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import polukhin.apps.anotes.R;
import polukhin.apps.anotes.viewmodel.NotesViewModel;
import polukhin.apps.anotes.databinding.CustomItemBinding;
import polukhin.apps.anotes.model.Note;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder> {

    private List<Note> notes = new ArrayList<>();
    private final NotesViewModel mNotesViewModel;

    public CustomAdapter(NotesViewModel notesViewModel) {
        mNotesViewModel = notesViewModel;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
        Collections.sort(notes);
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        CustomItemBinding binding = CustomItemBinding.inflate(inflater, parent, false);
        return new CustomViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {

        Note currentNote = notes.get(position);
        holder.binding.setNote(currentNote);

        holder.binding.imageViewPinned.setAlpha(currentNote.getPinned() ? 1 : 0.2f);

        holder.binding.getRoot().setOnLongClickListener(v -> {

            AlertDialog.Builder alertDialog = new AlertDialog.Builder(v.getContext());
            alertDialog.setPositiveButton("Редактировать", (dialog, which) -> {

            })
                    .setNegativeButton("Удалить", (dialog, which) ->
                            mNotesViewModel.removeNote(notes.get(position))).create().show();
            return true;
        });

        holder.binding.imageViewPinned.setOnClickListener(v -> {

            if (currentNote.getPinned()) {
                currentNote.setPinned(false);
            } else {
                currentNote.setPinned(true);
            }
            mNotesViewModel.updateNote(currentNote);
        });

    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public static class CustomViewHolder extends RecyclerView.ViewHolder {

        private final CustomItemBinding binding;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);

            binding = DataBindingUtil.bind(itemView);

        }
    }


}
