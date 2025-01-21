<?php
include_once "employee.php";




if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    //variáveis de feedback
    $error = "";
    $success = "";
    $usernameError = "";
    $emailError = "";

    //dados do formulário
    $name = htmlspecialchars(trim($_POST['name']));
    $username = htmlspecialchars(trim($_POST['username']));
    $email = htmlspecialchars(trim($_POST['email']));
    $password = $_POST['password'];
    $confirm_password = $_POST['confirm_password'];

    //validação
    if (empty($username) || empty($email) || empty($password) || empty($confirm_password)) {
        $error = "Todos os campos são obrigatórios!";
    } elseif ($password !== $confirm_password) {
        $error = "As palavras-passe não coincidem!";
    } elseif (strlen($password) < 8) {
        $error = "A palavra-passe deve ter pelo menos 8 caracteres!";
    } else {
        $hashed_password = password_hash($password, PASSWORD_BCRYPT);

        $user = new Employee(null, $name, $username, $email, $hashed_password, 0, 1);

        //verificar duplicidade
        $duplicate = $user->checkDuplicate();

        if ($duplicate['username']) {
            $usernameError = "O nome de utilizador já está em uso. Escolha um nome de utilizador diferente.";
        } elseif ($duplicate['email']) {
            $emailError = "O email já está em uso. Por favor, confirme o e-mail ou inicie sessão.";
        } else {
            $result = $user->create();
            if ($result) {
                header("location: login.php?message=success");
            } else {
                echo "<p style='color: red;'>Erro ao registar o utilizador</p>";
                header("location: index.php");
            }
        }
    }

    //feedback
    if (!empty($error)) {
        echo "<p style='color: red;'>$error</p>";
    } elseif (!empty($success)) {
        echo "<p style='color: green;'>$success</p>";
    }
}
?>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gourmet Manager - Registro</title>
    <link rel="stylesheet" href="estilos.css">
    <link rel="stylesheet" href="https://use.typekit.net/opk3aod.css">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link
        href="https://fonts.googleapis.com/css2?family=Poppins:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900&display=swap"
        rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</head>

<body>
    <!-- Incluir a barra de navegação com indicação da página atual -->
    <?php
        $page = 'register'; // Nome da página atual
        include 'navbar.php';
    ?>

    <main>
        <div class="contacts-container">
            <div class="row">
                <div class="col-lg-6">
                    <?php include_once "register_form.html"?>         
                </div>
                <div class="col-lg-6 center">
                    <p>Já tem uma conta? <a href="login.php" class="ext-link">Faça login aqui.</a></p>
                </div>
            </div>
        </div>
        

                   
    </main>

    <!-- Incluir o footer -->
    <?php include 'footer.php';?>

    <script src="codigo.js"></script>
</body>

</html>


