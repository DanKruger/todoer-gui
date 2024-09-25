package org.todoergui.todoer.gui.controllers;

import javafx.fxml.FXML;
import org.todoergui.todoer.Todoer;

import java.io.IOException;

public class TodoController {

    @FXML
    protected void goBack(){
        try {
            Todoer.changeScene("/org/todoergui/todoer/note-view.fxml","Todoer ~ Notes");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
