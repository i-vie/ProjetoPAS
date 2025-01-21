<?php

namespace App\Http\Controllers\Api;

use App\Http\Controllers\Controller;
use App\Models\Order;
use App\Http\Requests\StoreOrderRequest;
use App\Http\Resources\OrderResource;
use Carbon\Carbon;

class OrderController extends Controller
{
    /*
    * Display a listing of the resource.
    */
    public function index()
    {
        $orders = Order::all();
        return OrderResource::collection($orders);
    }

    /*
    * Store a newly created resource in storage.
    */
    public function store(StoreOrderRequest $request)
    {
        $order = Order::create($request->validated());
        return new OrderResource($order);
    }

    /*
    * Display the specified resource.
    */
    public function show($id)
    {
        $order = Order::findOrFail($id);
        return new OrderResource($order);
    }

    /*
    * Update the specified resource in storage.
    */
    public function update(StoreOrderRequest $request, Order $order)
    {
        $order->update($request->validated());
        return new OrderResource($order);
    }

    /*
    * Remove the specified resource from storage.
    */
    public function destroy(Order $order)
    {
        $order->delete();
        return response(null, 204);
    }

    /*
    * Get all orders by status.
    */
    public function getOrdersByStatus($open)
    {
        $orders = Order::where('open', $open)
            ->orderBy('open', 'desc')
            ->orderBy('created_at', 'desc')
            ->get();

        return OrderResource::collection($orders);
    }

    /*
    * Get all orders assigned to a specific employee.
    */
    public function getOrdersByEmployeeId($employeeId)
    {
        $startDate = now()->startOfDay();
        $endDate = now()->endOfDay();

        $orders = Order::where('employeeId', $employeeId)
            ->where(function ($query) use ($startDate, $endDate) {
                $query->where('open', true)
                      ->orWhere(function ($query) use ($startDate, $endDate) {
                          $query->where('open', false)
                                ->whereBetween('created_at', [$startDate, $endDate]);
                      });
            })
            ->orderBy('open', 'desc')
            ->orderBy('created_at', 'desc')
            ->get();

        return OrderResource::collection($orders);
    }

    /*
    * Get all orders created today.
    */
    public function getOrdersToday($day)
    {
        $startDate = $day ? Carbon::parse($day)->startOfDay() : now()->startOfDay();
        $endDate = $day ? Carbon::parse($day)->endOfDay() : now()->endOfDay();

        $orders = Order::whereBetween('created_at', [$startDate, $endDate])
            ->orderBy('open', 'desc')
            ->get();

        return OrderResource::collection($orders);
    }

    /*
    * Get the total number of orders by status.
    */
    public function getTotalOrdersByStatus($status)
    {
        $startDate = now()->startOfDay();
        $endDate = now()->endOfDay();

        $total = Order::where('open', $status)
            ->whereBetween('created_at', [$startDate, $endDate])
            ->get()->count();

        return response()->json([
            'total' => $total
        ]);
    }

    /*
    * Get the total amount of orders by status.
    */
    public function getTotalOrdersAmountByStatus($status)
    {
        $startDate = now()->startOfDay();
        $endDate = now()->endOfDay();

        $total = Order::where('open', $status)
            ->whereBetween('created_at', [$startDate, $endDate])
            ->sum('total');

        return response()->json([
            'total' => $total
        ]);
    }

    /*
    * Get the total number of orders created today.
    */
    public function getTotalOrdersToday($day = null)
    {
        $startDate = $day ? Carbon::parse($day)->startOfDay() : now()->startOfDay();
        $endDate = $day ? Carbon::parse($day)->endOfDay() : now()->endOfDay();

        $total = Order::whereBetween('created_at', [$startDate, $endDate])->get()->count();

        return response()->json([
            'total' => $total
        ]);
    }
}
