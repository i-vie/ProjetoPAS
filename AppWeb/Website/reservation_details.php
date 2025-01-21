<?php

include_once "reservation.php";



if (isset($_GET['id']) && is_numeric($_GET['id'])) {
    $reservation_id = (int) $_GET['id'];

    $reservation = new Reservation($reservation_id, null, null, null, null, null, null, null);
    $reservation_data = $reservation->consult();

    if ($reservation_data) {
        $employeeId = $reservation_data['employeeId'];
        $tableId = $reservation_data['table_id'];
        $nrPeople = $reservation_data['nrPeople'];
        $dateReservation = $reservation_data['dateReservation'];
        $comments = $reservation_data['comments'];
    } else {
        echo "Reserva não encontrada.";
        exit;
    }
} else {
    echo "ID da reserva não fornecido.";
    exit;
}

$is_past_reservation = strtotime($dateReservation) < time();
$tables = $reservation->get_tables();
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
        $page = 'reservations'; //Nome da página atual
        include 'navbar_dashboard.php';
        ?>
        <main>
            <div class="title">
                <h1>Detalhes da reserva</h1>
            </div>
            <div class="center">
            <div class="login-div">
            <div class="form-container">
                <!-- Formulário para editar reserva -->
                <form action="update_reservation.php" method="POST">
                    <h2>Editar Reserva #<?php echo $reservation_id; ?></h2>
                    <input type="hidden" name="reservation_id" value="<?php echo $reservation_id; ?>">

                    <label for="employeeId">Funcionário:</label>
                    <input type="text" name="employeeId" value="<?php echo $employeeId; ?>" disabled><br><br>

                    <label for="tableId">Mesa:</label>
                    <select name="tableId" class="table-dropdown" <?php echo $is_past_reservation ? 'disabled' : ''; ?>>
                                <?php foreach ($tables as $table): ?>
                                    <option value="<?php echo $table['id']; ?>" <?php echo $tableId == $table['id'] ? 'selected' : ''; ?>>
                                        <?php echo $table['id']; ?>
                                    </option>
                                <?php endforeach; ?>
                            </select><br><br>

                    <label for="nrPeople">Nº de Pessoas:</label>
                    <input type="number" name="nrPeople" value="<?php echo $nrPeople; ?>" <?php echo $is_past_reservation ? 'disabled' : ''; ?> required><br><br>

                    <label for="dateReservation">Data da Reserva:</label>
                    <input type="datetime-local" name="dateReservation" value="<?php echo date('Y-m-d\TH:i', strtotime($dateReservation)); ?>" <?php echo $is_past_reservation ? 'disabled' : ''; ?> required><br><br>

                    <label for="comments">Comentários:</label>
                    <textarea name="comments" rows="4"><?php echo $comments; ?> <?php echo $is_past_reservation ? 'disabled' : ''; ?></textarea><br><br>

                    <input type="submit" value="Guardar alterações"></input>
                </form>
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