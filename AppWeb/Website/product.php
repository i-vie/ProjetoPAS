<?php

class Product {
    public $id;
    public $name;
    public $price;
    public $description;
    public $category_id;
    public $active;

    private $conn;

    public function __construct($id = null, $name = null, $price = null, $description = null, $category_id = null, $active = true) {
        $this->id = $id;
        $this->name = $name;
        $this->price = $price;
        $this->description = $description;
        $this->category_id = $category_id;
        $this->active = $active;
    }



    public function connectDB() {
        $servername = "localhost";
        $username = "twdm";
        $password = "password";
        $dbname = "gourmet_manager";

        //cria a ligação à base de dados
        $conn = new mysqli($servername, $username, $password, $dbname);

        //verifica a ligação
        if ($conn->connect_error) {
            die("Connection failed: " . $conn->connect_error);
        }
        $this->conn = $conn;
    }

    public function disconnectDB() {
        $this->conn->close();
    }

    public function create() {
        //insere novo produto
        $this->connectDB();
        $sql = "INSERT INTO products (name, price, description, category_id) 
                VALUES ('$this->name', '$this->price', '$this->description', '$this->category_id')";
        $result = $this->conn->query($sql);
        $this->disconnectDB();
        return $result;
    }

    public function consult_all() {
        // o resultado é um conjunto de registos da tabela
        $this->connectDB();
        $sql = "SELECT * FROM products";
        $result = $this->conn->query($sql);
        $this->disconnectDB();
        return $result;
    }

    public function consult() {
        // o resultado é um registo da tabela
        $this->connectDB();
        $sql = "SELECT * FROM products WHERE id=$this->id";
        $result = $this->conn->query($sql);
        $this->disconnectDB();
        return $result->fetch_assoc();        
    }

    public function consult_products_by_category($categoryId) {
        $this->connectDB();
        $sql = "SELECT * FROM products WHERE category_id=$categoryId AND active=true";
        $result = $this->conn->query($sql);
        $this->disconnectDB();
        $products = [];


        if ($result && $result->num_rows > 0) {
            while ($row = $result->fetch_assoc()) {
                $products[] = $row; 
            }
        }

        return $products;
    }

    public function update() {
        // o resultado é um booleano
        $this->connectDB();
        $sql = "UPDATE products 
                SET name='$this->name', price='$this->price', description='$this->description', category_id='$this->category_id', active='$this->active' 
                WHERE id='$this->id'";
        $result = $this->conn->query($sql);
        $this->disconnectDB();
        return $result;
    }

    public function delete() {
        // o resultado é um booleano
        $this->connectDB();
        $sql = "DELETE FROM products WHERE id=$this->id";
        $result = $this->conn->query($sql);
        $this->disconnectDB();
        return $result;
    }

}