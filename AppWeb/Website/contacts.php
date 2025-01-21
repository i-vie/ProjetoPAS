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
        $page = 'contacts'; //Nome da página atual
        include 'navbar.php';
    ?>

    <main>
        <div class="title">
            <h1>Contactos</h1>
        </div>

        <div class="contacts-container">
            <div class="row">
                <div class="col-lg-6">
                    <h3>Formulário de contacto</h3><br>
                    <div class="form-div">
                        <form>
                            <label for="fname">Primeiro nome:</label><br>
                            <input type="text" id="fname" name="fname"><br><br>
                            <label for="lname">Último nome:</label><br>
                            <input type="text" id="lname" name="lname"><br><br>
                            <label for="email">Email:</label><br>
                            <input type="text" id="email" name="email"><br><br>
                            <label for="phone">Contacto telefónico:</label><br>
                            <input type="text" id="phone" name="phone"><br><br>
                            <label for="message">Mensagem:</label><br>
                            <textarea name="message" id="message" rows="14" cols="10" wrap="soft" maxlength="500" class="message"
                                style="overflow:hidden; resize:none"></textarea><br><br>
                            <input type="submit" value="Enviar"><br>
                        </form>
                    </div>
                </div>

                <div class="col-lg-6">
                    <h3>Fale conosco</h3><br>
                    <br>
                    <p>Também pode entrar em contacto conosco por email. Envie-nos um email para <a
                            href="mailto:geral@gourmetmanager.pt" class="ext-link">geral@gourmetmanager.pt</a></p><br>
                </div>

            </div>
        </div>
        <br>
        <br>
    </main>


    <!-- Incluir o footer -->
    <?php include 'footer.php';?>

    <script src="codigo.js"></script>


</body>

</html>