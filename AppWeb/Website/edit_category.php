<?php

include_once "category.php";



if (isset($_GET['id']) && is_numeric($_GET['id'])) {
    $category_id = (int) $_GET['id'];

    $category = new Category($category_id, null, null);
    $category_data = $category->consult();

    if ($category_data) {
        $name = $category_data['name'];
        $active = $category_data['active'];
    } else {
        echo "Categoria não encontrada.";
        exit;
    }
} else {
    echo "ID da categoria não fornecido.";
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
        $page = 'menu'; //Nome da página atual
        include 'navbar_dashboard.php';
        ?>
        <main>
            <div class="title">
                <h1>Detalhes do produto</h1>
            </div>
            <div class="center">
                <div class="login-div">
                    <div class="form-container">

                        <!-- Formulário para editar produto -->
                        <form action="update_category.php" method="POST">
                            <h2>Alterar Categoria #<?php echo $category_id; ?></h2>
                            <input type="hidden" name="category_id" value="<?php echo $category_id; ?>">

                            <label for="name">Nome</label>
                            <input type="text" name="name" value="<?php echo $name; ?>" required><br><br>

                            <label for="active">Ativo</label>
                            <input type="checkbox" name="active" value="1" <?php echo $active ? 'checked' : ''; ?>><br><br>

                            <input type="submit" value="Guardar alterações"></input>
                        </form>
                    </div>
                </div>
            </div>

        </main>