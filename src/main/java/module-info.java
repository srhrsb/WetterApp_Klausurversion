module com.brh.wetterapp_klausurversion {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires json.simple;
    requires java.net.http;
    requires javafx.graphics;


    opens com.brh.wetterapp_klausurversion to javafx.fxml;
    exports com.brh.wetterapp_klausurversion;
    exports com.brh.wetterapp_klausurversion.controller;
    opens com.brh.wetterapp_klausurversion.controller to javafx.fxml;
    exports com.brh.wetterapp_klausurversion.model;
    opens com.brh.wetterapp_klausurversion.model to javafx.fxml;
    exports com.brh.wetterapp_klausurversion.view;
    opens com.brh.wetterapp_klausurversion.view to javafx.fxml;
}