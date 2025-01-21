<?php

class Reservation {
    public $id;
    public $employeeId;
    public $tableId;
    public $dateReservation;
    public $nrPeople;
    public $comments;
    public $dateCreated;
    public $dateUpdated;
	


    private $conn;

    public function __construct($id = null, $employeeId = null, $tableId = null, $dateReservation = null, $nrPeople = null, $comments = null, $dateCreated = null, $dateUpdated) {
        $this->id = $id;
        $this->employeeId = $employeeId;
        $this->tableId = $tableId;
        $this->dateReservation = $dateReservation;
        $this->nrPeople = $nrPeople;
        $this->comments = $comments;
        $this->dateCreated = $dateCreated;
        $this->dateUpdated = $dateUpdated;
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
        //insere nova reserva
        $this->connectDB();
        $sql = "INSERT INTO reservations (employeeId, table_id, dateReservation, nrPeople, comments) 
                VALUES ('$this->employeeId', '$this->tableId', '$this->dateReservation', '$this->nrPeople', '$this->comments')";
        $result = $this->conn->query($sql);
        $this->disconnectDB();
        return $result;
    }

    public function consult_all($date_range, $page_nr) {
        // o resultado é um array de resultados
        $this->connectDB();
        list($start, $end) = $date_range;
        $results_per_page = 10; //número de resultados por página
        $offset = ($page_nr - 1) * $results_per_page;
        $sql = "SELECT * FROM reservations WHERE dateReservation BETWEEN '$start' AND '$end' LIMIT $results_per_page OFFSET $offset";
        $result = $this->conn->query($sql);
        $this->disconnectDB();
        $reservations = [];


        if ($result && $result->num_rows > 0) {
            while ($row = $result->fetch_assoc()) {
                $reservations[] = $row; 
            }
        }

        return $reservations;
    }

    public function consult() {
        // o resultado é um registo da tabela
        $this->connectDB();
        $sql = "SELECT * FROM reservations WHERE id=$this->id";
        $result = $this->conn->query($sql);
        $this->disconnectDB();
        if ($result && $result->num_rows > 0) {
            return $result->fetch_assoc();
        } else {
            return false; 
        }
    }

    public function update() {
        $this->connectDB();
        $sql = "UPDATE reservations
                SET table_id = '$this->tableId', nrPeople = '$this->nrPeople', 
                dateReservation = '$this->dateReservation', comments = '$this->comments' 
                WHERE id='$this->id'";

        $result = $this->conn->query($sql);
        $this->disconnectDB();
        return $result;
    }

    //total de pedidos em determinado periodo temporal
    public function totalReservations($date_range) {
        list($start, $end) = $date_range;
        $this->connectDB();
        $sql = "SELECT COUNT(*) AS total FROM reservations WHERE dateReservation BETWEEN '$start' AND '$end'";
        $result = $this->conn->query($sql);
        $this->disconnectDB();
    
        $row = $result->fetch_assoc();
        return $row['total'];
    }
    

    public function avgNrPeople($date_range) {
        list($start, $end) = $date_range;
        $this->connectDB();
        $sql = "SELECT 
                AVG(nrPeople) AS average_people
            FROM 
                reservations
            WHERE 
                dateReservation BETWEEN '$start' AND '$end'
        ";
        $result = $this->conn->query($sql);
        $this->disconnectDB();
        
        if ($result && $row = $result->fetch_assoc()) {
            return round($row['average_people'], 2); // Arredonda para 2 casas decimais
        }
        return 0;
        
        
    }
    
    
    
    public function peakHour($date_range) {
        $this->connectDB();
        list($start, $end) = $date_range;
        
        $sql = "SELECT 
                HOUR(dateReservation) as hour, 
                COUNT(*) as total_reservations 
            FROM 
                reservations 
            WHERE 
                dateReservation BETWEEN '$start' AND '$end'
            GROUP BY 
                HOUR(dateReservation)
            ORDER BY 
                total_reservations DESC
            LIMIT 1";
        $result = $this->conn->query($sql);
        $this->disconnectDB();
        $total = 0;
        if ($result->num_rows > 0) {
            $row = $result->fetch_assoc();
            return $row['hour'] . "h";
        }
        return "-";
    }

    public function count_reservations($date_range) {
        list($start, $end) = $date_range;
        $this->connectDB();
        $sql = "SELECT COUNT(*) AS total_reservations FROM reservations WHERE created_at BETWEEN '$start' AND '$end'";
        $result = $this->conn->query($sql);
        $this->disconnectDB();
        $row = $result->fetch_assoc();
        return $row['total_reservations'];
    }

    public function get_tables() {
        $this->connectDB();
        $sql = "SELECT * FROM tables";
        $result = $this->conn->query($sql);
        $this->disconnectDB();
    
        $tables = [];
        if ($result && $result->num_rows > 0) {
            while ($row = $result->fetch_assoc()) {
                $tables[] = $row;
            }
        }
        return $tables;
    }
    
}