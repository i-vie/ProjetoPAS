<?php

Class Period {
    public $period;


    public function __construct($period) {
        $this->period = $period; 
    }

    //função para obter o intervalo de datas para determinado periodo temporal
    public function getDateRange() {
        switch ($this->period) {
            case 'today':
                $start = date('Y-m-d 00:00:00');
                $end = date('Y-m-d 23:59:59');
                break;
            case 'yesterday':
                $start = date('Y-m-d 00:00:00', strtotime('-1 day'));
                $end = date('Y-m-d 23:59:59', strtotime('-1 day'));
                break;
            case 'this_week':
                $start = date('Y-m-d 00:00:00', strtotime('monday this week'));
                $end = date('Y-m-d 23:59:59', strtotime('sunday this week'));
                break;
            case 'this_month':
                $start = date('Y-m-01 00:00:00');
                $end = date('Y-m-t 23:59:59');
                break;
            case 'this_year':
                $start = date('Y-01-01 00:00:00');
                $end = date('Y-12-31 23:59:59');
                break;
            default:
                throw new Exception('Período inválido');
        }
        return [$start, $end];
    }
}