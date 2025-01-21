<?php

include_once "employee.php";



if (isset($_GET['id']) && is_numeric($_GET['id'])) {
    $employee_id = (int) $_GET['id'];

    $employee = new Employee($employee_id, null, null, null, null, null, null);
    $employee_data = $employee->consult();

    if ($employee_data) {
        $name = $employee_data['name'];
        $email = $employee_data['email'];
        $admin = $employee_data['admin'];
    } else {
        echo "Funcionário não encontrado.";
        exit;
    }
} else {
    echo "ID do funcionário não fornecido.";
    exit;
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
    <div class="page-container">
        <?php
        $page = 'employees'; //Nome da página atual
        include 'navbar_dashboard.php';
        ?>
        <main>
            <div class="title">
                <h1>Consultar Funcionário</h1>
            </div>
            <div class="center">
                <div class="login-div">
                    <div class="form-container">

                        <!-- Formulário para editar produto -->
                        <form action="update_employee.php" method="POST">
                            <h2><?php echo $name; ?></h2><br>

                            <input type="hidden" name="employee_id" value="<?php echo $employee_id; ?>">

                            <p>Email: <?php echo $email; ?></p>

                            <label for="admin">Administrador</label>
                            <input type="checkbox" name="admin" value="<?php echo $admin;?>" <?php echo $admin ? 'checked' : ''; ?>><br><br>

                            <label for="active">Ativo</label>
                                  
                            <input <?php echo $_SESSION['user_id'] == $employee_id ? 'type="hidden"' : 'type="checkbox"';?> name="active" value="1" checked><br><br>

                            <input type="submit" value="Guardar alterações"></input>
                        </form>
                    </div>
                </div>
            </div>

        </main>