<?php

include_once "employee.php";

if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    
    $active = isset($_POST['active']) ? 1 : 0;
    $admin = isset($_POST['admin']) ? 1 : 0;
    $name = isset($_POST['name']) ? $_POST['name'] : null;
    $username = isset($_POST['username']) ? $_POST['username'] : null;
    $email = isset($_POST['email']) ? $_POST['email'] : null;
    $password = isset($_POST['password']) ? $_POST['password'] : null;
    $id = (int) $_POST['employee_id'];
    

    // Atualizar o produto no banco de dados
    $employee = new Employee($id,$name,$username,$email,$password,$admin,$active);
    $update_result = $employee->update();
?>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" contents="width=device-width, initial scale=1.0">
    <title>Gourmet Manager</title>
    <link rel="stylesheet" href="estilos.css">
    <link rel="stylesheet" href="https://use.typekit.net/opk3aod.css">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link
        href="https://fonts.googleapis.com/css2?family=Poppins:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,100;1,200;1,300;1,400;1,500;1,600;1,700;1,800;1,900&display=swap"
        rel="stylesheet">
    <link
        href="https://fonts.googleapis.com/css2?family=Poppins:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,100;1,200;1,300;1,400;1,500;1,600;1,700;1,800;1,900&display=swap"
        rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>

</head>

<body>
    <div class="page-container">
        <?php
        $page = 'employees'; //Nome da página atual
        include 'navbar_dashboard.php';
        if ($update_result) {
            $text = "Dados do funcionário atualizados com sucesso!";
            if (isset($_SESSION['user_id']) && $_SESSION['user_id'] == $id) {
                $_SESSION['admin'] = $admin;
            }
        } else {
            $text = "Erro ao atualizar os dados do funcionário.";
        }
    } else {
        $text = "Dados do formulário não enviados.";
    }
        ?>
        <main>
            <div class="title">
                <h1>Menu</h1>
            </div>
            <div class="center">
                <?php echo "<p>$text</p>";?>
            </div>
            <div class="center">
                <button type="button" class="button"><a href="employees.php" class="ref-button">Voltar</button>
            </div>

        </main>
        <!-- Incluir o footer -->
        <?php include 'footer.php'; ?>

        <script src="codigo.js"></script>
    </div>
</body>

</html>