package polukhin.apps.anotes.model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Entity(tableName = "Notes")
public class Note implements Comparable<Note> {

    @PrimaryKey(autoGenerate = true)
    private int uid;

    private String title;
    private String description;
    private boolean pinned;
    private String date;

    public Note(int uid, String title, String description, boolean pinned) {
        this.uid = uid;
        this.title = title;
        this.description = description;
        this.pinned = pinned;
        SimpleDateFormat sdf = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss",
                Locale.getDefault());
        date = sdf.format(new Date());
    }

    @Ignore
    public Note(String title, String description) {
        this.title = title;
        this.description = description;
        SimpleDateFormat sdf = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss",
                Locale.getDefault());
        date = sdf.format(new Date());
    }

    public int getUid() {
        return uid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean getPinned() {
        return pinned;
    }

    public void setPinned(boolean pinned) {
        this.pinned = pinned;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public int compareTo(Note o) {

        if (this.getPinned() && !o.getPinned()) {
            return -1;
        }
        if (!this.getPinned() && o.getPinned()) {
            return 1;
        }

        return 0;
    }

    @Override
    public String toString() {
        return title + " " + description;
    }
}
