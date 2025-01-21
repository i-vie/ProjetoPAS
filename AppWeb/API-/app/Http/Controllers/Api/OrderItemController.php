<?php

namespace App\Http\Controllers\Api;

use App\Http\Controllers\Controller;
use App\Models\OrderItem;
use App\Http\Requests\StoreOrderItemRequest;
use App\Http\Resources\OrderItemResource;

class OrderItemController extends Controller
{
    /*
    * Display a listing of all order items.
    */
    public function index()
    {
        $orderItems = OrderItem::all();
        return OrderItemResource::collection($orderItems);
    }

    /*
    * Store a newly created order item in storage.
    */
    public function store(StoreOrderItemRequest $request)
    {
        $orderItem = OrderItem::create($request->validated());
        return new OrderItemResource($orderItem);
    }

    /*
    * Display the specified order item.
    */
    public function show($orderId, $productId)
    {
        $orderItem = OrderItem::where('orderId', $orderId)
            ->where('productId', $productId)
            ->firstOrFail();

        return new OrderItemResource($orderItem);
    }

    /*
    * Update the specified order item in storage.
    */
    public function update(StoreOrderItemRequest $request, OrderItem $orderItem)
    {
        $orderItem->update($request->validated());
        return new OrderItemResource($orderItem);
    }

    /*
    * Remove the specified order item from storage.
    */
    public function destroy($orderId, $productId)
    {
        $orderItem = OrderItem::where('orderId', $orderId)
            ->where('productId', $productId)
            ->firstOrFail();

        $orderItem->delete();
        return response(null, 204);
    }

    /*
    * Retrieve all order items for a specific order ID.
    */
    public function getOrderItemsByOrderId($orderId)
    {
        $orderItems = OrderItem::where('orderId', $orderId)->get();

        return OrderItemResource::collection($orderItems);
    }
}
