package app;

import java.io.File;
import java.util.List;
import java.util.Map;

import app.service.ExcelReader;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage) {

        TableView<Map<String, String>> tabela = new TableView<>();
        Button btnGrafico = new Button("Gerar Gráfico");
        final FilteredList<Map<String, String>>[] filtrada = new FilteredList[1];

        tabela.setPrefHeight(300);

        tabela.setRowFactory(tv -> new TableRow<Map<String, String>>() {
            @Override
            protected void updateItem(Map<String, String> item, boolean empty) {
                super.updateItem(item, empty);

                if (item == null || empty) {
                    setStyle("");
                } else if (item.equals(tabela.getSelectionModel().getSelectedItem())) {
                    setStyle("-fx-background-color: lightgreen;");
                } else {
                    setStyle("");
                }
            }
        });

        Button btn = new Button("Carregar Excel");

        ComboBox<String> seletor = new ComboBox<>();
        Button btnMaior = new Button("Maior");
        Button btnMenor = new Button("Menor");

        btn.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            File file = fileChooser.showOpenDialog(stage);

            if (file != null) {

                List<Map<String, String>> dados = ExcelReader.read(file.getAbsolutePath());

                tabela.getItems().clear();
                tabela.getColumns().clear();

                if (!dados.isEmpty()) {

                    seletor.getItems().clear();
                    seletor.getItems().addAll(dados.get(0).keySet());

                    for (String key : dados.get(0).keySet()) {
                        TableColumn<Map<String, String>, String> coluna = new TableColumn<>(key);
                        coluna.setSortable(true);

                        coluna.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(key)));

                        coluna.setComparator((a, b) -> {
                            try {
                                double da = Double.parseDouble(a);
                                double db = Double.parseDouble(b);
                                return Double.compare(da, db);
                            } catch (NumberFormatException ex) {
                                return a.compareTo(b);
                            }
                        });

                        tabela.getColumns().add(coluna);
                    }

                    ObservableList<Map<String, String>> lista = FXCollections.observableArrayList(dados);
                    filtrada[0] = new FilteredList<>(lista, p -> true);

                    tabela.setItems(filtrada[0]);
                }
            }
        });

        btnMaior.setOnAction(ev -> {
            String atributo = seletor.getValue();
            if (atributo == null)
                return;

            Map<String, String> maior = null;

            for (Map<String, String> linha : tabela.getItems()) {
                try {
                    double valor = Double.parseDouble(linha.get(atributo));

                    if (maior == null || valor > Double.parseDouble(maior.get(atributo))) {
                        maior = linha;
                    }

                } catch (NumberFormatException ex) {
                }
            }

            if (maior != null) {
                tabela.getSelectionModel().select(maior);
                tabela.scrollTo(maior);
            }
        });

        btnMenor.setOnAction(ev -> {
            String atributo = seletor.getValue();
            if (atributo == null)
                return;

            Map<String, String> menor = null;

            for (Map<String, String> linha : tabela.getItems()) {
                try {
                    double valor = Double.parseDouble(linha.get(atributo));

                    if (menor == null || valor < Double.parseDouble(menor.get(atributo))) {
                        menor = linha;
                    }

                } catch (NumberFormatException ex) {
                }
            }

            if (menor != null) {
                tabela.getSelectionModel().select(menor);
                tabela.scrollTo(menor);
            }
        });

        TextField filtro = new TextField();
        filtro.setPromptText("Filtro");
        filtro.setPrefWidth(200);

        filtro.textProperty().addListener((obs, oldVal, newVal) -> {

            if (filtrada[0] == null)
                return;

            filtrada[0].setPredicate(linha -> {
                if (newVal == null || newVal.isEmpty())
                    return true;

                String atributo = seletor.getValue();
                if (atributo == null)
                    return true;

                String valor = linha.get(atributo);

                return valor != null && valor.toLowerCase().contains(newVal.toLowerCase());
            });
        });

        tabela.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            tabela.refresh();
        });

        btnGrafico.setOnAction(ev -> {

            String atributo = seletor.getValue();
            if (atributo == null)
                return;

            CategoryAxis xAxis = new CategoryAxis();
            NumberAxis yAxis = new NumberAxis();

            BarChart<String, Number> grafico = new BarChart<>(xAxis, yAxis);
            grafico.setTitle("Gráfico de " + atributo);
            grafico.setLegendVisible(false);

            XYChart.Series<String, Number> serie = new XYChart.Series<>();

            int i = 1;
            for (Map<String, String> linha : tabela.getItems()) {
                try {
                    double valor = Double.parseDouble(linha.get(atributo));

                    String nome = linha.get("NOME");
                    if (nome == null || nome.isEmpty()) {
                        nome = "Item " + i;
                    }

                    XYChart.Data<String, Number> data = new XYChart.Data<>(nome, valor);
                    serie.getData().add(data);

                    final String nomeFinal = nome;
                    final double valorFinal = valor;
                    final Map<String, String> linhaFinal = linha;

                    data.nodeProperty().addListener((obs, oldNode, node) -> {
                        if (node != null) {

                            node.setOnMouseClicked(event -> {
                                tabela.getSelectionModel().select(linhaFinal);
                                tabela.scrollTo(linhaFinal);
                            });

                            Tooltip tooltip = new Tooltip(nomeFinal + ": " + valorFinal);
                            Tooltip.install(node, tooltip);
                        }
                    });

                    i++;

                } catch (NumberFormatException ex) {
                }
            }

            grafico.getData().add(serie);
            grafico.setAnimated(false);
            grafico.setPrefSize(600, 400);

            Stage novaJanela = new Stage();
            VBox rootGrafico = new VBox(grafico);

            Scene sceneGrafico = new Scene(rootGrafico, 600, 400);

            novaJanela.setTitle("Dashboard - " + atributo);
            novaJanela.setScene(sceneGrafico);
            novaJanela.show();
        });

        HBox controles = new HBox(10, seletor, btnMaior, btnMenor, filtro, btnGrafico);
        VBox root = new VBox(15, btn, controles, tabela);
        root.setStyle("-fx-padding: 15;");

        Scene scene = new Scene(root, 500, 400);

        stage.setScene(scene);
        stage.setTitle("Data Insight XLSX");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}