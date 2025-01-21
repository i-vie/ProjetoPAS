<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Order extends Model
{
    use HasFactory;

    protected $fillable = ['open', 'employeeId', 'table_id', 'date', 'total'];

    public function employee() {
        return $this->belongsTo(Employee::class, 'employeeId');
    }


}

