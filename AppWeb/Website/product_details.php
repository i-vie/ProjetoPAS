<?php

include_once "product.php";
include_once "category.php";



if (isset($_GET['id']) && is_numeric($_GET['id'])) {
    $product_id = (int) $_GET['id'];

    $product = new Product($product_id, null, null, null, null, null);
    $product_data = $product->consult();

    if ($product_data) {
        $name = $product_data['name'];
        $price = $product_data['price'];
        $description = $product_data['description'];
        $categoryId = $product_data['category_id'];
        $active = $product_data['active'];
    } else {
        echo "Produto não encontrado.";
        exit;
    }
} else {
    echo "ID do produto não fornecido.";
    exit;
}

$category = new Category(null, null, null);
$categories = $category->consult_active();
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
                        <form action="update_product.php" method="POST">
                            <h2>Alterar Produto #<?php echo $product_id; ?></h2>
                            <input type="hidden" name="product_id" value="<?php echo $product_id; ?>">

                            <label for="name">Nome</label>
                            <input type="text" name="name" value="<?php echo $name; ?>" required><br><br>

                            <label for="price">Preço</label>
                            <input type="number" name="price" value="<?php echo $price; ?>" step="0.01" min="0" required><br><br>


                            <label for="categoryId">Categoria</label>
                            <select name="categoryId" class="table-dropdown">
                                <?php foreach ($categories as $category): ?>
                                    <option value="<?php echo $category['id']; ?>" <?php echo $categoryId == $category['id'] ? 'selected' : ''; ?>>
                                        <?php echo $category['name']; ?>
                                    </option>
                                <?php endforeach; ?>
                            </select><br><br>


                            <label for="description">Descrição</label>
                            <textarea name="description" rows="4"><?php echo $description; ?></textarea><br><br>

                            <label for="active">Ativo</label>
                            <input type="checkbox" name="active" value="1" <?php echo $active ? 'checked' : ''; ?>><br><br>

                            <input type="submit" value="Guardar alterações"></input>
                        </form>
                    </div>
                </div>
            </div>

        </main>

        <!-- Incluir o footer -->
    <?php include 'footer.php';?>

<script src="codigo.js"></script>


</body>

</html>