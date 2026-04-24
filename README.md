# Data Insight XLSX

Uma aplicação desktop desenvolvida em Java com JavaFX que permite ler arquivos Excel (.xlsx), visualizar dados em formato de tabela, aplicar filtros, ordenações e gerar gráficos interativos a partir das informações carregadas.

---

## Sobre o Projeto

O Data Insight XLSX foi criado com o objetivo de transformar dados brutos de planilhas em informações visuais e manipuláveis de forma simples e intuitiva.

A aplicação permite que o usuário carregue um arquivo Excel e, automaticamente:

* Estruture os dados em uma tabela dinâmica
* Identifique atributos (colunas) e seus respectivos valores
* Navegue e analise os dados de forma interativa

---

## Funcionalidades

* Leitura de arquivos .xlsx
* Exibição automática dos dados em tabela
* Seleção de atributos (colunas)
* Filtro dinâmico por texto
* Ordenação automática (numérica e alfabética)
* Identificação de maior valor
* Identificação de menor valor
* Destaque visual da linha selecionada
* Geração de gráfico interativo (BarChart)
* Interação com gráfico (clique sincroniza com a tabela)
* Tooltip com valores ao passar o mouse

---

## Exemplo de Dados

O projeto já inclui um arquivo de exemplo:

dados.xlsx

Esse arquivo contém registros como:

* Nome
* Idade
* Altura
* Salário

Você pode utilizá-lo para testar todas as funcionalidades da aplicação imediatamente.

---

## Tecnologias Utilizadas

* Java 17
* JavaFX
* Maven
* Apache POI (leitura de Excel)

---

## Como Executar o Projeto

### Pré-requisitos

* Java JDK 17 instalado
* Maven configurado no PATH
* VS Code ou outra IDE Java (opcional)

### Passos

1. Clone o repositório:

```bash
git clone https://github.com/SEU-USUARIO/data-insight-xlsx.git
cd data-insight-xlsx
```

2. Execute o projeto:

```bash
mvn clean javafx:run
```

3. Clique em **"Carregar Excel"**
4. Selecione o arquivo `dados.xlsx`
5. Explore os dados

---

## Como Usar

* Use o seletor para escolher um atributo (coluna)
* Digite no campo de filtro para buscar valores
* Clique em "Maior" ou "Menor" para destacar registros extremos
* Clique em "Gerar Gráfico" para visualizar os dados
* Clique nas barras do gráfico para sincronizar com a tabela

---

## Estrutura do Projeto

```
src/
 └── main/
     ├── java/
     │   └── app/
     │       ├── App.java
     │       └── service/
     │           └── ExcelReader.java
     └── resources/
         └── style.css
```

---

## Possibilidades de Expansão

Este projeto possui grande potencial de evolução:

* Exportação de relatórios
* Suporte a múltiplas planilhas
* Gráficos adicionais (pizza, linha, etc.)
* Integração com banco de dados
* Dashboard completo
* Upload de arquivos via web (versão web futura)
* Análise estatística automática

---

## Objetivo

Este projeto também serve como base para:

* Portfólio profissional
* Estudos de JavaFX
* Manipulação de dados
* Visualização de dados
* Desenvolvimento de aplicações desktop

---

## Observações

* O sistema tenta interpretar automaticamente valores numéricos
* Campos inválidos são ignorados em cálculos
* Arquivos muito grandes podem impactar performance

---

## Autor

Desenvolvido como projeto de estudo e evolução em desenvolvimento de software.

---

## Licença

Este projeto é livre para uso educacional e pessoal.
