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
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>

</head>

<body>


    <!-- Incluir a barra de navegação com indicação da página atual -->
    <?php
        $page = 'index'; //Nome da página atual
        include 'navbar.php';
    ?>




    <main>
        <div class="slideshow-container">
            <div class="slideshow">
                <img src="imagens/imagem4.jpg" alt="SlideShow" id="slide" width="100%">
                <div class="text-block">
                    <br>
                    <h2>Gestão de pedidos</h2>
                    <br>
                    <p>Crie, modifique ou remova pedidos rapidamente</p>
                    <br>
                </div>
            </div>

            <div class="slideshow">
                <img src="imagens/imagem2.jpg" alt="SlideShow" id="slide" width="100%">
                <div class="text-block">
                    <br>
                    <h2>Gestão de reservas</h2>
                    <br>
                    <p>Faça uma gestão das reservas do seu restaurante de forma fácil e eficaz</p>
                    <br>
                </div>
            </div>

            <div class="slideshow">
                <img src="imagens/imagem5.jpg" alt="SlideShow" id="slide" width="100%">
                <div class="text-block">
                    <br>
                    <h2>Gestão de ementa e produtos</h2>
                    <br>
                    <p>Visualize todos os produtos da ementa e adicione as novas criações com facilidade</p>
                    <br>
                </div>
            </div>

            <div class="slideshow">
                <img src="imagens/imagem1.jpg" alt="SlideShow" id="slide" width="100%">
                <div class="text-block">
                    <br>
                    <h2>Relatórios</h2>
                    <br>
                    <p>Consulte os relatórios diários com informação sobre pedidos, produtos, reservas e faturação</p>
                    <br>
                </div>
            </div>

            <div>
                <a class="prev" onclick="plusSlides(-1)">&#10094;</a>
                <a class="next" onclick="plusSlides(1)">&#10095;</a>
            </div>
            <br>

            <div style="text-align:center">
                <span class="dot" onclick="currentSlide(1)"></span>
                <span class="dot" onclick="currentSlide(2)"></span>
                <span class="dot" onclick="currentSlide(3)"></span>
                <span class="dot" onclick="currentSlide(4)"></span>
            </div>
        </div>

        <div class="card-container">
            <div class="card">
                <div class="title">
                    <h1>Gourmet Manager</h1>
                </div>
                <div class="description">
                    <h3>Uma solução completa para o seu restaurante</h3>
                    <br>
                    <p>Oferecemos uma forma simples e completa para a gestão de pedidos, de reservas e dos produtos do
                        seu restaurante.</p>
                    <br>
                    <p class="learn-more">
                        <a href="features.html" class="learn-btn" role="button">Sabe mais</a>
                    </p>
                </div>
            </div>
        </div>

    </main>

    <!-- Incluir o footer -->
    <?php include 'footer.php';?>

    <script src="codigo.js"></script>


</body>

</html>