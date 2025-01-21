<?php

namespace App\Http\Controllers;

use App\Models\Employee;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Hash;

class AuthController extends Controller
{
    public function login(Request $request)
    {
        $request->validate([
            'username' => 'required_without:email',
            'email' => 'required_without:username',
            'password' => 'required'
        ]);

        
        $employee = null;
        if ($request->has('username')) {
            $employee = Employee::where('username', $request->username)->first();
        }
        
        if ($request->has('email')) {
            $employee = Employee::where('email', $request->email)->first();
        }

        //verificar se o Employee foi encontrado
        if ($employee) {
            //comparar a password fornecida com o hash armazenado
            if (Hash::check($request->password, $employee->password)) {
                return response()->json([
                    'status' => 'success',
                    'message' => "success",
                    'employee' => [
                    'id' => $employee->id,
                    'name' => $employee->name,
                    'username' => $employee->username,
                    'email' => $employee->email,
                    'admin' => $employee->admin
                    ]
                ]);
            } else {
                return response()->json([
                    'status' => 'error',
                    'message' => "incorrect password"
                ], 401);
            }
        } else {
            // Employee não encontrado
            return response()->json([
                'status' => 'error',
                'message' => "not found"
            ], 404);
        }
    }
}
