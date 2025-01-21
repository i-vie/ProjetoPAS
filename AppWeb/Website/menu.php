<?php
include_once "category.php";
include_once "product.php";

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
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
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
                <h1>Ementa</h1>
            </div>
            <?php
            foreach ($categories as $categoryData):
                $currentCategory = new Category($categoryData['id'], $categoryData['name'], $categoryData['active']);
            ?>
                <div class="category top-padding">
                    <h2>
                        <?php echo htmlspecialchars($currentCategory->name); ?>
                        </a>
                    </h2>
                    <div class="product">
                        <?php
                        $product = new Product(null, null, null, null, null, null);
                        $products = $product->consult_products_by_category($currentCategory->id);
                        foreach ($products as $productData):
                            $currentProduct = new Product($productData['id'], $productData['name'], $productData['price'], $productData['description'], $productData['category_id'], $productData['active']);
                        ?>
                            <table class="product-table">
                                <tbody>
                                    <tr>
                                        <td class="product-table" style="width:70%"><?php echo htmlspecialchars($currentProduct->name); ?></td>
                                        <td class="product-table" style="width:20%">€<?php echo number_format($currentProduct->price, 2, ',', '.'); ?></td>
                                        <td class="product-table" style="width:10%">
                                            <a href="product_details.php?id=<?php echo $currentProduct->id; ?>">
                                                <i class="fa fa-edit"></i>
                                            </a>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="product-table product-description" colspan="3"><?php echo htmlspecialchars($currentProduct->description); ?></td>
                                    </tr>
                                </tbody>
                            </table>


                        <?php endforeach; ?>
                    </div>


                </div>
            <?php endforeach; ?>
        </main>

        <!-- Incluir o footer -->
        <?php include 'footer.php'; ?>

        <script src="codigo.js"></script>

    </div>


</body>

</html>