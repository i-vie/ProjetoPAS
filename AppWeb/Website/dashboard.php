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
        ?>
        <main>
            <div class="title">
                <h1>Resumo</h1>
            </div>

            <?php
            include_once "select_period.php";
            include_once "order.php";
            include_once "reservation.php";
            $order = new Order(null, null, null, null, null, null);
            $reservation = new Reservation(null, null, null, null, null, null, null, null);

            $stat_title_array = array(
                "Total de pedidos",
                "Total faturado",
                "Total de reservas"
            );

            $total_orders = $order->totalOrders($date_range);
            $total_revenue = "€ " . $order->getTotalRevenue($date_range);
            $total_reservations = $reservation->totalReservations($date_range);

            $stat_value_array = array(
                $total_orders,
                $total_revenue,
                $total_reservations
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
                    }
                    ?>
                </div>
                <br>
            </div>
        </main>

        <!-- Incluir o footer -->
        <?php include 'footer.php'; ?>

        <script src="codigo.js"></script>

    </div>


</body>

</html>