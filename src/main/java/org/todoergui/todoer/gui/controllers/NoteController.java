package org.todoergui.todoer.gui.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import org.todoergui.todoer.gui.Note;
import org.todoergui.todoer.Todoer;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;

public class NoteController {

    @FXML
    private TextArea noteTitleInput;

    @FXML
    private TextArea noteContentInput;

    @FXML
    private Button formBtn;

    @FXML
    private ListView<Note> listView;

    private ObservableList<Note> list;
    private final Set<Note> uniq = new LinkedHashSet<>();

    @FXML
    protected void goForward() {
        try {
            Todoer.changeScene("/org/todoergui/todoer/todo-view.fxml","Tasks");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    protected void initialize() {
        list = FXCollections.observableArrayList();
        listView.setItems(list);
        listView.setCellFactory(listView -> new ListCell<Note>() {
            @Override
            protected void updateItem(Note item, boolean empty) {
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
                noteTitleInput.setText(newValue.getTitle());
                noteContentInput.setText(newValue.getContent());
            } else {
                formBtn.setText("Add Item");
            }
        });
    }

    private HBox createNoteCard(Note item) {
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
            list.remove(item);
            refreshList();
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
        MultipleSelectionModel<Note> selected = listView.getSelectionModel();
        int selectedIndex = selected.getSelectedIndex();
        String title = noteTitleInput.getText().trim();
        String content = noteContentInput.getText().trim();
        if(selectedIndex >= 0) {
            Note note = list.get(selectedIndex);
            if(!(title.isEmpty() && content.isEmpty())) {
                note.setTitle(title);
                note.setContent(content);
                refreshList();
                selected.clearSelection(selectedIndex);
            }

        } else {
            Note n = new Note(title,content);
            if ((!title.isEmpty() || !content.isEmpty()) && uniq.add(n)){
                list.add(n);
            }
        }

        noteTitleInput.clear();
        noteContentInput.clear();

    }
}