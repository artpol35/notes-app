package polukhin.apps.anotes.view.fragments;

import androidx.activity.OnBackPressedCallback;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import polukhin.apps.anotes.R;
import polukhin.apps.anotes.view.CustomAdapter;
import polukhin.apps.anotes.viewmodel.NoteViewModelFactory;
import polukhin.apps.anotes.viewmodel.NotesViewModel;
import polukhin.apps.anotes.model.Note;

public class NotesFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.notes_fragment, container, false);

        ImageView imageViewPomodoro = root.findViewById(R.id.imageViewPomodoro);
        RecyclerView recyclerView = root.findViewById(R.id.noteFragmentRecyclerView);
        NotesViewModel viewModel = new ViewModelProvider(this,
                new NoteViewModelFactory(this.getActivity()
                        .getApplication()))
                        .get(NotesViewModel.class);

        CustomAdapter adapter = new CustomAdapter(viewModel);
        recyclerView.setAdapter(adapter);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,
                    StaggeredGridLayoutManager.VERTICAL));
        } else {
            recyclerView.setLayoutManager(new StaggeredGridLayoutManager(3,
                    StaggeredGridLayoutManager.VERTICAL));
        }

        viewModel.getNotes().observe(getViewLifecycleOwner(), notes -> {
            StringBuilder test = new StringBuilder();
            for (Note n : notes
                 ) {
                test.append(" ").append(n.toString());
            }
            Log.i("TestDb", test.toString());
            adapter.setNotes(notes);
            adapter.notifyDataSetChanged();
        });

        FloatingActionButton fab_remove = root.findViewById(R.id.fabAddNote);
        fab_remove.setOnClickListener(view ->
                Navigation.findNavController(root).navigate(R.id.actionToDetailed));

        imageViewPomodoro.setOnClickListener(v ->
                Navigation.findNavController(root).navigate(R.id.action_to_pomodoro));
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
            }
        };
        requireActivity().getOnBackPressedDispatcher()
                .addCallback(getViewLifecycleOwner(), callback);

        return root;
    }

}