<?php

namespace App\Http\Controllers\Api;

use App\Http\Controllers\Controller;
use App\Models\Category;
use App\Http\Requests\StoreCategoryRequest;
use App\Http\Resources\CategoryResource;

class CategoryController extends Controller
{
   /*
* Display a listing of the resource.
*/
   public function index()
   {
      $categories = Category::all();
      return CategoryResource::collection($categories);
   }


   /*
* Store a newly created resource in storage.
*/
   public function store(StoreCategoryRequest $request)
   {
      $category = Category::create($request->validated());
      return new CategoryResource($category);
   }

   /*
* Display the specified resource.
*/
   public function show(string $id)
   {
      $category = Category::findOrFail($id);
      return new CategoryResource($category);
   }


   /*
* Update the specified resource in storage.
*/
   public function update(StoreCategoryRequest $request, Category $category)
   {
      $category->update($request->validated());
      return new CategoryResource($category);
   }

   /*
* Remove the specified resource from storage.
*/
   public function destroy(Category $category)
   {
      $category->delete();
      return new CategoryResource($category);
   }
}
