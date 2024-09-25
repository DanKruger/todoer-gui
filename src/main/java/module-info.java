module org.todoergui.todoer {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires java.desktop;
    requires ormlite.jdbc;
    requires java.sql;

    exports org.todoergui.todoer.database.models;
    exports org.todoergui.todoer.database.schemas;
    opens org.todoergui.todoer.database.schemas to ormlite.jdbc;
    exports org.todoergui.todoer.database;
    exports org.todoergui.todoer.gui;
    opens org.todoergui.todoer.gui to javafx.fxml;
    exports org.todoergui.todoer.gui.controllers;
    opens org.todoergui.todoer.gui.controllers to javafx.fxml;
    exports org.todoergui.todoer;
    opens org.todoergui.todoer to javafx.fxml;
}