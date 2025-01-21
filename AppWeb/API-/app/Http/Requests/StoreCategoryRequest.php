<?php

namespace App\Http\Requests;

use Illuminate\Foundation\Http\FormRequest;

class StoreCategoryRequest extends FormRequest
{
    public function authorize()
    {
        return true; // Permite todas as requisições
    }

    public function rules()
    {
        return [
            'name' => ['required', 'string', 'max:255'],
            'active' => ['required', 'boolean'],
        ];
    }
}
