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
        $employee = new Employee($_SESSION['user_id'], null, null, null, null, null, null);
        $employee_data = $employee->consult();

        if ($employee_data) {
            $name = $employee_data['name'];
            $username = $employee_data['username'];
            $email = $employee_data['email'];
            $admin = $employee_data['admin'];
        }
        ?>
        <main>
            <div class="title">
                <h1>Perfil</h1>
            </div>
            <div class="center">
            <div class="login-div">
                <?php if (isset($_GET['message']) && $_GET['message'] === 'success') {
                    echo '<p class="success-message">Palavra-passe alterada com sucesso!</p><br>';
                } ?>
                <div class="form-container">
                    <form action="update_employee.php" method="POST">
                        <h2><?php echo $name; ?></h2><br>
                        <label for="name">Name</label>
                        <input type="text" name="name" value="<?php echo $name; ?>"><br><br>
                        <label for="username">Name</label>
                        <input type="text" name="username" value="<?php echo $username; ?>"><br><br>
                        <label for="email">Email</label>
                        <input type="text" name="email" value="<?php echo $email; ?>"><br><br>
                        <input type="hidden" name="employee_id" value="<?php echo $employee_id; ?>">
                        <button type="button" class="button"><a href="change_password.php" class="ref-button">Alterar palavra-passe</button>
                        <input type="hidden" name="admin" value="<?php echo $admin ?>">
                        <input type="hidden" name="active" value="1">

                        <input type="submit" value="Guardar alterações"></input>
                    </form>
                </div>
            </div>
        </div>
        </main>

        
        

        <!-- Incluir o footer -->
        <?php include 'footer.php'; ?>

        <script src="codigo.js"></script>

    </div>


</body>

</html>