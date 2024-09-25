package org.todoergui.todoer.database.schemas;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import org.todoergui.todoer.database.models.Note;

@DatabaseTable(tableName = "Notes")
public class NoteSchema {
    @DatabaseField(generatedId = true)
    private long id;
    @DatabaseField(canBeNull = false)
    private String title;
    @DatabaseField(canBeNull = false)
    private String content;

    public NoteSchema() { }

    public NoteSchema(Note note) {
        this.id = note.getId();
        this.title = note.getTitle();
        this.content = note.getContent();
    }

    public long getId() { return id; }

    public void setId(long id) { this.id = id; }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public String getContent() { return content; }

    public void setContent(String content) { this.content = content; }

}
