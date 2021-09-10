package polukhin.apps.anotes.view.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import polukhin.apps.anotes.R;
import polukhin.apps.anotes.viewmodel.NoteViewModelFactory;
import polukhin.apps.anotes.viewmodel.NotesViewModel;
import polukhin.apps.anotes.model.Note;

public class DetailedFragment extends Fragment {

    private EditText title, description;
    private Button buttonSaveFromDetailed;
    private NotesViewModel mNotesViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_detailed, container, false);

        getActivity().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);


        mNotesViewModel = new ViewModelProvider(this,
                new NoteViewModelFactory(this.getActivity()
                        .getApplication()))
                        .get(NotesViewModel.class);

        title = root.findViewById(R.id.editTextDetailedTitle);
        description = root.findViewById(R.id.editTextDetailedDescription);
        buttonSaveFromDetailed = root.findViewById(R.id.buttonDetailedSave);

        buttonSaveFromDetailed.setOnClickListener(v -> {
            if (title.getText().length() > 0 && description.getText().length() > 0) {
                mNotesViewModel.addNote(new Note(title.getText().toString(),
                        description.getText().toString()));
                Navigation.findNavController(root).navigate(R.id.saveChangesFromDetailed);
            } else {
                Toast.makeText(root.getContext(), "Empty fields!", Toast.LENGTH_SHORT).show();
            }
        });

        return root;
    }
}