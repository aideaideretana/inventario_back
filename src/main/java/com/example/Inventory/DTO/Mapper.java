package com.example.Inventory.DTO;

import com.example.Inventory.model.Product;
import com.example.Inventory.model.Groups;
import com.example.Inventory.model.Currency;

public class Mapper {

    public static Product toProduct(ProductRequest productRequest){
        
        Product product = new Product();
        product.setName(productRequest.name());
        product.setPrice(productRequest.price());
        product.setQuantity(productRequest.quantity());
        product.setDescription(productRequest.description());
        product.setCategory(productRequest.category());
        product.setTeams(productRequest.teams());
        product.setFavorite(productRequest.favorite());
        return product;
    }

    public static ProductResponse toProductResponse(Product product){
       return new ProductResponse(product.getId(), product.getName(), product.getQuantity(), product.getPrice(), product.getDescription(),product.getCategory(),product.getTeams(),product.isFavorite());
    }

    public static Currency toCurrency(CurrencyRequest request) {
        Currency currency = new Currency();
        currency.setShortName(request.shortName());
        currency.setName(request.name());
        currency.setSymbol(request.symbol());
        currency.setRate(request.rate());
        return currency;
    }
    
    public static CurrencyResponse toCurrencyResponse(Currency currency) {
        return new CurrencyResponse(currency.getId(), currency.getShortName(), currency.getName(), currency.getSymbol(), currency.getRate());
    }
    
    public static Groups toGroup(GroupRequest request) {
        Groups group = new Groups();
        group.setName(request.name());
        group.setImage(request.image());
        group.setDescription(request.description());
        return group;
    }
    
    public static GroupResponse toGroupResponse(Groups group) {
        return new GroupResponse(group.getId(), group.getName(), group.getImage(), group.getDescription());
    }

}
