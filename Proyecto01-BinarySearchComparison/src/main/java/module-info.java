module ec.edu.espol.binarysearchcomparison {
    requires javafx.controls;
    requires javafx.fxml;

    opens ec.edu.espol.binarysearchcomparison to javafx.fxml;
    exports ec.edu.espol.binarysearchcomparison;
}
