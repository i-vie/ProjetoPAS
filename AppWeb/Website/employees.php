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
        if (!isset($_SESSION['admin']) || !$_SESSION['admin']) {
            //se o utilizador não for administrador, vai ser redirecionado para o dashboard - apenas admin terão acesso a esta página
            header('Location: dashboard.php');
            exit;
        }
        include_once 'employee.php';
        ?>
        <main>
            <div class="title">
                <h1>Funcionários</h1>
            </div>
            <div class="container">
                <div class="center top-padding">
                    <?php
                    $page_nr = isset($_GET['page_nr']) ? (int) $_GET['page_nr'] : 1;
                    ?>
                    <table>
                        <thead>
                            <tr>
                                <th>ID do funcionário</th>
                                <th>Nome</th>
                                <th>E-mail</th>
                                <th>Administrador</th>
                            </tr>
                        </thead>
                        <tbody>
                            <?php
                            $employee = new Employee(null, null, null, null, null, null, null);
                            $employeeList = $employee->consult_all($page_nr);
                            foreach ($employeeList as $employeeData):
                                $currentEmployee = new Employee($employeeData['id'], $employeeData['name'], null, $employeeData['email'], null, $employeeData['admin'], '1');
                                if ($currentEmployee->admin == 1) {
                                    $isAdmin = "Sim";
                                } else {
                                    $isAdmin = "Não";
                                }
                                ?>
                            <tr>
                                <td><a href='edit_employee.php?id=<?php echo $currentEmployee->id; ?>'><?php echo htmlspecialchars($currentEmployee->id)?></a></td>
                                <td><?php echo $currentEmployee->name;?></td>
                                <td><?php echo $currentEmployee->email;?></td>
                                <td><?php echo $isAdmin;?></td>
                            </tr>
                            
                            <?php endforeach;?>

                        </tbody>

                    </table>
                </div>
                <div class="pagination center">
                    <?php
                    if ($page_nr > 1) {
                        echo "<a href='?page_nr=" . ($page_nr - 1) . "' class='enabled'>Anterior</a> ";
                    } else {
                        echo "<span class='disabled'>Anterior</span> ";
                    }
                    $total_employees = $employee->count_employees(); //número de pedidos total a ser apresentado
                    $total_pages = ceil($total_employees / 10); //10 resultados por página
                    if ($page_nr < $total_pages) {
                        echo "<a href='?page_nr=" . ($page_nr + 1) . "' class='enabled'>Próxima</a>";
                    } else {
                        echo "<span class='disabled'>Próxima</span>";
                    }
                    ?>
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