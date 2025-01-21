<?php

namespace App\Http\Resources;

use Illuminate\Http\Resources\Json\JsonResource;

class OrderResource extends JsonResource
{
    public function toArray($request)
    {
        return [
            'id' => $this->id,
            'employeeId' => $this->employeeId,
            'table_id' => $this->table_id,
            'total' => $this->total,
            'open' => $this->open,
            'created_at' => $this->created_at,
            'updated_at' => $this->updated_at,
        ];
    }
}
