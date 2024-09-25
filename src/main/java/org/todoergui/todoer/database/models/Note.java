package org.todoergui.todoer.database.models;

import org.todoergui.todoer.database.schemas.NoteSchema;

import java.text.MessageFormat;

/**
 * Note Data Object
 * Contains the structure of a Note entr in the database
 */
public class Note {
    /** The Generated ID */
    private long id;
    /** The Title of the Note */
    private String title;
    /** The Content or Description of the Note */
    private String content;

    /**
     * Create a new Note Object
     * 
     * @param title   {@link #title}
     * @param content {@link #content}
     */
    public Note(final String title, final String content) {
        this.id = 1L + (long) (Math.random() * (10L - 1L));
        this.title = title;
        this.content = content;
    }

    /**
     * Create a new Note Object from a schema
     * 
     * @param schema {@link NoteSchema the schema}
     */
    public Note(final NoteSchema schema) {
        this.id = schema.getId();
        this.title = schema.getTitle();
        this.content = schema.getContent();
    }

    public Note() {
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return MessageFormat.format("Note'{'id={0}, title=''{1}'', content=''{2}'''}'", id, title, content);
    }

    /** @return A formatted string to look nice */
    public String format() {
        return MessageFormat.format("ID: {0}\nTitle:\n    {1}\nDesription:\n    {2}", id, title, content);
    }

    public void setId(final long id) {
        this.id = id;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public void setContent(final String content) {
        this.content = content;
    }
}
