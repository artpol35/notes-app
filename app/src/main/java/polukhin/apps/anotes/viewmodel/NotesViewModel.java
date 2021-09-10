package polukhin.apps.anotes.viewmodel;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import polukhin.apps.anotes.model.Note;
import polukhin.apps.anotes.db.NoteDao;
import polukhin.apps.anotes.db.NoteDatabase;

public class NotesViewModel extends ViewModel {

    private NoteDao mNoteDao;
    private LiveData<List<Note>> notes;

    public NotesViewModel(Application application) {

        NoteDatabase db = NoteDatabase.getInstance(application);
        mNoteDao = db.mNoteDao();
        notes = mNoteDao.getAllNotes();
    }

    public LiveData<List<Note>> getNotes() {
        return notes;
    }

    public void addNote(Note note) {
        new asyncTaskInsert(mNoteDao).execute(note);
    }

    private static class asyncTaskInsert extends AsyncTask<Note, Void, Void> {

        private NoteDao mNoteDao;

        asyncTaskInsert(NoteDao noteDao) {
            mNoteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            mNoteDao.addNote(notes[0]);
            return null;
        }
    }

    public void removeNote(Note note) {
        new asyncTaskRemove(mNoteDao).execute(note);
    }

    private static class asyncTaskRemove extends AsyncTask<Note,Void,Void> {

        private NoteDao mNoteDao;

        asyncTaskRemove(NoteDao noteDao) {
            mNoteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            mNoteDao.deleteNote(notes[0]);
            return null;
        }
    }

    public void updateNote(Note note) {
        new asyncTaskUpdate(mNoteDao).execute(note);
    }

    private static class asyncTaskUpdate extends AsyncTask<Note,Void,Void> {

        private NoteDao mNoteDao;

        asyncTaskUpdate(NoteDao noteDao) {
            mNoteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            mNoteDao.updateNote(notes[0]);
            return null;
        }
    }


}