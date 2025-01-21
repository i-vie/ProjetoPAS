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
    Schema::create('order_items', function (Blueprint $table) {
        $table->unsignedBigInteger('orderId'); // ID do pedido
        $table->unsignedBigInteger('productId'); // ID do produto
        $table->integer('quantity');
        $table->timestamps();

        // Chaves primárias compostas
        $table->primary(['orderId', 'productId']);

        // Chaves estrangeiras
        $table->foreign('orderId')->references('id')->on('orders')->onDelete('cascade')->onUpdate('cascade');
        $table->foreign('productId')->references('id')->on('products')->onDelete('restrict')->onUpdate('cascade');
    });
}

public function down()
{
    Schema::dropIfExists('order_items');
}

};
