<?php

include_once "reservation.php";

if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    $id = (int) $_POST['reservation_id'];
    $tableId = (int) $_POST['tableId'];
    $nrPeople = (int) $_POST['nrPeople'];
    $dateReservation = $_POST['dateReservation'];
    $comments = $_POST['comments'];

    // Atualizar a reserva no banco de dados
    $reservation = new Reservation($id,null,$tableId,$dateReservation,$nrPeople,$comments,null,null);
    $update_result = $reservation->update();

    if ($update_result) {
        $text = "Reserva atualizada com sucesso!";
    } else {
        $text = "Erro ao atualizar a reserva.";
    }
} else {
    $text = "Dados do formulário não enviados.";
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
        $page = 'reservations'; //Nome da página atual
        include 'navbar_dashboard.php';
        ?>
        <main>
            <div class="title">
                <h1>Reservas</h1>
            </div>
            <div class="center">
                <?php echo "<p>$text</p>";?>
            </div>
            <div class="center">
                <button type="button" class="button"><a href="reservations.php" class="ref-button">Voltar</button>
            </div>

        </main>
        <!-- Incluir o footer -->
        <?php include 'footer.php'; ?>

        <script src="codigo.js"></script>
    </div>
</body>

</html>