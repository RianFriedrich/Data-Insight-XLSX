# Data Insight XLSX
 
Aplicação desktop desenvolvida em **Java + JavaFX** para análise interativa de dados a partir de arquivos Excel (.xlsx).
 
O sistema permite transformar planilhas em **tabelas dinâmicas, métricas e dashboards visuais**, facilitando a exploração e interpretação de dados.
 
---
 
## ✨ Visão Geral
 
O Data Insight XLSX foi projetado para fornecer uma forma simples e eficiente de analisar dados sem depender de ferramentas complexas.
 
A aplicação permite:
 
- Importar arquivos Excel
- Visualizar dados em tabela dinâmica
- Filtrar e ordenar informações
- Identificar valores extremos
- Gerar métricas automaticamente
- Visualizar dados em gráficos interativos
---
 
## ⚙️ Funcionalidades
 
### 📊 Manipulação de Dados
- Leitura de arquivos `.xlsx` (Apache POI)
- Geração automática de colunas na tabela
- Ordenação inteligente (numérica e textual)
- Filtro dinâmico por atributo
### 🔎 Análise de Dados
- Identificação de maior valor
- Identificação de menor valor
- Destaque de linha selecionada
- Cálculo automático de:
  - Média
  - Soma
  - Mínimo
  - Máximo
  - Quantidade de registros
### 📈 Dashboard
- Exibição dinâmica de métricas
- Atualização automática ao selecionar coluna
- Integração com gráficos
### 📊 Visualização
- Gráfico de barras (BarChart)
- Atualização automática ao trocar atributo
- Tooltips com valores
- Interação com gráfico
### 📤 Exportação
- Exportação dos dados filtrados para CSV
---
 
## 🧠 Arquitetura do Projeto
 
O projeto foi estruturado seguindo separação de responsabilidades:
 
```
src/main/java/app/
├── controller/
│    └── MainController.java
│
├── model/
│    └── DataRow.java
│
├── service/
│    ├── ExcelReader.java
│    └── DataAnalyzer.java
│
├── ui/
│    ├── DashboardView.java
│    ├── MainView.java
│    └── TableViewComponent.java
│
└── MainApp.java
```
 
```
src/main/resources/
├── view/
│    └── main.fxml
└── style.css
```
 
### 🔹 Camadas
 
- **Controller** → Gerencia eventos da interface
- **Service** → Processamento de dados e regras de negócio
- **Model** → Estrutura dos dados
- **UI/View** → Componentes visuais
- **FXML** → Definição declarativa da interface
---
 
## 🛠️ Tecnologias
 
- Java 17
- JavaFX
- Maven
- Apache POI
---
 
## ▶️ Como Executar
 
### Pré-requisitos
 
- Java JDK 17+
- Maven instalado
### Passos
 
```bash
git clone https://github.com/RianFriedrich/data-insight-xlsx.git
cd data-insight-xlsx
mvn clean javafx:run
```
 
---
 
## 🧪 Como Usar
 
1. Clique em **"Carregar Excel"**
2. Selecione um arquivo `.xlsx`
3. Escolha uma coluna no seletor
4. Utilize as funcionalidades:
   - 🔍 Filtrar dados
   - ⬆️⬇️ Encontrar maior/menor valor
   - 📊 Visualizar dashboard
   - 📈 Analisar gráfico
   - 📤 Exportar CSV
---
 
## 🎯 Objetivo
 
Este projeto foi desenvolvido com foco em:
 
- Prática de JavaFX
- Manipulação de dados
- Visualização de informações
- Construção de portfólio profissional
---
 
## ⚠️ Observações
 
- Valores não numéricos são ignorados em cálculos
- Arquivos muito grandes podem impactar a performance
- A aplicação interpreta automaticamente tipos de dados
---
 
## 📄 Licença
 
Uso livre para fins educacionais e pessoais.
