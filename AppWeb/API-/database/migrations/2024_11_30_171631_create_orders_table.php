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
    Schema::create('orders', function (Blueprint $table) {
        $table->id();
        $table->boolean('open');
        $table->foreignId('employeeId')
                ->constrained('employees')
                ->onDelete('restrict')
                ->onUpdate('cascade');
        $table->foreignId('table_id')
                ->constrained('tables') 
                ->onDelete('restrict')
                ->onUpdate('cascade'); 
        $table->float('total');
        $table->timestamps();
    });
}


    /**
     * Reverse the migrations.
     */
    public function down(): void
    {
        Schema::dropIfExists('orders');
    }
};
