# **Gourmet Manager**

O Gourmet Manager permite que funcionários de restaurantes possam gerir pedidos, reservas, ementa e os respetivos produtos através da aplicação móvel.
Os utilizadores podem também, através do website, criar e gerir a sua conta, consultar pedidos, assim como gerir reservas e a ementa e consultar estatísticas relevantes sobre pedidos, reservas e produtos pedidos.

---

## **Sumário**

1. [Funcionalidades](#funcionalidades)
2. [Tecnologias Utilizadas](#tecnologias-utilizadas)
3. [Instalação e Execução](#instalacao-e-execucao)
4. [Base de dados](#base-dados)
5. [API (Laravel)](#api-laravel)
6. [Aplicação Móvel](#aplicacao-movel)
7. [Website](#website)

---

## **Funcionalidades**

### **API (Laravel)**

- **Autenticação**: O utilizador pode fazer o login.
- **Gestão de Reservas**: Permite a criação, leitura, atualização e exclusão de reservas.
- **Gestão de pedidos**:  Permite a criação, leitura, atualização e exclusão de pedidos.
- **Gestão de produtos e categorias**:  Permite a criação, leitura, atualização e exclusão de produtos e categorias do menu.

### **Aplicação Móvel**

- **Login de utilizador**: Funcionalidade para entrar no sistema.
- **Criação e gestão de Reservas**: Os funcionários podem criar, consultar, editar e eliminar reservas.
- **Criação e gestão de Pedidos**: Os funcionários podem criar, consultar, editar e eliminar reservas.
- **Criação e gestão da Ementa**: Os funcionários podem criar, consultar, editar e eliminar produtos e categorias do menu.
- **Seleção de tema escuro/claro**
- **Seleção de idioma: Português/Inglês**

### **Website**

- **Estatísticas sobre reservas e pedidos**: Exibição de estatísticas relevantes.
- **Tabelas de pedidos e reservas**: Exibição de tabelas de pedidos e reservas por data selecionada
- **Gestão de funcionários**: Funcionalidade para gerir os funcionários (apenas para funcionários administradores)
- **Criação e gestão de conta de utilizador**: Permite criar conta e geri-la.
- **Menu**: Exibição do menu e possibilidade de adicionar/alterar produtos e categorias.
- **Reservas**: Criação e alteração de reservas.

---

## **Tecnologias Utilizadas**

### **Aplicação Móvel**

- **Kotlin**
- **Jetpack Compose** 
- **Retrofit**

### **API (Laravel)**

- **PHP** (com o framework Laravel).
- **MySQL** (base de dados).

### **Website**

- **HTML, CSS, JavaScript** (para a estrutura básica).
- **Bootstrap** (para o design responsivo).
- **PHP**

---

## **Instalação e Execução**

### **Backend (Laravel)**

1. Clone o repositório:
   ```bash
   git clone https://github.com/i-vie/ProjetoPAS

2. Instale as dependências:
   ```bash
   composer install
3. Execute as migrations do base de dados:
   ```bash
   php artisan migrate
4. Inicie o servidor local:
   ```bash
   php artisan serve

### **Aplicação Móvel** ###

1. Abra o projeto no Android Studio.
2. Execute o projeto.
   
### **Website** ###

1. Instale o XAMPP
2. Coloque os arquivos na pasta pública do servidor.

---

## **Base de dados**

A estrutura da base de dados utilizada foi semelhante à usada para os projetos anteriores e pode ser visualizada abaixo:

![Captura de ecrã 2025-01-21 232757](https://github.com/user-attachments/assets/0b470aa7-31bd-4213-9944-5c9b3aabcb4d)

Optou-se por utilizar o RoomDB para armazenar apenas os dados dos funcionários que efetuam login, já que os dados dos funcionários são principalmente informações que não necessitam de ser partilhadas em tempo real entre vários utilizadores. A utilização do Room permite que esses dados sejam facilmente acessíveis de forma local, proporcionando uma experiência de login mais rápida e eficiente.
No entanto, para dados sobre pedidos, produtos e reservas, optou-se por não guardar estes dados localmente, tendo em conta que precisam de ser constantemente atualizados e sincronizados em tempo real. Por exemplo, quando um funcionário regista um pedido ou uma reserva, essa informação deve ser imediatamente refletida para todos os utilizadores que estão conectados à aplicação e ao website.

---

## **API (Laravel)**

## 1. **Autenticação**

### `POST /login`
- **Descrição**: Efetua o login de um funcionário.

---

## 2. **Mesas**

### `GET /tables`
- **Descrição**: Obtém a lista de todas as mesas.

### `POST /tables`
- **Descrição**: Cria uma nova mesa.

### `GET /tables/{id}`
- **Descrição**: Obtém os detalhes de uma mesa específica.

### `PUT /tables/{id}`
- **Descrição**: Atualiza os detalhes de uma mesa específica.

### `DELETE /tables/{id}`
- **Descrição**: Exclui uma mesa específica.

---

## 3. **Categorias de Produtos**

### `GET /categories`
- **Descrição**: Obtém a lista de todas as categorias.

### `POST /categories`
- **Descrição**: Cria uma nova categoria.

### `GET /categories/{id}`
- **Descrição**: Obtém os detalhes de uma categoria específica.

### `PUT /categories/{category}`
- **Descrição**: Atualiza uma categoria específica.

### `DELETE /categories/{category}`
- **Descrição**: Exclui uma categoria específica.

---

## 4. **Funcionários**

### `GET /employees`
- **Descrição**: Obtém a lista de todos os funcionários.

### `POST /employees`
- **Descrição**: Cria um novo funcionário.

### `GET /employees/{id}`
- **Descrição**: Obtém os detalhes de um funcionário específico.

### `PUT /employees/{employee}`
- **Descrição**: Atualiza os dados de um funcionário específico.

### `DELETE /employees/{employee}`
- **Descrição**: Exclui um funcionário específico.

---

## 5. **Produtos**

### `GET /products`
- **Descrição**: Obtém a lista de todos os produtos.

### `POST /products`
- **Descrição**: Cria um novo produto.

### `GET /products/{id}`
- **Descrição**: Obtém os detalhes de um produto específico.

### `PUT /products/{product}`
- **Descrição**: Atualiza os dados de um produto específico.

### `DELETE /products/{product}`
- **Descrição**: Exclui um produto específico.

### `GET /products/category/{categoryId}`
- **Descrição**: Obtém todos os produtos de uma categoria específica.

### `GET /products/status/{status}`
- **Descrição**: Obtém todos os produtos com um determinado status (ativo ou não).

---

## 6. **Itens de Pedido**

### `GET /orderItems`
- **Descrição**: Obtém a lista de todos os itens de pedido.

### `POST /orderItems`
- **Descrição**: Cria um novo item de pedido.

### `GET /orderItems/{orderId}/{productId}`
- **Descrição**: Obtém os detalhes de um item de pedido específico.

### `PUT /orderItems/{orderItem}`
- **Descrição**: Atualiza os dados de um item de pedido específico.

### `DELETE /orderItems/{orderId}/{productId}`
- **Descrição**: Exclui um item de pedido específico.

### `GET /orderItems/order/{orderId}`
- **Descrição**: Obtém todos os itens associados a um pedido específico.

---

## 7. **Pedidos**

### `GET /orders`
- **Descrição**: Obtém a lista de todos os pedidos.

### `POST /orders`
- **Descrição**: Cria um novo pedido.

### `GET /orders/{id}`
- **Descrição**: Obtém os detalhes de um pedido específico.

### `PUT /orders/{order}`
- **Descrição**: Atualiza os dados de um pedido específico.

### `DELETE /orders/{order}`
- **Descrição**: Exclui um pedido específico.

### `GET /orders/employee/{employeeId}`
- **Descrição**: Obtém os pedidos associados a um funcionário específico.

### `GET /orders/status/{status}`
- **Descrição**: Obtém todos os pedidos com um determinado status (aberta/faturado).

### `GET /orders/by-day/{day}`
- **Descrição**: Obtém todos os pedidos feitos em um dia específico.

### `GET /orders/total/by-status/{status}`
- **Descrição**: Obtém o total de pedidos com um determinado status.

### `GET /orders/total-amount/{status}`
- **Descrição**: Obtém o total de valor dos pedidos com um determinado status.

### `GET /orders/total/by-day/{day}`
- **Descrição**: Obtém o total de pedidos feitos em um dia específico.

---

## 8. **Reservas**

### `GET /reservations`
- **Descrição**: Obtém a lista de todas as reservas.

### `POST /reservations`
- **Descrição**: Cria uma nova reserva.

### `GET /reservations/{id}`
- **Descrição**: Obtém os detalhes de uma reserva específica.

### `PUT /reservations/{reservation}`
- **Descrição**: Atualiza os dados de uma reserva específica.

### `DELETE /reservations/{reservation}`
- **Descrição**: Exclui uma reserva específica.

### `GET /reservations/by-day/{day}`
- **Descrição**: Obtém todas as reservas feitas em um dia específico.

### `GET /reservations/total/by-day/{day}`
- **Descrição**: Obtém o total de reservas feitas em um dia específico.

### `GET /reservations/created/{day}`
- **Descrição**: Obtém todas as reservas criadas em um dia específico.

### `GET /reservations/total/created/{day}`
- **Descrição**: Obtém o total de reservas criadas em um dia específico.

## **Aplicação Móvel**

### Fluxo de Navegação

- **Login**: O utilizador faz login com suas credenciais.
- **Início**: Estatísticas relevantes
- **Pedidos**: Lista de pedidos; é possível selecionar pedidos para visualizar/alterar e criar pedidos.
- **Reservas**: Lista de reservas; é possível selecionar reservas para visualizar/alterar e criar novas reservas.
- **Menu**: Visualização/gestão de categorias e produtos pertencentes às mesmas.

![Screenshot_20250121_235521](https://github.com/user-attachments/assets/8c1e0020-d9c9-4990-b813-15aece7cc42f)
![Screenshot_20250121_235456](https://github.com/user-attachments/assets/604977e8-8de4-404e-ae75-738333737dc5)
![Screenshot_20250121_235440](https://github.com/user-attachments/assets/798bf2b0-6c40-4b3a-88c3-aa1664221179)
![Screenshot_20250121_235421](https://github.com/user-attachments/assets/49161ed1-9503-4a92-8382-f0e2a00b275c)

## **Website**

### Fluxo de Navegação após login (área reservada)

-- **Dashboard**: Resumo com algumas estatísticas gerais
- **Ementa**: Ver ementa/Adicionar ou alterar categorias/Adicionar produtos
- **Pedidos**: Tabela de pedidos por período e estatísticas relevantes
- **Reservas**: Tabela de reservas por período e estatísticas relevantes
- **Funcionários**: Apenas para administrador - consulta de dados de funcionários e alteração do estado de administrador
- **Conta** : Perfil do funcionário com sessão iniciada e opção de terminar sessão

![Captura de ecrã 2025-01-21 232353](https://github.com/user-attachments/assets/006ce0cb-38e2-4dee-aa44-d518c75de779)
