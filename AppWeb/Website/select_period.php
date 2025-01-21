<div class="contacts-container">
    <div class="row">
        <form method="POST">
            <label for="period">Selecione um período:</label>
            <select id="period" name="period" class="period" onchange="this.form.submit()">
                <option value="today" <?php if (isset($_POST['period']) && $_POST['period'] == 'today') echo 'selected'; ?>>Hoje</option>
                <option value="yesterday" <?php if (isset($_POST['period']) && $_POST['period'] == 'yesterday') echo 'selected'; ?>>Ontem</option>
                <option value="this_week" <?php if (isset($_POST['period']) && $_POST['period'] == 'this_week') echo 'selected'; ?>>Esta semana</option>
                <option value="this_month" <?php if (isset($_POST['period']) && $_POST['period'] == 'this_month') echo 'selected'; ?>>Este mês</option>
                <option value="this_year" <?php if (isset($_POST['period']) && $_POST['period'] == 'this_year') echo 'selected'; ?>>Este ano</option>
                <option value="choose_date" <?php if (isset($_POST['period']) && $_POST['period'] == 'choose_date') echo 'selected'; ?>>Escolher dia</option>
            </select>

            <?php
            if ($_SERVER['REQUEST_METHOD'] == 'POST' && isset($_POST['period']) && $_POST['period'] == "choose_date"):
                $today = date('Y-m-d'); ?>
                <input type="date" id="data_hora" name="data_hora"
                    value="<?php echo $_POST['data_hora'] ?? ''; ?>"
                    onchange="this.form.submit()">
            <?php endif; ?>
        </form>
        <br>
        <br>
    </div>
</div>

<?php
include "period.php"; //inclui a classe Period para lidar com periodos temporais

$start_period = new Period("today");
$date_range = $start_period->getDateRange();
if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $period = $_POST['period'];

    if ($period == "choose_date" && isset($_POST['data_hora'])) {
        $selectedDate = $_POST['data_hora'];
        $start = $selectedDate . ' 00:00:00';
        $end = $selectedDate . ' 23:59:59';
        $date_range = [$start, $end];
    } else if ($period != "choose_date") {
        try {
            $p = new Period($period);
            $date_range = $p->getDateRange();
        } catch (Exception $e) {
            exit;
        }
    }
}
?>