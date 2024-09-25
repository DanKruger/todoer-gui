package org.todoergui.todoer.gui;

import org.todoergui.todoer.database.models.Note;

public class GuiNote {
    private long noteId;
    private String title;
    private String content;

    public GuiNote(String title, String content) {
        this.title = title;
        this.content = content;
    }
    public GuiNote(Note note) {
        this.noteId = note.getId();
        this.title = note.getTitle();
        this.content = note.getContent();
    }

    public long getNoteId() {
        return noteId;
    }

    public void setNoteId(long noteId) {

    }
    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public String getContent() { return content; }

    public void setContent(String content) { this.content = content; }

}
