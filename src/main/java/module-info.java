module org.utl.dsm.dreamsoft_sicefa {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.utl.dsm.dreamsoft_sicefa to javafx.fxml;
    exports org.utl.dsm.dreamsoft_sicefa;

    exports org.utl.dsm.dreamsoft_sicefa.Controller;

    exports org.utl.dsm.dreamsoft_sicefa.Model;
}