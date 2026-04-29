package app.controller;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import app.service.ExcelReader;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class MainController {

    @FXML
    private VBox dashboardBox;
    @FXML
    private TableView<Map<String, String>> tabela;
    @FXML
    private ComboBox<String> seletor;
    @FXML
    private Button btnMaior;
    @FXML
    private Button btnMenor;
    @FXML
    private Button btnCarregar;
    @FXML
    private TextField filtro;

    private FilteredList<Map<String, String>> filtrada;

    @FXML
    private Label lblStats;

    @FXML
    private Button btnExportar;

    @FXML
    public void initialize() {

        btnCarregar.setOnAction(e -> carregarExcel());

        btnMaior.setOnAction(e -> selecionarMaior());
        btnMenor.setOnAction(e -> selecionarMenor());

        filtro.textProperty().addListener((obs, oldVal, newVal) -> aplicarFiltro(newVal));

        tabela.getSelectionModel().selectedItemProperty().addListener((obs, o, n) -> tabela.refresh());

        seletor.valueProperty().addListener((obs, oldVal, atributo) -> {
            if (atributo != null) {

                dashboardBox.getChildren().clear();

                atualizarDashboard(atributo);

                BarChart<String, Number> grafico = criarGrafico(atributo);
                dashboardBox.getChildren().add(grafico);

            }
        });

        btnExportar.setOnAction(e -> {

            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Salvar CSV");
            fileChooser.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter("CSV", "*.csv"));

            File file = fileChooser.showSaveDialog(null);

            if (file == null)
                return;

            try (PrintWriter writer = new PrintWriter(file)) {

                List<String> colunas = new ArrayList<>(tabela.getColumns().size());
                for (TableColumn<?, ?> col : tabela.getColumns()) {
                    colunas.add(col.getText());
                }
                writer.println(String.join(",", colunas));

                for (Map<String, String> linha : tabela.getItems()) {
                    List<String> valores = new ArrayList<>();
                    for (String col : colunas) {
                        valores.add(linha.getOrDefault(col, ""));
                    }
                    writer.println(String.join(",", valores));
                }

            } catch (Exception ex) {

            }
        });
    }

    private void carregarExcel() {
        seletor.getSelectionModel().selectFirst();
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(new Stage());

        if (file == null)
            return;

        List<Map<String, String>> dados = ExcelReader.read(file.getAbsolutePath());

        tabela.getItems().clear();
        tabela.getColumns().clear();

        if (dados.isEmpty())
            return;

        seletor.getItems().clear();
        seletor.getItems().addAll(dados.get(0).keySet());

        for (String key : dados.get(0).keySet()) {
            TableColumn<Map<String, String>, String> coluna = new TableColumn<>(key);

            coluna.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(key)));

            coluna.setComparator((a, b) -> {
                try {
                    return Double.compare(Double.parseDouble(a), Double.parseDouble(b));
                } catch (NumberFormatException e) {
                    return a.compareTo(b);
                }
            });

            tabela.getColumns().add(coluna);
        }

        ObservableList<Map<String, String>> lista = FXCollections.observableArrayList(dados);
        filtrada = new FilteredList<>(lista, p -> true);

        tabela.setItems(filtrada);
    }

    private void atualizarDashboard(String atributo) {

        dashboardBox.getChildren().clear();

        double soma = 0;
        double max = Double.MIN_VALUE;
        double min = Double.MAX_VALUE;
        int count = 0;

        for (Map<String, String> linha : tabela.getItems()) {
            try {
                double v = Double.parseDouble(linha.get(atributo));

                soma += v;
                max = Math.max(max, v);
                min = Math.min(min, v);
                count++;

            } catch (NumberFormatException ignored) {
            }
        }

        if (count == 0) {
            dashboardBox.getChildren().add(new Label("Coluna não numérica"));
            return;
        }

        double media = soma / count;

        Label l1 = new Label("Média: " + media);
        Label l2 = new Label("Soma: " + soma);
        Label l3 = new Label("Máximo: " + max);
        Label l4 = new Label("Mínimo: " + min);
        Label l5 = new Label("Quantidade: " + count);

        dashboardBox.getChildren().addAll(l1, l2, l3, l4, l5);
    }

    private void calcularEstatisticas(String atributo) {

        double soma = 0;
        int count = 0;

        for (Map<String, String> linha : tabela.getItems()) {
            try {
                double valor = Double.parseDouble(linha.get(atributo));
                soma += valor;
                count++;
            } catch (NumberFormatException e) {
            }
        }

        if (count == 0)
            return;

        double media = soma / count;

        lblStats.setText(
                "Soma: " + soma +
                        " | Média: " + String.format("%.2f", media) +
                        " | Registros: " + count);
    }

    private void aplicarFiltro(String texto) {
        if (filtrada == null)
            return;

        filtrada.setPredicate(linha -> {
            if (texto == null || texto.isEmpty())
                return true;

            String atributo = seletor.getValue();
            if (atributo == null)
                return true;

            String valor = linha.get(atributo);
            return valor != null && valor.toLowerCase().contains(texto.toLowerCase());
        });
    }

    private BarChart<String, Number> criarGrafico(String atributo) {

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

                serie.getData().add(new XYChart.Data<>(nome, valor));
                i++;

            } catch (NumberFormatException ignored) {
            }
        }

        grafico.getData().add(serie);
        grafico.setPrefHeight(300);

        return grafico;
    }

    private void selecionarMaior() {
        selecionarExtremo(true);
    }

    private void selecionarMenor() {
        selecionarExtremo(false);
    }

    private void selecionarExtremo(boolean maior) {
        String atributo = seletor.getValue();
        if (atributo == null)
            return;

        Map<String, String> escolhido = null;

        for (Map<String, String> linha : tabela.getItems()) {
            try {
                double valor = Double.parseDouble(linha.get(atributo));

                if (escolhido == null ||
                        (maior && valor > Double.parseDouble(escolhido.get(atributo))) ||
                        (!maior && valor < Double.parseDouble(escolhido.get(atributo)))) {

                    escolhido = linha;
                }

            } catch (NumberFormatException ignored) {
            }
        }

        if (escolhido != null) {
            tabela.getSelectionModel().select(escolhido);
            tabela.scrollTo(escolhido);
        }
    }
}