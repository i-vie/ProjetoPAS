<?php

namespace App\Http\Resources;

use Illuminate\Http\Resources\Json\JsonResource;

class ReservationResource extends JsonResource
{
    public function toArray($request)
    {
        return [
            'id' => $this->id,
            'employeeId' => $this->employeeId,
            'table_id' => $this->table_id,
            'dateReservation' => $this->dateReservation,
            'nrPeople' => $this->nrPeople,
            'comments' => $this->comments,
            'created_at' => $this->created_at,
        ];
    }
}

