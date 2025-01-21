<?php


include_once "employee.php";

$usernameError = "";
$passwordError = "";

if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $username = $_POST['username'];
    $hashed_password = ($_POST['password']);


    $user = new Employee(null, null, $username, null, $hashed_password, null, null);

    $loginStatus = $user->login();

    if ($loginStatus === "username_not_found") {
        $usernameError = "O nome de utilizador não existe.";
    } elseif ($loginStatus === "password_incorrect") {
        $passwordError = "A palavra-passe está incorreta.";
    } elseif ($loginStatus === "login_success") {
        $user->create_session();
        header("Location: dashboard.php");
        exit();
    }
} else {
    header("index.php");
}

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

    <!-- Incluir a barra de navegação com indicação da página atual -->
    <?php
    $page = 'login'; //Nome da página atual
    include 'navbar.php';
    ?>

    <main>
        <div class="center">
            <div class="login-div">

                <?php
                if (isset($_GET['message']) && $_GET['message'] === 'success') {
                    echo '<p class="success-message">Registo bem-sucedido! Já pode iniciar sessão com a sua conta.</p><br>';
                }
                include_once "login_form.html" ?>
            </div>
        </div>
        <?php

        if (!empty($usernameError)) {
            echo "<p id='username-error' class='login-error'>$usernameError</p>";
        } elseif (!empty($passwordError)) {
            echo "<p id='password-error' class='login-error'>$passwordError</p>";
        }
        ?>



    </main>

    <!-- Incluir o footer -->
    <?php include 'footer.php'; ?>

    <script src="codigo.js"></script>

</body>

</html>