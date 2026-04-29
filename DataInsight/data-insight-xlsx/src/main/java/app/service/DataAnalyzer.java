package app.service;

import java.util.List;
import java.util.Map;

public class DataAnalyzer {

    public static double soma(List<Map<String, String>> dados, String atributo) {
        double soma = 0;

        for (Map<String, String> linha : dados) {
            try {
                soma += Double.parseDouble(linha.get(atributo));
            } catch (NumberFormatException e) {} catch (    Exception e) {}
        }

        return soma;
    }

    public static double media(List<Map<String, String>> dados, String atributo) {
        double soma = 0;
        int count = 0;

        for (Map<String, String> linha : dados) {
            try {
                soma += Double.parseDouble(linha.get(atributo));
                count++;
            } catch (NumberFormatException e) {} catch (Exception e) {}
        }

        return count == 0 ? 0 : soma / count;
    }

    public static double max(List<Map<String, String>> dados, String atributo) {
        double max = Double.MIN_VALUE;

        for (Map<String, String> linha : dados) {
            try {
                double valor = Double.parseDouble(linha.get(atributo));
                if (valor > max) max = valor;
            } catch (NumberFormatException e) {} catch (    Exception e) {}
        }

        return max;
    }

    public static double min(List<Map<String, String>> dados, String atributo) {
        double min = Double.MAX_VALUE;

        for (Map<String, String> linha : dados) {
            try {
                double valor = Double.parseDouble(linha.get(atributo));
                if (valor < min) min = valor;
            } catch (NumberFormatException e) {} catch (        Exception e) {}
        }

        return min;
    }
}