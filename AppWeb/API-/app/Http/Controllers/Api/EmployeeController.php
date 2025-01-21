<?php

namespace App\Http\Controllers\Api;

use App\Http\Controllers\Controller;
use App\Models\Employee;
use App\Http\Requests\StoreEmployeeRequest;
use App\Http\Resources\EmployeeResource;

class EmployeeController extends Controller
{
    /*
    * Display a listing of the resource.
    */
    public function index()
    {
        $employees = Employee::all();
        return EmployeeResource::collection($employees);
    }

    /*
    * Store a newly created resource in storage.
    */
    public function store(StoreEmployeeRequest $request)
    {
        $employee = Employee::create($request->validated());
        return new EmployeeResource($employee);
    }

    /*
    * Display the specified resource.
    */
    public function show($id)
    {
        $employee = Employee::findOrFail($id);
        return new EmployeeResource($employee);
    }

    /*
    * Update the specified resource in storage.
    */
    public function update(StoreEmployeeRequest $request, Employee $employee)
    {
        $employee->update($request->validated());
        return new EmployeeResource($employee);
    }

    /*
    * Remove the specified resource from storage.
    */
    public function destroy(Employee $employee)
    {
        $employee->delete();
        return response(null, 204);
    }
}
