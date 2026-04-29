package app.model;

import java.util.Map;

public class DataRow {
    private final Map<String, String> dados;

    public DataRow(Map<String, String> dados) {
        this.dados = dados;
    }

    public String get(String key) {
        return dados.get(key);
    }

    public Map<String, String> getAll() {
        return dados;
    }
}
