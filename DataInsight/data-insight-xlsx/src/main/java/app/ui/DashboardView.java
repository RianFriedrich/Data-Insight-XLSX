package app.ui;

import java.util.List;
import java.util.Map;

import app.service.DataAnalyzer;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class DashboardView {

    private VBox root = new VBox(10);

    public DashboardView(List<Map<String, String>> dados, ComboBox<String> seletor) {

        Label mediaLabel = new Label();
        Label somaLabel = new Label();
        Label maxLabel = new Label();
        Label minLabel = new Label();

        Button calcular = new Button("Calcular Métricas");

        calcular.setOnAction(e -> {
            String atributo = seletor.getValue();
            if (atributo == null)
                return;

            mediaLabel.setText("Média: " + DataAnalyzer.media(dados, atributo));
            somaLabel.setText("Soma: " + DataAnalyzer.soma(dados, atributo));
            maxLabel.setText("Máx: " + DataAnalyzer.max(dados, atributo));
            minLabel.setText("Min: " + DataAnalyzer.min(dados, atributo));
        });

        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();

        BarChart<String, Number> grafico = new BarChart<>(xAxis, yAxis);

        Button gerarGrafico = new Button("Gerar Gráfico");

        gerarGrafico.setOnAction(e -> {
            grafico.getData().clear();

            String atributo = seletor.getValue();
            if (atributo == null)
                return;

            XYChart.Series<String, Number> serie = new XYChart.Series<>();

            int i = 1;
            for (Map<String, String> linha : dados) {
                try {
                    double valor = Double.parseDouble(linha.get(atributo));
                    String nome = linha.getOrDefault("NOME", "Item " + i);

                    serie.getData().add(new XYChart.Data<>(nome, valor));
                    i++;
                } catch (NumberFormatException ex) {
                }
            }

            grafico.getData().add(serie);
        });

        root.getChildren().addAll(
                calcular,
                mediaLabel, somaLabel, maxLabel, minLabel,
                gerarGrafico,
                grafico);
    }

    public VBox getRoot() {
        return root;
    }
}