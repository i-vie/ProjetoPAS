<?php

class Order
{
    public $id;
    public $open;
    public $employeeId;
    public $tableId;
    public $dateCreated;
    public $dateUpdated;

    private $conn;

    public function __construct($id = null, $open = null, $employeeId = null, $tableId = null, $dateCreated = null, $dateUpdated)
    {
        $this->id = $id;
        $this->open = $open;
        $this->employeeId = $employeeId;
        $this->tableId = $tableId;
        $this->dateCreated = $dateCreated;
        $this->dateUpdated = $dateUpdated;
    }



    public function connectDB()
    {
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

    public function disconnectDB()
    {
        $this->conn->close();
    }

    public function consult_all($date_range, $page_nr)
    {
        // o resultado é um array de resultados
        list($start, $end) = $date_range;
        $results_per_page = 10; //número de resultados por página
        $offset = ($page_nr - 1) * $results_per_page;
        $this->connectDB();
        $sql = "SELECT * FROM orders WHERE created_at BETWEEN '$start' AND '$end' LIMIT $results_per_page OFFSET $offset";
        $result = $this->conn->query($sql);
        $this->disconnectDB();
        $orders = [];


        if ($result && $result->num_rows > 0) {
            while ($row = $result->fetch_assoc()) {
                $orders[] = $row; 
            }
        }

        return $orders;
    }

    

    //total de pedidos em determinado periodo temporal
    public function totalOrders($date_range)
    {
        list($start, $end) = $date_range;
        $this->connectDB();
        $sql = "SELECT COUNT(*) AS total FROM orders WHERE created_at BETWEEN '$start' AND '$end'";
        $result = $this->conn->query($sql);
        $this->disconnectDB();

        $row = $result->fetch_assoc();
        return $row['total'];
    }

    //total de pedidos abertos para determinado periodo
    public function openOrders($date_range)
    {
        list($start, $end) = $date_range;
        $this->connectDB();
        $sql = "SELECT COUNT(*) AS open_orders FROM orders WHERE open = 1 AND created_at BETWEEN '$start' AND '$end'";
        $result = $this->conn->query($sql);
        $this->disconnectDB();

        $row = $result->fetch_assoc();
        return $row['open_orders'];
    }

    //total de pedidos fechados para determinado periodo
    public function closedOrders($date_range)
    {
        list($start, $end) = $date_range;
        $this->connectDB();
        $sql = "SELECT COUNT(*) AS closed_orders FROM orders WHERE open = 0 AND created_at BETWEEN '$start' AND '$end'";
        $result = $this->conn->query($sql);
        $this->disconnectDB();

        $row = $result->fetch_assoc();
        return $row['closed_orders'];
    }

    public function mostOrderedProduct($date_range)
    {
        list($start, $end) = $date_range;
        $this->connectDB();
        $sql = "SELECT productId, SUM(quantity) AS total_quantity 
                FROM order_items 
                WHERE orderId IN (SELECT id FROM orders WHERE created_at BETWEEN '$start' AND '$end') 
                GROUP BY productId 
                ORDER BY total_quantity DESC 
                LIMIT 1";
        $result = $this->conn->query($sql);
        $this->disconnectDB();

        if ($result && $result->num_rows > 0) {
            $row = $result->fetch_assoc();
            return $row['productId'];
        } else {
            return null; //devolve null se não houver registos
        }
    }



    public function getTotalRevenue($date_range)
    {
        $this->connectDB();
        list($start, $end) = $date_range;

        $sql = "SELECT SUM(oi.quantity * p.price) AS total_faturado
                FROM order_items oi
                JOIN products p ON oi.productId = p.id JOIN orders o ON oi.orderId = o.id
                      WHERE o.open = '0' AND o.created_at BETWEEN '$start' AND '$end'";
        $result = $this->conn->query($sql);
        $this->disconnectDB();
        $total = 0;
        if ($result->num_rows > 0) {
            $total += round($result->fetch_assoc()['total_faturado'], 2);
            return $total;
        }
    }

    public function getTotal()
    {
        $this->connectDB();

        $sql = "SELECT total
                FROM orders
                WHERE id = $this->id";
        $result = $this->conn->query($sql);
        $this->disconnectDB();
        $total = 0;
        if ($result->num_rows > 0) {
            $total += round($result->fetch_assoc()['total'], 2);
            return $total;
        }
    }

    public function count_orders($date_range) {
        list($start, $end) = $date_range;
        $this->connectDB();
        $sql = "SELECT COUNT(*) AS total_orders FROM orders WHERE created_at BETWEEN '$start' AND '$end'";
        $result = $this->conn->query($sql);
        $this->disconnectDB();
        $row = $result->fetch_assoc();
        return $row['total_orders'];
    }
    
}
