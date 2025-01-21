<?php

class Employee {
    public $id;
    public $name;
    public $username;
    public $email;
    public $password;
    public $admin;
    public $active;
    public $conn;

    public const Time = 1800;

    public function __construct($id,$name,$username,$email,$password,$admin,$active) {
        $this->id = $id;
        $this->name = $name;
        $this->username = $username;
        $this->email = $email;
        $this->password = $password;
        $this->admin = $admin;
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

    //verifica se já existe username ou email introduzido na base de dados
    public function checkDuplicate() {
        $this->connectDB();
        $duplicate = ['username' => false, 'email' => false];

        //duplicidade do username
        $sql_username = "SELECT id FROM employees WHERE username='$this->username'";
        $result_username = $this->conn->query($sql_username);
        if ($result_username->num_rows > 0) {
            $duplicate['username'] = true;
        }

        //duplicidade do email
        $sql_email = "SELECT id FROM employees WHERE email='$this->email'";
        $result_email = $this->conn->query($sql_email);
        if ($result_email->num_rows > 0) {
            $duplicate['email'] = true;
        }

        $this->disconnectDB();
        return $duplicate;
    }

    public function create() {
        //insere novo utilizador, após verificar duplicidade de email e username
        $this->connectDB();
        $sql = "INSERT INTO employees (name, username, email, password, admin, active) 
                VALUES ('$this->name', '$this->username', '$this->email', '$this->password', '$this->admin', '$this->active')";
        $result = $this->conn->query($sql);
        $this->disconnectDB();
        return $result;
    }

    //consultar todos os funcionários activos (sem devolver a password)
    public function consult_all($page_nr) {
        // o resultado é um conjunto de registos da tabela
        $results_per_page = 10; //número de resultados por página
        $offset = ($page_nr - 1) * $results_per_page;
        $this->connectDB();
        $sql = "SELECT id, name, email, admin FROM employees WHERE active='1' LIMIT $results_per_page OFFSET $offset";
        $result = $this->conn->query($sql);
        $this->disconnectDB();
        $employees = [];


        if ($result && $result->num_rows > 0) {
            while ($row = $result->fetch_assoc()) {
                $employees[] = $row; 
            }
        }

        return $employees;
    }

    public function consult() {
        // o resultado é um registo da tabela
        $this->connectDB();
        $sql = "SELECT * FROM employees WHERE id=$this->id";
        $result = $this->conn->query($sql);
        $this->disconnectDB();
        return $result->fetch_assoc();;
    }

    public function update() {
        // o resultado é um booleano
        $this->connectDB();
        $sql = "UPDATE employees SET ";
        $virgula = false;
        if ($this->name != null) {
            $sql .= "name='$this->name' ";
        }
        if ($this->username != null) {
            if ($virgula) {
                $sql .= ",";
            }
            $sql .= "username='$this->username' ";
            $virgula = true;
        }

        if ($this->email != null) {
            if ($virgula) {
                $sql .= ",";
            }
            $sql .= "email='$this->email' ";
            $virgula = true;
        }

        if ($this->password != null) {
            if ($virgula) {
                $sql .= ",";
            }
            $sql .= "password='$this->password' ";
            $virgula = true;
        }

        if ($this->admin !== null) {
            if ($virgula) {
                $sql .= ",";
            }
            $sql .= "admin='$this->admin' ";
            $virgula = true;
        }

        if ($this->active !== null) {
            if ($virgula) {
                $sql .= ",";
            }
            $sql .= "active='$this->active' ";
        }
        $sql .= "WHERE id='$this->id'";


        $result = $this->conn->query($sql);
        $this->disconnectDB();
        return $result;
    }

    public function delete() {
        // o resultado é um booleano
        $this->connectDB();
        $sql = "DELETE FROM employees WHERE id=$this->id";
        $result = $this->conn->query($sql);
        $this->disconnectDB();
        return $result;
    }

    // verifica se existe o par utilizador password

    public function login() {
        $this->connectDB();
        
    
        //selecionar o utilizador com base no username
        $sql = "SELECT * FROM employees WHERE username='$this->username'";
        $result = $this->conn->query($sql);
        
        if ($result->num_rows == 0) {
            $this->disconnectDB();
            return "username_not_found"; //utilizador não encontrado
        } else {
            $user = $result->fetch_assoc();
            
            // password_verify usado para comparar a password com o hash na bd
            if (password_verify($this->password, $user['password'])) {
                $this->id = $user['id'];
                $this->name = $user['name'];
                $this->admin = $user['admin'];
                $this->active = $user['active'];
                $this->disconnectDB();
                return "login_success"; // login bem-sucedido
            }
        

                if ($this->password == $user['password']) {
                    $this->id = $user['id'];
                    $this->name = $user['name'];
                    $this->admin = $user['admin'];
                    $this->active = $user['active'];
                    $this->disconnectDB();
                    return "login_success"; // login bem-sucedido
                }
        }
        
        $this->disconnectDB();
        return "password_incorrect"; // senha incorreta
    }
    

    // cria uma sessão com uma duração de 30m
    // cria valores para $_SESSION['name'] e $_SESSION ['expire']

    public function create_session() {
        session_start();
        $_SESSION['user_id'] = $this->id;
        $_SESSION['name'] = $this->name;
        $_SESSION['admin'] = $this->admin;
        $_SESSION['expires'] = time() + self::Time;
    }

    // verifica se exite uma sessão e se está ativa (retorna true)
    // se não existir ou se estiver expirada retorna false
    public function verify_session() {
        if(!empty($_SESSION)) {
            if(time() < $_SESSION['expires']) {
                return true;
            }
        }
        return false;
    }

    // se existir uma sessão: remove todas as variáveis da sessão e elimina a sessão
    public function end_session() {
        if(!empty($_SESSION)) {
            session_unset();
            session_destroy();
        }
    }

    public function count_employees() {
        $this->connectDB();
        $sql = "SELECT COUNT(*) AS total_employees FROM employees WHERE active='1'";
        $result = $this->conn->query($sql);
        $this->disconnectDB();
        $row = $result->fetch_assoc();
        return $row['total_employees'];
    }

    public function changePassword($currentPassword, $newPassword) {
        $this->connectDB();
        
        $sql = "SELECT password FROM employees WHERE id='$this->id'";
        $result = $this->conn->query($sql);
    
        if ($result->num_rows == 0) {
            $this->disconnectDB();
            return "user_not_found";
        }
    
        $user = $result->fetch_assoc();
        
        if (!password_verify($currentPassword, $user['password'])) {
            $this->disconnectDB();
            return "current_password_incorrect";
        }
    
        $newPasswordHash = password_hash($newPassword, PASSWORD_DEFAULT);
    
        $updateSql = "UPDATE employees SET password='$newPasswordHash' WHERE id='$this->id'";
        if ($this->conn->query($updateSql) === TRUE) {
            $this->disconnectDB();
            return "password_updated_successfully";
        } else {
            $this->disconnectDB();
            return "password_update_failed";
        }
    }
    
}
