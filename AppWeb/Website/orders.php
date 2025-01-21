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
        $page = 'orders'; //Nome da página atual
        include 'navbar_dashboard.php';
        ?>
        <main>
            <div class="title">
                <h1>Pedidos</h1>
            </div>
            
            <?php
            include_once "select_period.php";
            include_once "order.php";
            include_once "product.php";
            include_once "category.php";
            $order = new Order(null, null, null, null, null, null, null);

            $stat_title_array = array(
                "Total de pedidos",
                "Pedidos abertos",
                "Pedidos faturados",
                "Produto mais pedido",
                "Categoria mais pedida",
                "Total faturado"
            );

            $total_orders = $order->totalOrders($date_range);
            $total_open_orders = $order->openOrders($date_range);
            $total_closed_orders = $order->closedOrders($date_range);
            $most_ordered_product_id = $order->mostOrderedProduct($date_range);
            if ($most_ordered_product_id == null) {
                $most_ordered_product = "-";
                $most_ordered_category = "-";
            } else {
                $product = new Product($most_ordered_product_id, null, null, null, null, null);
                $result_row = $product->consult();
                $most_ordered_product = $result_row['name'];
                $category = new Category($result_row['category_id'], null, null);
                $result_row = $category->consult();
                $most_ordered_category = $result_row['name'];
            }
            $total_revenue = "€ " . $order->getTotalRevenue($date_range);

            $stat_value_array = array(
                $total_orders,
                $total_open_orders,
                $total_closed_orders,
                $most_ordered_product,
                $most_ordered_category,
                $total_revenue
            );
            ?>

            <!-- Apresentação de estatísticas relevantes sobre pedidos -->
            <div class="container">
                <div class="row justify-content-around">
                    <?php
                    for ($i = 0; $i <= 2; $i++) {
                        $stat_title = $stat_title_array[$i];
                        $stat_value = $stat_value_array[$i];
                        include 'stat_card.php';
                    } ?>
                </div>
                <br>
                <div class="row justify-content-around">
                    <?php
                    for ($i = 3; $i <= 5; $i++) {
                        $stat_title = $stat_title_array[$i];
                        $stat_value = $stat_value_array[$i];
                        include 'stat_card.php';
                    } ?>
                </div>
                <br>
                <div class="center top-padding">
                    <?php
                    $page_nr = isset($_GET['page_nr']) ? (int) $_GET['page_nr'] : 1;
                    ?>
                    <table>
                        <caption>Pedidos efetuados</caption>
                        <thead>
                            <tr>
                                <th>ID do pedido</th>
                                <th>Mesa</th>
                                <th>Total</th>
                                <th>Funcionário</th>
                                <th>Faturado</th>
                                <th>Data de criação</th>
                            </tr>
                        </thead>
                        <tbody>
                            <?php
                            $orderList = $order->consult_all($date_range, $page_nr);
                            foreach ($orderList as $orderData) {
                                $currentOrder = new Order($orderData['id'], $orderData['open'], $orderData['employeeId'], $orderData['table_id'], $orderData['created_at'], $orderData['updated_at']);
                                if ($currentOrder->open == 1) {
                                    $isClosed = "Não";
                                } else {
                                    $isClosed = "Sim";
                                }
                                $orderTotal = $currentOrder->getTotal();
                                echo "
                            <tr>
                                <td>{$currentOrder->id}</td>
                                <td>{$currentOrder->tableId}</td>
                                <td>{$orderTotal}</td>
                                <td>{$currentOrder->employeeId}</td>
                                <td>$isClosed</td>
                                <td>{$currentOrder->dateCreated}</td>
                            </tr>";
                            }
                            ?>
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
                    $total_orders = $order->count_orders($date_range); //número de pedidos total a ser apresentado
                    $total_pages = ceil($total_orders / 10); //10 resultados por página
                    if ($page_nr < $total_pages) {
                        echo "<a href='?page_nr=" . ($page_nr + 1) . "' class='enabled'>Próxima</a>";
                    } else {
                        echo "<span class='disabled'>Próxima</span>";
                    }
                    ?>
                </div>
            </div>



        </main>

        <!-- Incluir o footer -->
        <?php include 'footer.php'; ?>

        <script src="codigo.js"></script>

    </div>


</body>

</html>