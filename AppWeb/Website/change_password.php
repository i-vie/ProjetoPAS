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
        $page = 'dashboard'; //Nome da página atual
        include 'navbar_dashboard.php';
        include_once 'employee.php';
        $employee_id = $_SESSION['user_id'];
        $employee = new Employee($employee_id, null, null, null, null, null, null);
        $passwordError = "";


        if ($_SERVER['REQUEST_METHOD'] == 'POST') {
            //variáveis de feedback
            $error = "";
            $success = "";
            $currentPassword = isset($_POST['current_password']) ? $_POST['current_password'] : null;
            $newPassword = $_POST['new_password'];
            $confirmPassword = $_POST['confirm_password'];

            if (empty($currentPassword) || empty($newPassword) || empty($confirmPassword)) {
                $error = "Todos os campos são obrigatórios!";
            } elseif ($newPassword !== $confirmPassword) {
                $error = "As palavras-passe não coincidem!";
            } elseif (strlen($newPassword) < 8) {
                $error = "A palavra-passe deve ter pelo menos 8 caracteres!";
            } else {



                $result = $employee->changePassword($currentPassword, $newPassword);

                if ($result === "password_updated_successfully") {
                    $success = "Senha alterada com sucesso!";
                    header("location: profile.php?message=success");
                } elseif ($result === "current_password_incorrect") {
                    $error = "A palavra-passe atual está incorreta.";
                } elseif ($result === "user_not_found") {
                    $error = "Utilizador não encontrado.";
                } else {
                    $error = "Falha ao alterar a palavra-passe.";
                }
            }
        }

        ?>
        <main>
            <div class="title">
                <h1>Alterar palavra-passe</h1>
            </div>
            <div class="center">
                <?php include_once "change_password_form.html";

                //feedback
                if (!empty($error)) {
                    echo "<p style='color: red;'>$error</p>";
                } elseif (!empty($success)) {
                    echo "<p style='color: green;'>$success</p>";
                }
                ?>
            </div>

        </main>


        <!-- Incluir o footer -->
        <?php include 'footer.php'; ?>

        <script src="codigo.js"></script>

    </div>


</body>

</html>