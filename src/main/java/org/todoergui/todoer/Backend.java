package org.todoergui.todoer;

import org.todoergui.todoer.database.DatabaseManager;

import java.sql.SQLException;

public class Backend {
    private static DatabaseManager database;

    public void setDatabase(DatabaseManager newDb){
            Backend.database = newDb;
    }
    public DatabaseManager getDatabase(){
        return Backend.database;
    }

    public Backend(String dbUrl ) throws SQLException {
        setDatabase(new DatabaseManager(dbUrl));
    }
}
