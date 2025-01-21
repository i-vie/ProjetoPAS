<?php

namespace App\Http\Requests;

use Illuminate\Foundation\Http\FormRequest;

class StoreReservationRequest extends FormRequest
{
    public function authorize()
    {
        return true;
    }

    public function rules()
    {
        return [
            'employeeId' => ['required', 'exists:employees,id'],
            'table_id' => ['required', 'integer'],
            'nrPeople' => ['required', 'integer', 'min:1'],
            'dateReservation' => ['required', 'date'],
            'comments' => ['string', 'nullable'],
        ];
    }
}

