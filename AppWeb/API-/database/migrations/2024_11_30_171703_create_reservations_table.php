<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

return new class extends Migration
{
    /**
     * Run the migrations.
     */
    public function up()
{
    Schema::create('reservations', function (Blueprint $table) {
        $table->id();
        $table->foreignId('employeeId')->constrained('employees')->onDelete('restrict')->onUpdate('cascade');
        $table->foreignId('table_id')
                ->constrained('tables') 
                ->onDelete('restrict')
                ->onUpdate('cascade'); 
        $table->timestamp('dateReservation')->nullable();
        $table->integer('nrPeople');
        $table->string('comments')->nullable();
        $table->timestamps();
    });
}


    /**
     * Reverse the migrations.
     */
    public function down(): void
    {
        Schema::dropIfExists('reservations');
    }
};
