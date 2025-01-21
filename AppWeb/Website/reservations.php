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
        $page = 'reservations'; //Nome da página atual
        include 'navbar_dashboard.php';
        ?>
        <main>
            <div class="title">
                <h1>Reservas</h1>
            </div>
            
            <?php
            include_once "select_period.php";
            include_once "reservation.php";
            $reservation = new Reservation(null, null, null, null, null, null, null, null);

            $stat_title_array = array(
                "Total de reservas",
                "Média de pessoas por reserva",
                "Horário mais reservado"
            );

            $total_reservations = $reservation->totalReservations($date_range);
            $avg_people = $reservation->avgNrPeople($date_range);
            $peak_hour = $reservation->peakHour($date_range);
            

            $stat_value_array = array(
                $total_reservations,
                $avg_people,
                $peak_hour
            );
            ?>

            <!-- Apresentação de estatísticas relevantes sobre reservas -->
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
                <div class="right-alignment top-padding">
                    <button type="button" class="button"><a href="new_reservation.php" class="ref-button">Adicionar nova reserva</a></button>
                </div>
                <!-- Apresentação de tabela de reservas para o periodo selecionado -->
                <div class="center top-padding">
                    <?php
                    $page_nr = isset($_GET['page_nr']) ? (int) $_GET['page_nr'] : 1;
                    ?>
                    <table>
                        <caption>Reservas efetuadas</caption>
                        <thead>
                            <tr>
                                <th>ID da reserva</th>
                                <th>Nº de pessoas</th>
                                <th>Mesa</th>
                                <th>Funcionário</th>
                                <th>Data da reserva</th>
                                <th>Data de criação</th>
                                <th></th>
                            </tr>
                        </thead>
                        <tbody>
                            <?php
                            $reservations = $reservation->consult_all($date_range, $page_nr);
                            foreach ($reservations as $row) {
                                $reservation = new Reservation($row['id'], $row['employeeId'], $row['table_id'], $row['dateReservation'], $row['nrPeople'], $row['comments'], $row['created_at'], $row['updated_at']);
                                echo "
                            <tr>
                                <td>{$reservation->id}</td>
                                <td>{$reservation->nrPeople}</td>
                                <td>{$reservation->tableId}</td>
                                <td>{$reservation->employeeId}</td>
                                <td>{$reservation->dateReservation}</td>
                                <td>{$reservation->dateCreated}</td>
                                <td><a href='reservation_details.php?id={$reservation->id}'>Ver e editar</a></td>
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
                    $total_reservations = $reservation->count_reservations($date_range); //número de reservas total a ser apresentado
                    $total_pages = ceil($total_reservations / 10); //10 resultados por página
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