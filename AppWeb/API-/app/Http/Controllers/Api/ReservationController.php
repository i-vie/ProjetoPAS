<?php

namespace App\Http\Controllers\Api;

use App\Http\Controllers\Controller;
use App\Models\Reservation;
use App\Http\Requests\StoreReservationRequest;
use App\Http\Resources\ReservationResource;
use Carbon\Carbon;

class ReservationController extends Controller
{
    /*
    * Display a listing of the resource.
    */
    public function index()
    {
        $reservations = Reservation::all();
        return ReservationResource::collection($reservations);
    }

    /*
    * Store a newly created resource in storage.
    */
    public function store(StoreReservationRequest $request)
    {
        $reservation = Reservation::create($request->validated());
        return new ReservationResource($reservation);
    }

    /*
    * Display the specified resource.
    */
    public function show($id)
    {
        $reservation = Reservation::findOrFail($id);
        return new ReservationResource($reservation);
    }

    /*
    * Update the specified resource in storage.
    */
    public function update(StoreReservationRequest $request, Reservation $reservation)
    {
        $reservation->update($request->validated());
        return new ReservationResource($reservation);
    }

    /*
    * Remove the specified resource from storage.
    */
    public function destroy(Reservation $reservation)
    {
        $reservation->delete();
        return response(null, 204);
    }

    /*
    * Get reservations for a specific day.
    */
    public function getReservationsByDay($day)
    {
        $startDate = $day ? Carbon::parse($day)->startOfDay() : now()->startOfDay();
        $endDate = $day ? Carbon::parse($day)->endOfDay() : now()->endOfDay();

        $reservations = Reservation::whereBetween('dateReservation', [$startDate, $endDate])
            ->orderBy('dateReservation', 'asc')
            ->get();

        return ReservationResource::collection($reservations);
    }

    /*
    * Get the total number of reservations made for a specific day.
    */
    public function getTotalReservationsToday($day = null)
    {
        $startDate = $day ? Carbon::parse($day)->startOfDay() : now()->startOfDay();
        $endDate = $day ? Carbon::parse($day)->endOfDay() : now()->endOfDay();

        $total = Reservation::whereBetween('dateReservation', [$startDate, $endDate])
            ->get()->count();

        return response()->json([
            'total' => $total
        ]);
    }

    /*
    * Get all reservations created today.
    */
    public function getReservationsCreatedToday($day = null)
    {
        $startDate = $day ? Carbon::parse($day)->startOfDay() : now()->startOfDay();
        $endDate = $day ? Carbon::parse($day)->endOfDay() : now()->endOfDay();

        $reservations = Reservation::whereBetween('created_at', [$startDate, $endDate])
            ->orderBy('dateReservation', 'asc')
            ->get();

        return ReservationResource::collection($reservations);
    }

    /*
    * Get the total number of reservations created today.
    */
    public function getTotalReservationsCreatedToday($day = null)
    {
        $startDate = $day ? Carbon::parse($day)->startOfDay() : now()->startOfDay();
        $endDate = $day ? Carbon::parse($day)->endOfDay() : now()->endOfDay();

        $total = Reservation::whereBetween('created_at', [$startDate, $endDate])
            ->get()->count();

        return response()->json([
            'total' => $total
        ]);
    }
}
