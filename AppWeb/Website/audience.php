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
        $page = 'audience'; //Nome da página atual
        include 'navbar.php';
    ?>


    <main>
        <div class="title">
            <h1>Público-alvo</h1>
            <br>
        </div>
        <div class="features-div">

            <p class = "audience">Os utilizadores da nossa aplicação serão funcionários e gerentes de um restaurante. Isto
                inclui empregados de mesa e bar, chefes de sala, chefes de cozinha, gerentes de restaurante,
                entre outros. Estes utilizadores poderão ter níveis distintos de formação e familiaridade
                com sistemas informáticos, podendo ir de um nível intermédio/avançado (por exemplo, um
                gerente ou diretor do restaurante) a um nível mais baixo (por exemplo, um empregado de
                mesa, que não necessita de ter conhecimentos específicos de gestão do restaurante).
                </p><br>
            <h3>Personas</h3><br>
            <p>Apresentamos, em seguida, duas personas criadas com base em potenciais utilizadores
                da nossa aplicação.</p>

        </div>

        <div class="card-container">
            <div class="card">
                <div class="description">
                    <br><br>
                    <h3>Leonor Fernandes, gerente de restaurante - 45 anos
                        </h3>
                    <br>
                    <p class="audience">Tem uma licenciatura em Gestão
                        de Unidades de Restauração e Catering e formação na área de sistemas de informática
                        aplicados à restauração e à gestão. Utiliza diariamente ferramentas de apoio à gestão
                        na área da restauração e sente-se bastante à vontade com tecnologia relacionada com a
                        sua área profissional. É responsável pela gestão do restaurante, incluindo, por exemplo, a
                        área financeira, gestão de stocks, análise de desempenho ou a administração da aplicação
                        de gestão de pedidos e reservas. Precisa de consultar as estatísticas diárias relativas a
                        pedidos, de uma forma fácil de gerir as contas dos funcionários do restaurante na aplicação
                        e de poder consultar a ementa e os pedidos realizados, de forma a garantir que tudo está
                        em conformidade.</p>
                </div>
            </div>
        </div>

        <div class="card-container">
            <div class="card">
                <div class="description">
                    <br><br>
                    <h3>Rafael Lopes, empregado de mesa - 22 anos
                        </h3>
                    <br>
                    <p class="audience"> Não tem formação relevante na
                        área da informática e os seus conhecimentos de tecnologia são básicos. Está familiarizado
                        com smartphones e aplicações de uso comum. Para realizar o seu trabalho de uma forma
                        mais eficiente, precisa de consultar o menu e criar e gerir pedidos de rápida e facilmente</p>
                </div>
            </div>
        </div>
    </main>



    <!-- Incluir o footer -->
    <?php include 'footer.php';?>

    <script src="codigo.js"></script>


</body>

</html>