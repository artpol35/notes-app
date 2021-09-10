package polukhin.apps.anotes.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import polukhin.apps.anotes.model.Note;

@Dao
public interface NoteDao {

    @Query("SELECT * FROM Notes")
    LiveData<List<Note>> getAllNotes();

    @Delete
    void deleteNote(Note note);

    @Update
    void updateNote(Note note);

    @Insert
    void addNote(Note note);
}

