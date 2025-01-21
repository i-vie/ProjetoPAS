<?php
session_start();
?>
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
                <li class="nav-item"><a href="dashboard.php" class="<?= ($page == 'dashboard') ? 'active' : '' ?> nav-link">Início</a></li>
                <li class="dropdown nav-item">
                    <a href="javascript:void(0)" class="dropbtn <?= ($page == 'menu') ? 'active' : '' ?>">Ementa</a>
                    <div class="dropdown-content dropdown-dashboard">
                        <a href="menu.php" class="nav-link">Ver ementa</a>
                        <a href="categories.php" class="nav-link">Categorias de produtos</a>
                        <a href="new_product.php" class="nav-link">Adicionar produtos</a>
                    </div>
                <li class="nav-item"><a href="orders.php" class="<?= ($page == 'orders') ? 'active' : '' ?> nav-link">Pedidos</a></li>
                <li class="nav-item"><a href="reservations.php" class="<?= ($page == 'reservations') ? 'active' : '' ?> nav-link">Reservas</a></li>
                <?php if (isset($_SESSION['admin']) && $_SESSION['admin']): ?>
                    <li class="nav-item"><a href="employees.php" class="<?= ($page == 'employees') ? 'active' : '' ?> nav-link">Funcionários</a></li>
                <?php endif; ?>
                <li class="dropdown nav-item">
                    <a href="javascript:void(0)" class="dropbtn">Conta</a>
                    <div class="dropdown-content dropdown-dashboard">
                        <a href="profile.php" class="<?= ($page == 'profile') ? 'active' : '' ?> nav-link">Perfil</a>
                        <a href="end_session.php" class="nav-link">Terminar sessão</a>
                    </div>
                </li>
            </ul>
        </div>
    </div>
</nav>