package org.todoergui.todoer.gui.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import org.todoergui.todoer.database.models.Note;
import org.todoergui.todoer.gui.GuiNote;
import org.todoergui.todoer.Todoer;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class NoteController {

    @FXML
    private TextArea noteTitleInput;

    @FXML
    private TextArea noteContentInput;

    @FXML
    private Button formBtn;

    @FXML
    private ListView<GuiNote> listView;

    @FXML
    private Button clearBtn;

    private ObservableList<GuiNote> list;
    private final Set<GuiNote> uniq = new LinkedHashSet<>();
    private long selectedNoteId;

    @FXML
    protected void goForward() {
        try {
            Todoer.changeScene("/org/todoergui/todoer/todo-view.fxml","Todoer ~ Tasks");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    protected void initialize() throws SQLException {
        updateList();
        clearBtn.setVisible(false);
        listView.setCellFactory(listView -> new ListCell<GuiNote>() {
            @Override
            protected void updateItem(GuiNote item, boolean empty) {
                super.updateItem(item, empty);
                if(empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    HBox card = createNoteCard(item);
                    setGraphic(card);
                }
            }
        });

        listView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue != null) {
                formBtn.setText("Update");
                clearBtn.setVisible(true);
                selectedNoteId = newValue.getNoteId();
                noteTitleInput.setText(newValue.getTitle());
                noteContentInput.setText(newValue.getContent());
            } else {
                formBtn.setText("Add Item");
                clearBtn.setVisible(false);
            }
        });
    }

    private void updateList() {
        List<GuiNote> dBNotes = getDbNotes();
        list = FXCollections.observableArrayList();
        list.addAll(dBNotes);
        listView.setItems(list);
    }

    public boolean showDialog(String title, String message) {
        Dialog<Boolean> dialog = new Dialog<>();
        dialog.setTitle(title);
        Label confirmationLabel = new Label(message);
        dialog.getDialogPane().setContent(new VBox(10, confirmationLabel));
        ButtonType confirmButtonType = new ButtonType("Confirm", ButtonBar.ButtonData.YES);
        ButtonType cancelButtonType = new ButtonType("Cancel", ButtonBar.ButtonData.NO);
        dialog.getDialogPane().getButtonTypes().addAll(confirmButtonType, cancelButtonType);
        dialog.setResultConverter(dialogButton -> dialogButton == confirmButtonType);
        return dialog.showAndWait().orElse(false);
    }

    private HBox createNoteCard(GuiNote item) {
        HBox card = new HBox();
        VBox inner = new VBox();

        String iTitle = item.getTitle();
        Text title =  new Text(iTitle);
        title.setFont(Font.font(16));
        title.setWrappingWidth(200);

        String iContent = item.getContent();
        Text content =  new Text(iContent);
        content.setWrappingWidth(200);

        Button delete = new Button("Delete");
        delete.setOnAction(event -> {
            if(showDialog("Confirm Deletion", "This action is permanent")){
                this.selectedNoteId = item.getNoteId();
                list.remove(item);
                deleteNote( new Note(item) );
                clearInput();
                refreshList();
            }
        });

        if(iTitle.isEmpty() && !iContent.isEmpty()) {
            inner.getChildren().addAll(content);
        } else if(iContent.isEmpty() && !iTitle.isEmpty()) {
            inner.getChildren().addAll(title);
        } else {
            inner.getChildren().addAll(title,content);
        }

        card.getChildren().addAll(inner, delete);
        card.setAlignment(Pos.CENTER);
        card.setPrefWidth(200);

        return card;
    }

    private void refreshList(){
        listView.refresh();
    }

    @FXML
    protected void handleAddItem() {
        MultipleSelectionModel<GuiNote> selected = listView.getSelectionModel();
        int selectedIndex = selected.getSelectedIndex();
        String title = noteTitleInput.getText().trim();
        String content = noteContentInput.getText().trim();
        if(selectedIndex >= 0) {
            GuiNote guiNote = list.get(selectedIndex);
            if(!(title.isEmpty() && content.isEmpty())) {
                guiNote.setTitle(title);
                guiNote.setContent(content);
                updateNote(new Note(guiNote));
                refreshList();
            }

        } else {
            GuiNote n = new GuiNote(title,content);
            Note dbNote = new Note(title,content);
            if ((!title.isEmpty() || !content.isEmpty()) && uniq.add(n)){
                list.add(n);
                createNote(dbNote);
            }
        }
        updateList();
        clearInput();

    }

    @FXML
    protected void cancelUpdate(){
        if(showDialog("Cancel Update", "Any unsaved changes will be lost")){
            clearInput();
        }
    }

    private void clearInput() {
        noteTitleInput.clear();
        noteContentInput.clear();
        listView.getSelectionModel().clearSelection();
    }

    private List<GuiNote> getDbNotes(){
        List<GuiNote> dbNotes = new ArrayList<>();
        try {
            for(Note note : Todoer.getServer().getDatabase().getAllNotes()){
                dbNotes.add(new GuiNote(note));
            }
        } catch (SQLException e) {
            return dbNotes;
        }
        return dbNotes;
    }

    private void deleteNote(Note note){
        try {
            Todoer.getServer().getDatabase().deleteNote(note);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void updateNote(Note note){
        try {
            Todoer.getServer().getDatabase().updateNote(note);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void createNote(Note note){
        try {
            Todoer.getServer().getDatabase().createNote(note);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}