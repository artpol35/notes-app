package polukhin.apps.anotes.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import polukhin.apps.anotes.model.Note;

@Database(entities = Note.class, version = 5)
public abstract class NoteDatabase extends RoomDatabase {

    private static NoteDatabase sNoteDatabase;

    public abstract NoteDao mNoteDao();

    public synchronized static NoteDatabase getInstance(Context context) {
        if (sNoteDatabase == null) {
            synchronized (NoteDatabase.class) {
                sNoteDatabase = Room.databaseBuilder(context, NoteDatabase.class, "noteDB")
                        .fallbackToDestructiveMigration().build();
            }
        }
        return sNoteDatabase;
    }

//    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
//        @Override
//        public void migrate(SupportSQLiteDatabase database) {
//            // Since we didn't alter the table, there's nothing else to do here.
//        }
//    };
//
//    static final Migration MIGRATION_2_3 = new Migration(2, 3) {
//        @Override
//        public void migrate(SupportSQLiteDatabase database) {
//            database.execSQL("ALTER TABLE notes "
//                    + " ADD COLUMN completed BOOLEAN");
//        }
//    };
}
