module org.utl.dsm.dreamsoft_sicefa {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires unirest.java;
    requires java.desktop;

    opens org.utl.dsm.dreamsoft_sicefa to javafx.fxml;
    exports org.utl.dsm.dreamsoft_sicefa;

    opens org.utl.dsm.dreamsoft_sicefa.Controller to javafx.fxml;
    exports org.utl.dsm.dreamsoft_sicefa.Controller;

    opens org.utl.dsm.dreamsoft_sicefa.Model to com.google.gson;
}