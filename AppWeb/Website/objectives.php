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

    <!-- Incluir a barra de navegação com indicação da página atual -->
    <?php
        $page = 'objectives'; //Nome da página atual
        include 'navbar.php';
    ?>

    <main>
        <div class="features-container">
            <div class="row">
                <div class="col-lg-4 transparent">
                    <div class="title">
                        <h1>Objetivos</h1>
                        <br>
                    </div>
                    <div class="features-div">


                        <h3>Facilitar a Gestão de Pedidos</h3>
                        <p>Automatizar e simplificar o processo de criação, edição e acompanhamento de pedidos</p><br>

                        <h3>Otimizar a Gestão do Menu</h3>
                        <p>Permitir a adição e edição rápida de produtos e categorias, mantendo o menu sempre
                            atualizado.</p><br>

                        <h3>Centralizar a Gestão do Restaurante</h3>
                        <p>Integrar funcionalidades de gestão de funcionários e mesas em uma única plataforma.</p>
                        <br><br>

                    </div>
                </div>

                <div class="col-lg-8 transparent">
                    <br>
                    <img class="mockup" src="imagens/mockup3.png" alt="mockup">


                    <br>

                </div>
            </div>
        </div>
    </main>

    <!-- Incluir o footer -->
    <?php include 'footer.php';?>

    <script src="codigo.js"></script>


</body>

</html>