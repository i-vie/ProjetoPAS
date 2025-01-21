<!-- barra de navegação através de um modelo Bootstrap -->
<!-- o menu de navegação é exibido no topo, mas colapsa e passa a estar disponível através de um ícone no breakpoint xl -->

<nav class="navbar navbar-expand-xl">
<div class="container-fluid">
    <img src="imagens/logo_horizontal2.png" alt="logótipo" width="350" class="dark">
    <button class="navbar-toggler custom-toggler" type="button" data-bs-toggle="collapse"
        data-bs-target="#collapsibleNavbar">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="collapsibleNavbar">
        <ul class="navbar-nav justify-content-end">
            <li class="nav-item"><a href="index.php" class="<?= ($page == 'index') ? 'active' : '' ?> nav-link">Início</a></li>
            <li class="dropdown nav-item">
                <a href="javascript:void(0)" class="dropbtn">Gourmet Manager</a>
                <div class="dropdown-content">
                    <a href="features.php" class="<?= ($page == 'features') ? 'active' : '' ?> nav-link">Funcionalidades</a>
                    <a href="objectives.php" class="<?= ($page == 'objectives') ? 'active' : '' ?> nav-link">Objetivos</a>
                </div>
            </li>
            <li class="nav-item"><a href="audience.php" class="<?= ($page == 'audience') ? 'active' : '' ?> nav-link">Público-alvo</a></li>
            <li class="nav-item"><a href="contacts.php" class="<?= ($page == 'contacts') ? 'active' : '' ?> nav-link">Contactos</a></li>
            <li class="nav-item"><a href="about.php" class="<?= ($page == 'about') ? 'active' : '' ?> nav-link">Sobre nós</a></li>
            <li class="nav-item"><a href="login.php" class="nav-link">Login</a></li>
        </ul>
    </div>
</div>
</nav>

