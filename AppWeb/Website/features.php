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

    <?php
        $page = 'features'; //Nome da página atual
        include 'navbar.php';
    ?>

    <main>
        <div class="features-container">
            <div class="row">
                <div class="col-lg-7 transparent">
                    <div class="title">
                        <h1>Funcionalidades</h1>
                        <br>
                    </div>
                    <div class="features-div">


                        <h3>Pedidos</h3>
                        <p>Crie e consulte pedidos rapidamente</p><br>

                        <h3>Ementa e produtos</h3>
                        <p>Adicione, consulte e altere os novos pratos e produtos à ementa de uma forma fácil</p><br>

                        <h3>Utilizadores</h3>
                        <p>Adicione os seus funcionários</p>
                        <p>Perfis diferenciados para cada função</p><br>
                    </div>

                </div>

                <div class="col-lg-5 transparent">
                    <br>
                    <video width="393" height="786" controls>
                        <source src="vídeo/screencast.mp4" type="video/mp4">
            
                      </video>
                    
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