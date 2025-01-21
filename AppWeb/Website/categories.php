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
                <h1>Categorias de produtos</h1>
            </div>
            <?php include_once "category.php"; ?>
            <div class="container">
                <div class="right-alignment">
                    <button type="button" class="button"><a href="new_category.php" class="ref-button">Adicionar nova categoria</button>
                </div>
                <div class="center top-padding">
                    <table>
                        <thead>
                            <tr>
                                <th>ID da categoria</th>
                                <th>Nome</th>
                                <th>Nº de produtos</th>
                                <th>Ativa</th>
                            </tr>
                        </thead>
                        <tbody>
                            <?php
                            $category = new Category(null, null, null);
                            $categoryList = $category->consult_all();
                            foreach ($categoryList as $categoryData):
                                $currentCategory = new Category($categoryData['id'], $categoryData['name'], $categoryData['active']);
                                if ($currentCategory->active) {
                                    $isActive = "Sim";
                                } else {
                                    $isActive = "Não";
                                }
                            ?>
                                <tr>
                                    <td><a href='edit_category.php?id=<?php echo $currentCategory->id; ?>'><?php echo htmlspecialchars($currentCategory->id) ?></a></td>
                                    <td><?php echo $currentCategory->name ?></td>
                                    <td><?php echo $currentCategory->totalProductsPerCategory() ?></td>
                                    <td><?php echo $isActive ?></td>
                                </tr>
                            <?php endforeach; ?>
                        </tbody>

                    </table>
                </div>
            </div>



        </main>

        <!-- Incluir o footer -->
        <?php include 'footer.php'; ?>

        <script src="codigo.js"></script>

    </div>


</body>

</html>