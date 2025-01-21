<?php

use Illuminate\Http\Request;
use Illuminate\Support\Facades\Route;
use App\Http\Controllers\api\CategoryController;
use App\Http\Controllers\api\EmployeeController;
use App\Http\Controllers\api\OrderController;
use App\Http\Controllers\api\OrderItemController;
use App\Http\Controllers\api\ProductController;
use App\Http\Controllers\api\ReservationController;
use App\Http\Controllers\AuthController;
use App\Http\Controllers\TableController;

Route::post('login', [AuthController::class, 'login']);

Route::apiResource('tables', TableController::class);

Route::apiResource('orders', OrderController::class)->except(['create', 'edit']);

// Rotas para categories
Route::get('/categories', [CategoryController::class, 'index']);
Route::post('/categories', [CategoryController::class, 'store']);
Route::get('/categories/{id}', [CategoryController::class, 'show']);
Route::put('/categories/{category}', [CategoryController::class, 'update']);
Route::delete('/categories/{category}', [CategoryController::class, 'destroy']);


// Rotas para employees
Route::get('/employees', [EmployeeController::class, 'index']);
Route::post('/employees', [EmployeeController::class, 'store']);
Route::get('/employees/{id}', [EmployeeController::class, 'show']);
Route::put('/employees/{employee}', [EmployeeController::class, 'update']);
Route::delete('/employees/{employee}', [EmployeeController::class, 'destroy']);

// Rotas para orderItems
Route::get('/orderItems', [OrderItemController::class, 'index']);
Route::post('/orderItems', [OrderItemController::class, 'store']);
Route::get('/orderItems/{orderId}/{productId}', [OrderItemController::class, 'show']);
Route::put('/orderItems/{orderItem}', [OrderItemController::class, 'update']);
Route::delete('/orderItems/{orderId}/{productId}', [OrderItemController::class, 'destroy']);
Route::get('/orderItems/order/{orderId}', [OrderItemController::class, 'getOrderItemsByOrderId']);

// Rotas para products
Route::get('/products', [ProductController::class, 'index']);
Route::post('/products', [ProductController::class, 'store']);
Route::get('/products/{id}', [ProductController::class, 'show']);
Route::put('/products/{product}', [ProductController::class, 'update']);
Route::delete('/products/{product}', [ProductController::class, 'destroy']);
Route::get('/products/category/{categoryId}', [ProductController::class, 'getProductsFromCategory']);
Route::get('/products/status/{status}', [ProductController::class, 'getProductsByStatus']);

// Rotas para reservations
Route::get('/reservations', [ReservationController::class, 'index']);
Route::post('/reservations', [ReservationController::class, 'store']);
Route::get('/reservations/{id}', [ReservationController::class, 'show']);
Route::put('/reservations/{reservation}', [ReservationController::class, 'update']);
Route::delete('/reservations/{reservation}', [ReservationController::class, 'destroy']);
Route::get('/reservations/by-day/{day}', [ReservationController::class, 'getReservationsByDay']);
Route::get('/reservations/total/by-day/{day}', [ReservationController::class, 'getTotalReservationsToday']);
Route::get('/reservations/created/{day}', [ReservationController::class, 'getReservationsCreatedToday']);
Route::get('/reservations/total/created/{day}', [ReservationController::class, 'getTotalReservationsCreatedToday']);

// Rotas para orders
Route::get('/orders', [OrderController::class, 'index']);
Route::post('/orders', [OrderController::class, 'store']);
Route::get('/orders/{id}', [OrderController::class, 'show']);
Route::put('/orders/{order}', [OrderController::class, 'update']);
Route::delete('/orders/{order}', [OrderController::class, 'destroy']);
Route::get('/orders/employee/{employeeId}', [OrderController::class, 'getOrdersByEmployeeId']);
Route::get('/orders/status/{status}', [OrderController::class, 'getOrdersByStatus']);
Route::get('/orders/by-day/{day}', [OrderController::class, 'getOrdersToday']);
Route::get('/orders/total/by-status/{status}', [OrderController::class, 'getTotalOrdersByStatus']);
Route::get('/orders/total-amount/{status}', [OrderController::class, 'getTotalOrdersAmountByStatus']);
Route::get('/orders/total/by-day/{day}', [OrderController::class, 'getTotalOrdersToday']);