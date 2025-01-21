<?php

namespace App\Http\Controllers;

use App\Http\Controllers\Controller;
use App\Models\Table;
use App\Http\Requests\StoreTableRequest;
use App\Http\Resources\TableResource;

class TableController extends Controller
{
    /*
    * Display a listing of the resource.
    */
    public function index()
    {
        $tables = Table::all();
        return TableResource::collection($tables);
    }

    /*
    * Store a newly created resource in storage.
    */
    public function store(StoreTableRequest $request)
    {
        $table = Table::create($request->validated());
        return new TableResource($table);
    }

    /*
    * Display the specified resource.
    */
    public function show(string $id)
    {
        $table = Table::findOrFail($id);
        return new TableResource($table);
    }

    /*
    * Update the specified resource in storage.
    */
    public function update(StoreTableRequest $request, Table $table)
    {
        $table->update($request->validated());
        return new TableResource($table);
    }

    /*
    * Remove the specified resource from storage.
    */
    public function destroy(Table $table)
    {
        $table->delete();
        return new TableResource($table);
    }
}
