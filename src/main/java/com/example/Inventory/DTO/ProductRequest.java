package com.example.Inventory.DTO;

public record ProductRequest(String name, Integer quantity, Double price, String description,String category,String group,boolean favorite) {

}
