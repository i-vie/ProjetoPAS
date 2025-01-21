<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Reservation extends Model
{
    use HasFactory;

    protected $fillable = ['employeeId', 'table_id', 'dateCreated', 'dateReservation', 'nrPeople', 'comments'];

    public function employee() {
        return $this->belongsTo(Employee::class, 'employeeId');
    }

}

