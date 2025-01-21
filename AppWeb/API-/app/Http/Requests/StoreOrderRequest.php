<?php

namespace App\Http\Requests;

use Illuminate\Foundation\Http\FormRequest;

class StoreOrderRequest extends FormRequest
{
    public function authorize()
    {
        
        return true;
    }

    public function rules()
    {
        return [
            'open' => ['required', 'integer'],
            'employeeId' => ['required', 'integer', 'exists:employees,id'],  
            'table_id' => ['required', 'integer'],
            'total' => ['required', 'numeric', 'min:0'],
        ];
    }
}

