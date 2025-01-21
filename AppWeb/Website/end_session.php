<?php 
session_start();
include_once "employee.php";
$user = new Employee(null, null, null, null, null, null, null, null);
$user->end_session();
header("location: login.php");

header("location: login.php")

?>