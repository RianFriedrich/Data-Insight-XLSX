package app.ui;

import java.io.File;
import java.util.List;
import java.util.Map;

import app.service.ExcelReader;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

public class MainView {

    private VBox root = new VBox(10);

    public VBox getRoot() {
        return root;
    }

    public MainView() {

        TableView<Map<String, String>> tabela = new TableView<>();
        ComboBox<String> seletor = new ComboBox<>();
        TextField filtro = new TextField();

        Button carregar = new Button("Carregar Excel");

        TabPane tabs = new TabPane();
        Tab abaTabela = new Tab("Tabela");
        Tab abaDashboard = new Tab("Dashboard");

        tabs.getTabs().addAll(abaTabela, abaDashboard);

        VBox telaTabela = new VBox(10, carregar, seletor, filtro, tabela);
        abaTabela.setContent(telaTabela);

        carregar.setOnAction(e -> {

            FileChooser fc = new FileChooser();
            File file = fc.showOpenDialog(null);

            if (file == null)
                return;

            List<Map<String, String>> dados = ExcelReader.read(file.getAbsolutePath());

            tabela.getColumns().clear();

            if (!dados.isEmpty()) {

                seletor.getItems().setAll(dados.get(0).keySet());

                for (String key : dados.get(0).keySet()) {
                    TableColumn<Map<String, String>, String> col = new TableColumn<>(key);
                    col.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().get(key)));
                    tabela.getColumns().add(col);
                }

                ObservableList<Map<String, String>> lista = FXCollections.observableArrayList(dados);
                FilteredList<Map<String, String>> filtrada = new FilteredList<>(lista, p -> true);

                tabela.setItems(filtrada);

                filtro.textProperty().addListener((obs, o, n) -> {
                    filtrada.setPredicate(linha -> {
                        if (n == null || n.isEmpty())
                            return true;

                        String att = seletor.getValue();
                        if (att == null)
                            return true;

                        return linha.get(att).toLowerCase().contains(n.toLowerCase());
                    });
                });

                DashboardView dashboard = new DashboardView(dados, seletor);
                abaDashboard.setContent(dashboard.getRoot());
            }
        });

        root.getChildren().add(tabs);
    }
}