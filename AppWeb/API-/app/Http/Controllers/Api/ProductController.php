<?php

namespace App\Http\Controllers\Api;

use App\Http\Controllers\Controller;
use App\Models\Product;
use App\Http\Requests\StoreProductRequest;
use App\Http\Resources\ProductResource;

class ProductController extends Controller
{
    /*
    * Display a listing of all products.
    */
    public function index()
    {
        $products = Product::all();
        return ProductResource::collection($products);
    }

    /*
    * Store a newly created product in storage.
    */
    public function store(StoreProductRequest $request)
    {
        $product = Product::create($request->validated());
        return new ProductResource($product);
    }

    /*
    * Display the specified product.
    */
    public function show($id)
    {
        $product = Product::findOrFail($id);
        return new ProductResource($product);
    }

    /*
    * Update the specified product in storage.
    */
    public function update(StoreProductRequest $request, Product $product)
    {
        $product->update($request->validated());
        return new ProductResource($product);
    }

    /*
    * Remove the specified product from storage.
    */
    public function destroy(Product $product)
    {
        $product->delete();
        return response(null, 204);
    }

    /*
    * Retrieve all products from a specific category.
    */
    public function getProductsFromCategory($categoryId)
    {
        $products = Product::where('category_id', $categoryId)
            ->where('active', 1)
            ->get();
        return ProductResource::collection($products);
    }

    /*
    * Retrieve all products by their active status.
    */
    public function getProductsByStatus($status)
    {
        $products = Product::where('active', $status)->get();
        return ProductResource::collection($products);
    }
}
