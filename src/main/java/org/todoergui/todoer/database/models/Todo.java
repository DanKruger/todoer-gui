
package org.todoergui.todoer.database.models;

import org.todoergui.todoer.database.schemas.TodoSchema;

import java.text.MessageFormat;

/**
 * Todo Data Object
 * Contains the structure of a Todo entry in the database
 */
public class Todo {
    /** The Generated ID */
    private long id;
    /** The Title of the Todo */
    private String title;
    /** The Content or Description of the Todo */
    private String content;
    /** {@link Boolean a boolean} value indicating the completion status */
    private boolean complete = false;

    /**
     * Create a new Todo object
     * 
     * @param title   {@link #title}
     * @param content {@link #content}
     */
    public Todo(final String title, final String content) {
        this.id = 1L + (long) (Math.random() * (10L - 1L));
        this.title = title;
        this.content = content;
    }

    /**
     * Create a new Todo object from Schema
     * 
     * @param schema {@link TodoSchema the schema}
     */
    public Todo(final TodoSchema schema) {
        this.id = schema.getId();
        this.title = schema.getTitle();
        this.content = schema.getContent();
        this.complete = schema.isComplete();
    }

    public Todo() {
    }

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(final boolean complete) {
        this.complete = complete;
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
        return MessageFormat.format("Todo'{'id={0}, title=''{1}'', content=''{2}'''}'", id, title, content);
    }

    /** @return A formatted string to look nice */
    public String format() {
        final String cTitle = complete ? "\033[9m" + title + "\033[0m" : title;
        return MessageFormat.format("{0}. [{1}] {2}", id, complete ? "X" : " ", cTitle);
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
