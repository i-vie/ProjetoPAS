<?php

class Category {
    public $id;
    public $name;
    public $active;

    private $conn;


    public function __construct($id,$name,$active) {
        $this->id = $id;
        $this->name = $name;
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
        //insere nova categoria
        $this->connectDB();
        $sql = "INSERT INTO categories (name, active) 
                VALUES ('$this->name', '$this->active')";
        $result = $this->conn->query($sql);
        $this->disconnectDB();
        return $result;
    }

    public function consult_all() {
        // o resultado é um conjunto de registos da tabela
        $this->connectDB();
        $sql = "SELECT * FROM categories";
        $result = $this->conn->query($sql);
        $this->disconnectDB();
        $categories = [];


        if ($result && $result->num_rows > 0) {
            while ($row = $result->fetch_assoc()) {
                $categories[] = $row; 
            }
        }

        return $categories;
    }

    public function consult_active() {
        // o resultado é um conjunto de registos da tabela
        $this->connectDB();
        $sql = "SELECT * FROM categories WHERE active=true";
        $result = $this->conn->query($sql);
        $this->disconnectDB();
        $categories = [];


        if ($result && $result->num_rows > 0) {
            while ($row = $result->fetch_assoc()) {
                $categories[] = $row; 
            }
        }

        return $categories;
    }

    public function consult() {
        // o resultado é um registo da tabela
        $this->connectDB();
        $sql = "SELECT * FROM categories WHERE id=$this->id";
        $result = $this->conn->query($sql);
        $this->disconnectDB();
        return $result->fetch_assoc();
    }

    public function update() {
        // o resultado é um booleano
        $this->connectDB();
        $sql = "UPDATE categories SET name='$this->name', active='$this->active'  WHERE id='$this->id'";
        $result = $this->conn->query($sql);
        $this->disconnectDB();
        return $result;
    }

    public function delete() {
        // o resultado é um booleano
        $this->connectDB();
        $sql = "DELETE FROM categories WHERE id=$this->id";
        $result = $this->conn->query($sql);
        $this->disconnectDB();
        return $result;
    }

    //total de produtos ativos de uma categoria
    public function totalProductsPerCategory() {
        $this->connectDB();
        $sql = "SELECT COUNT(*) AS total_products FROM products WHERE category_id='$this->id' AND active='1'";
        $result = $this->conn->query($sql);
        $this->disconnectDB();
        $row = $result->fetch_assoc();
        return $row['total_products'];
    }

}