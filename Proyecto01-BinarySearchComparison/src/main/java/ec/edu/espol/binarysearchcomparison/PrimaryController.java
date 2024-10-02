package ec.edu.espol.binarysearchcomparison;

import ec.edu.espol.binarysearchcomparison.modelo.AlgorithmCreateArray;
import ec.edu.espol.binarysearchcomparison.modelo.BinarySearchAlgorithmIterative;
import ec.edu.espol.binarysearchcomparison.modelo.BinarySearchAlgorithmRecursive;
import ec.edu.espol.binarysearchcomparison.modelo.LinearAlgorithm;
import ec.edu.espol.binarysearchcomparison.modelo.AlgorithmTimeManager;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Duration;
import javafx.util.StringConverter;

public class PrimaryController implements Initializable{

    @FXML
    private TextField elementToSearch;
    @FXML
    private Slider selectElementsSlider;
    @FXML
    private Text txtBusquedaLineal;
    @FXML
    private Text txtBusquedaBinIterada;
    @FXML
    private Text txtBusquedaBinRecursiva;
    @FXML
    private Button btnBuscar;
    @FXML
    private Text txtNElements;
    @FXML
    private Pane paneBusqueda;
    @FXML
    private Pane paneGrafica;
    @FXML
    private CheckBox checkBoxGraph;
    @FXML
    private LineChart<Number, Number> lineChartGraph;

    private XYChart.Series<Number, Number> seriesLinearSearch;
    private XYChart.Series<Number, Number> seriesBinarySearchIterative;
    private XYChart.Series<Number, Number> seriesBinarySearchRecursive;
    private Thread updateGraphThread;
    private volatile boolean updating = false;
    @FXML
    private ToggleButton tglButtonInitGraph;
    @FXML
    private NumberAxis yAxis;
    @FXML
    private NumberAxis xAxis;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        paneBusqueda.setVisible(true);
        paneBusqueda.setManaged(true);
        paneGrafica.setVisible(false);
        paneGrafica.setManaged(false);
        // Inicializa las series de datos
        seriesLinearSearch = new XYChart.Series<>();
        seriesLinearSearch.setName("Búsqueda Lineal");
        

        // Habilitar los puntos en las series
        lineChartGraph.setCreateSymbols(true);
        seriesBinarySearchIterative = new XYChart.Series<>();
        seriesBinarySearchIterative.setName("Búsqueda Binaria Iterada");

        seriesBinarySearchRecursive = new XYChart.Series<>();
        seriesBinarySearchRecursive.setName("Búsqueda Binaria Recursiva");

        // Añadir las series al gráfico
        lineChartGraph.getData().addAll(seriesLinearSearch, seriesBinarySearchIterative, seriesBinarySearchRecursive);
        yAxis.setAutoRanging(true); // Activa el ajuste automático
        // Inicializa el valor del slider, por ejemplo, en 10 elementos.
        selectElementsSlider.setValue(10);
        selectElementsSlider.setMin(10.0);
        selectElementsSlider.setMax(3000000.0);
        selectElementsSlider.setMajorTickUnit(100000);  // Mostrar tick cada 100000 unidades
        selectElementsSlider.setMinorTickCount(0);       // No mostrar ticks menores
        selectElementsSlider.setShowTickLabels(true);    // Mostrar etiquetas
        selectElementsSlider.setShowTickMarks(true);     // Mostrar marcas
        selectElementsSlider.setLabelFormatter(new StringConverter<Double>() {
            @Override
            public String toString(Double n) {
                if (n == 1000000) return "1E6";  // Representación en notación científica
                if (n >= 100000) return String.format("%.0E", n);
                return n.intValue() + "";
            }

            @Override
            public Double fromString(String s) {
                return Double.valueOf(s);
            }
        });

        selectElementsSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            txtNElements.setText(String.valueOf(newVal.intValue()));
            adjustYAxisRange(newVal.intValue());  // Ajustar el rango del eje Y
        });
    }

    
    
    @FXML
    private void search(ActionEvent event) {
        try{
            // Validar que el campo de texto no esté vacío
            if (elementToSearch.getText().isEmpty()) {
                showTooltip(elementToSearch, "Por favor, ingrese un número a buscar.");
                return;
            }

            // Validar que el valor ingresado es un número dentro del rango
            int target = Integer.parseInt(elementToSearch.getText());
            final int minRange = 1;
            int maxRange = (int) Math.floor(selectElementsSlider.getValue());

            if (target < minRange || target > maxRange) {
                showTooltip(elementToSearch, "El número debe estar entre " + minRange + " y " + maxRange + ".");
                return;
            }

            // Si todo es válido, restablecer el estilo del campo
            elementToSearch.setStyle(null);
            int arraySize = (int) selectElementsSlider.getValue(); // Obtener tamaño del array del slider.

            // Utilizar AlgorithmCreateArray para generar el arreglo.
            AlgorithmCreateArray arrayGenerator = new AlgorithmCreateArray();
            int[] array = arrayGenerator.generateSequentialArray(arraySize);

            // Instanciar los algoritmos de búsqueda.
            LinearAlgorithm linearSearch = new LinearAlgorithm();
            BinarySearchAlgorithmIterative binarySearchIterative = new BinarySearchAlgorithmIterative();
            BinarySearchAlgorithmRecursive binarySearchRecursive = new BinarySearchAlgorithmRecursive();

            // Medir el tiempo de búsqueda usando Linear Search.
            long linearTime = AlgorithmTimeManager.measureTime(linearSearch, array, target);
            txtBusquedaLineal.setText(String.format("%.5f ms", AlgorithmTimeManager.toMilliseconds(linearTime)));

            // Medir el tiempo de búsqueda usando Binary Search Iterativo.
            long binaryIterativeTime = AlgorithmTimeManager.measureTime(binarySearchIterative, array, target);
            txtBusquedaBinIterada.setText(String.format("%.5f ms", AlgorithmTimeManager.toMilliseconds(binaryIterativeTime)));

            // Medir el tiempo de búsqueda usando Binary Search Recursivo.
            long binaryRecursiveTime = AlgorithmTimeManager.measureTime(binarySearchRecursive, array, target);
            txtBusquedaBinRecursiva.setText(String.format("%.5f ms", AlgorithmTimeManager.toMilliseconds(binaryRecursiveTime)));
            changeColors(linearTime, binaryIterativeTime, binaryRecursiveTime);
            
            long maxTime = Math.max(Math.max(linearTime, binaryIterativeTime), binaryRecursiveTime);
            adjustXAxisRange((long) AlgorithmTimeManager.toMilliseconds(maxTime));
        } catch (NumberFormatException e) {
             showTooltip(elementToSearch, "Por favor, ingrese un valor numérico válido.");
        }
    }
    
    private void showTooltip(TextField textField, String message) {
        Tooltip tooltip = new Tooltip(message);
        tooltip.setAutoHide(true);
        tooltip.setShowDelay(Duration.ONE); 
        tooltip.setHideDelay(Duration.seconds(4)); // Ocultar después de 4 segundos

        // Posiciona el Tooltip debajo del TextField
        Window window = textField.getScene().getWindow();
        Point2D pos = textField.localToScene(0.0, 0.0);
        double tooltipX = window.getX() + pos.getX() + textField.getScene().getX();
        double tooltipY = window.getY() + pos.getY() + textField.getScene().getY() + textField.getHeight();

        tooltip.show(textField, tooltipX, tooltipY);

        // Mantener el focus en el TextField
        textField.requestFocus();
    }
    
    private void adjustYAxisRange(int maxTime) {
        yAxis.setAutoRanging(true);  // Desactiva el auto-rango para personalizar los límites
        yAxis.setLowerBound(0);       // Establece el límite inferior en 0
        yAxis.setUpperBound(2);  // Establece el límite superior en el valor del slider
        yAxis.setTickUnit(maxTime / 5.0);  // Ajusta la separación entre las marcas
    }
    
    private void adjustXAxisRange(long maxIterations) {
        xAxis.setAutoRanging(false);  // Desactiva el ajuste automático
        if (maxIterations>=34) {
            xAxis.setLowerBound(maxIterations-34);
        }else{
            xAxis.setLowerBound(0);       // Establece el límite inferior en 0 ms
        }
        xAxis.setUpperBound(maxIterations);  // El límite superior es un 10% mayor que el tiempo máximo observado
        xAxis.setTickUnit(50);    // Incrementa el divisor para reducir la separación
    }
    
    private void changeColors(long linearTime, long binaryIterativeTime, long binaryRecursiveTime) {
        long maxTime = Math.max(Math.max(linearTime, binaryIterativeTime), binaryRecursiveTime);
        long minTime = Math.min(Math.min(linearTime, binaryIterativeTime), binaryRecursiveTime);

        // Cambia el color del tiempo más alto a rojo
        if (linearTime == maxTime) {
            txtBusquedaLineal.setStyle("-fx-fill: red;");
        } else if (linearTime == minTime) {
            txtBusquedaLineal.setStyle("-fx-fill: green;");
        } else {
            txtBusquedaLineal.setStyle("-fx-fill: #FFB200;");
        }

        if (binaryIterativeTime == maxTime) {
            txtBusquedaBinIterada.setStyle("-fx-fill: red;");
        } else if (binaryIterativeTime == minTime) {
            txtBusquedaBinIterada.setStyle("-fx-fill: green;");
        } else {
            txtBusquedaBinIterada.setStyle("-fx-fill: #FFB200;");
        }

        if (binaryRecursiveTime == maxTime) {
            txtBusquedaBinRecursiva.setStyle("-fx-fill: red;");
        } else if (binaryRecursiveTime == minTime) {
            txtBusquedaBinRecursiva.setStyle("-fx-fill: green;");
        } else {
            txtBusquedaBinRecursiva.setStyle("-fx-fill: #FFB200;");
        }
    }

    @FXML
    private void turnGraphic(ActionEvent event) {
        CheckBox checkBoxGraph = (CheckBox) event.getTarget();
        if (checkBoxGraph.isSelected()){
            showGraphic();
        }else{
            hideGraphic();
        }
    }
    
    private void showGraphic(){
        double newWidth = (double) App.PREDET_WIDTH+paneGrafica.getPrefWidth();
        redimensionateWindow(newWidth, App.PREDET_HEIGHT);
        paneGrafica.setVisible(true);
        paneGrafica.setManaged(true);
        seriesLinearSearch.getNode().lookup(".chart-series-line").setStyle("-fx-stroke-width: 2px;");
        seriesBinarySearchIterative.getNode().lookup(".chart-series-line").setStyle("-fx-stroke-width: 2px;");
        seriesBinarySearchRecursive.getNode().lookup(".chart-series-line").setStyle("-fx-stroke-width: 2px;");
    }

    private void hideGraphic() {
        redimensionateWindow(App.PREDET_WIDTH, App.PREDET_HEIGHT);
        paneGrafica.setVisible(false);
        paneGrafica.setManaged(false);
    }
    
    private void redimensionateWindow(double newWidth, double newHeight){
        Stage primaryWindow = (Stage) paneBusqueda.getScene().getWindow();
        primaryWindow.setWidth(newWidth);
        primaryWindow.setHeight(newHeight+39);
    }

    @FXML
    private void initGraph(ActionEvent event) {
        ToggleButton toggleButton = (ToggleButton) event.getTarget();
        
        if (toggleButton.isSelected()) {
            toggleButton.setText("Detener Actualización");
            startUpdateThread();
        }else{
            stopUpdateThread();
            toggleButton.setText("Iniciar Actualización");
        }
    }


    private void startUpdateThread() {
        updating = true;
        final int[] iterationCount = {1}; // Contador de iteraciones
        final int numberOfPointsPerAlg = 35;
        updateGraphThread = new Thread(() -> {
            while (updating) {
                try {
                    javafx.application.Platform.runLater(() -> {
                        int arraySize = (int) selectElementsSlider.getValue();
                        AlgorithmCreateArray arrayGenerator = new AlgorithmCreateArray();
                        int[] array = arrayGenerator.generateSequentialArray(arraySize);
                        int target = (int) (Math.random() * arraySize);

                        LinearAlgorithm linearSearch = new LinearAlgorithm();
                        BinarySearchAlgorithmIterative binarySearchIterative = new BinarySearchAlgorithmIterative();
                        BinarySearchAlgorithmRecursive binarySearchRecursive = new BinarySearchAlgorithmRecursive();

                        long linearTime = AlgorithmTimeManager.measureTime(linearSearch, array, target);
                        long binaryIterativeTime = AlgorithmTimeManager.measureTime(binarySearchIterative, array, target);
                        long binaryRecursiveTime = AlgorithmTimeManager.measureTime(binarySearchRecursive, array, target);

                        // Añadir nuevos puntos de datos a las series usando la iteración como eje X
                        seriesLinearSearch.getData().add(new XYChart.Data<>(iterationCount[0], AlgorithmTimeManager.toMilliseconds(linearTime)));
                        seriesBinarySearchIterative.getData().add(new XYChart.Data<>(iterationCount[0], AlgorithmTimeManager.toMilliseconds(binaryIterativeTime)));
                        seriesBinarySearchRecursive.getData().add(new XYChart.Data<>(iterationCount[0], AlgorithmTimeManager.toMilliseconds(binaryRecursiveTime)));

                        // Incrementar el contador de iteraciones
                        iterationCount[0]++;

                        // Limitar la cantidad de datos en la gráfica
                        if (seriesLinearSearch.getData().size()+2 > numberOfPointsPerAlg) seriesLinearSearch.getData().remove(0);
                        if (seriesBinarySearchIterative.getData().size()+2 > numberOfPointsPerAlg) seriesBinarySearchIterative.getData().remove(0);
                        if (seriesBinarySearchRecursive.getData().size()+2 > numberOfPointsPerAlg) seriesBinarySearchRecursive.getData().remove(0);

                        adjustXAxisRange(iterationCount[0]);
                    });

                    Thread.sleep(500); // Intervalo de actualización cada 0.1 segundos
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });
        updateGraphThread.setDaemon(true);
        updateGraphThread.start();
    }

    private void stopUpdateThread() {
        updating = false;
        if (updateGraphThread != null && updateGraphThread.isAlive()) {
            updateGraphThread.interrupt(); // Detener el hilo de forma segura
        }
        updateGraphThread = null;
    }

}
