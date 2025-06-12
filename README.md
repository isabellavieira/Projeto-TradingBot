# Projeto Trading-bot

Isabella Vieira, Fabiano Amorim e Beatriz Vieira

## Objetivo do Projeto

Este projeto tem como objetivo desenvolver um **Trading Bot** que utiliza a API da Binance para realizar operações automáticas de compra e venda de criptomoedas. Além disso, o bot gerará relatórios de ganhos e perdas para monitorar o desempenho das transações realizadas.

---

## Tecnologias Utilizadas

- **API da Binance**: Para realizar operações de compra e venda de criptomoedas.
- **Azure SQL Database**: Para armazenamento de transações e relatórios de desempenho.
- **Azure App Service**: Para hospedagem do bot na nuvem.
- **Docker compose**: Para containerização do bot e suas dependências, garantindo portabilidade e escalabilidade entre diferentes ambientes 
- **Azure Container Registry** : Para armazenar as imagens Docker que são consumidas pelo Azure App Service for Containers.
---

## Funcionalidades Principais

- **Consulta de Preços de Moedas**: O bot será capaz de consultar preços em tempo real das criptomoedas na Binance para tomar decisões informadas sobre quando comprar ou vender.
  
- **Execução de Ordens de Compra e Venda**: O bot realizará ordens de compra e venda de criptomoedas com base nos sinais predefinidos.

- **Gerenciamento de Relatórios de Ganhos e Perdas**: O bot registrará e gerará relatórios detalhados sobre cada transação, incluindo lucros e perdas.

---

## Banco de Dados

- **Armazenamento de Transações**: Todas as transações realizadas (como moeda, quantidade, preço de compra, preço de venda, ganho/perda) serão armazenadas em um banco de dados SQL.

- **Armazenamento de Relatórios**: O sistema armazenará relatórios detalhados sobre a performance das transações ao longo do tempo.

- **Armazenamento de Imagem Docker**: A imagem Docker da aplicação será armazenada no Azure Container Registry, garantindo que as versões da imagem sejam gerenciadas de forma segura e eficiente, e permitindo que o Azure App Service for Containers consuma essa imagem para a execução do bot na nuvem.
---

## Hospedagem e Escalabilidade

- **Azure App Service**: O bot será hospedado na nuvem usando o Azure App Service, permitindo escalabilidade e manutenção simplificada. O código será configurado para rodar continuamente.

- **Escalabilidade**: A plataforma Azure permitirá que o bot opere sem interrupções, mesmo com aumento de carga.

- **Azure SQL Database**: Utilizado para armazenamento em nuvem de transações e usuários.

- **Azure Container Registry**: As imagens Docker do bot serão armazenadas no Azure Container Registry, permitindo um gerenciamento centralizado e seguro das imagens. 
---

## Regras do Projeto

- **Execução de Operações**: O bot deve realizar operações automaticamente com base nos sinais de trading predefinidos. O bot não pode realizar operações sem um sinal válido.
  
- **Relatórios de Ganhos e Perdas**: Cada transação será registrada, incluindo informações sobre a moeda, quantidade, preço de compra e venda, e o total de ganho ou perda. Relatórios periódicos serão gerados para monitorar a performance.

- **Implementação na Nuvem (Azure)**: O bot será hospedado no Azure App Service, que garantirá que o bot esteja em execução constante e conectado ao Azure SQL Database para armazenamento de transações e relatórios.

---

## Etapas para Desenvolvimento

### 1. Configuração do Ambiente de Desenvolvimento:

- Criar uma conta no Azure e configurar o **Azure App Service**.
- Criar um **servidor flexível de banco de dados SQL** no Azure.
- Configurar a conexão com a **API da Binance** para consulta de preços e execução de ordens.

### 2. Desenvolvimento do Bot:

- Implementar o **armazenamento de transações e relatórios** no **Azure SQL Database**.

### 3. Teste e Validação:

- Testar o bot no ambiente local e na nuvem para garantir que as operações de compra e venda estejam funcionando corretamente.
- Validar o cálculo de ganhos e perdas.

### 4. Deploy para o Azure:

- Fazer o deploy do código no **Azure App Service**, juntamente com o **GitHub Actions**.

### 5. Integração com Docker:

- Fazer o build da **imagem Docker**.
- Publicar a imagem no **Azure Container Registry**.
- Realizar o deploy da imagem para o **Azure App Service for Containers**.
- Pipeline com **GitHub Actions**.

### 6. Documentação e Apresentação:

- Documentar todas as funcionalidades implementadas e os processos de configuração.
- Apresentar o funcionamento do bot e os relatórios gerados.
