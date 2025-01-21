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
    Schema::create('products', function (Blueprint $table) {
        $table->id();
        $table->string('name');
        $table->decimal('price', 8, 2); 
        $table->text('description'); 
        $table->unsignedBigInteger('category_id'); 
        $table->boolean('active')->default(true); 
        $table->timestamps();
    
        // Relacionamento com a tabela categories
        $table->foreign('category_id')->references('id')->on('categories')->onDelete('cascade');
    });
}


public function down()
{
    Schema::dropIfExists('products');
}

};
