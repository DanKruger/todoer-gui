package org.todoergui.todoer.database;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import com.j256.ormlite.logger.Level;
import com.j256.ormlite.logger.Logger;
import com.j256.ormlite.table.TableUtils;
import org.todoergui.todoer.database.models.Note;
import org.todoergui.todoer.database.models.Todo;
import org.todoergui.todoer.database.schemas.NoteSchema;
import org.todoergui.todoer.database.schemas.TodoSchema;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {
    private static JdbcPooledConnectionSource conn;

    private static Dao<NoteSchema, Long> noteDao;
    private static Dao<TodoSchema, Long> todoDao;

    public DatabaseManager(final String url) throws SQLException {
        Logger.setGlobalLogLevel(Level.OFF);
        conn = new JdbcPooledConnectionSource(url);
        noteDao = DaoManager.createDao(conn, NoteSchema.class);
        todoDao = DaoManager.createDao(conn, TodoSchema.class);
        TableUtils.createTableIfNotExists(conn, NoteSchema.class);
        TableUtils.createTableIfNotExists(conn, TodoSchema.class);
    }

    @Override
    public String toString() {
        return "DatabaseManager []";
    }

    // CRUD

    public void createNote(final Note note) throws SQLException {
        noteDao.create(new NoteSchema(note));
    }

    public List<Note> getAllNotes() throws SQLException {
        final List<Note> notes = new ArrayList<>();
        for (final NoteSchema noteSchema : noteDao.queryForAll()) {
            notes.add(new Note(noteSchema));
        }
        return notes;
    }

    public Note readNote(final long id) throws SQLException {
        return new Note(noteDao.queryForId(id));
    }

    public void updateNote(final Note note) throws SQLException {
        noteDao.update(new NoteSchema(note));
    }

    public void deleteNote(final Note note) throws SQLException {
        noteDao.delete(new NoteSchema(note));
    }

    public void createTodo(final Todo todo) throws SQLException {
        todoDao.create(new TodoSchema(todo));
    }

    public List<Todo> getAllTodos() throws SQLException {
        final List<Todo> todos = new ArrayList<>();
        for (final TodoSchema todoSchema : todoDao.queryForAll()) {
            todos.add(new Todo(todoSchema));
        }
        return todos;
    }

    public Todo readTodo(final long id) throws SQLException {
        return new Todo(todoDao.queryForId(id));
    }

    public void updateTodo(final Todo todo) throws SQLException {
        todoDao.update(new TodoSchema(todo));
    }

    public void deleteTodo(final Todo todo) throws SQLException {
        todoDao.delete(new TodoSchema(todo));
    }

}
