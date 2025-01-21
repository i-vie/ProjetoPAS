<?php

include_once "category.php";



if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $name = $_POST['name'];

    $category = new Category(null, $name, 1);
    $result = $category->create();

    if ($result) {
            $text = "A categoria $name foi adicionada com sucesso";
        }
        else {
            $text = "Erro: A categoria não foi adicionada.";
        }
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
        $page = 'menu'; //Nome da página atual
        include 'navbar_dashboard.php';
        ?>
        <main>
            <div class="title">
                <h1>Menu</h1>
            </div>
            <div class="center">
                <?php echo "<p>$text</p>";?>
            </div>
            <div class="center">
                <button type="button" class="button"><a href="menu.php" class="ref-button">Voltar</button>
            </div>

        </main>

        <!-- Incluir o footer -->
    <?php include 'footer.php';?>

<script src="codigo.js"></script>


</body>

</html>