<?php

namespace App\Http\Requests;

use Illuminate\Foundation\Http\FormRequest;

class StoreEmployeeRequest extends FormRequest
{
    public function authorize()
    {
        return true;
    }

    public function rules()
    {
        return [
            'name' => ['required', 'string', 'max:255'],
            'username' => ['required', 'string', 'unique:employees'],
            'email' => ['required', 'email', 'unique:employees'],
            'password' => ['required', 'string', 'min:8'],
            'admin' => ['boolean'],
            'active' => ['boolean'],
        ];
    }
}
