<?php

namespace App\Http\Resources;

use Illuminate\Http\Resources\Json\JsonResource;

class OrderItemResource extends JsonResource
{
    public function toArray($request)
    {
        return [
            'orderId' => $this->orderId,
            'productId' => $this->productId,
            'quantity' => $this->quantity,
        ];
    }
}
