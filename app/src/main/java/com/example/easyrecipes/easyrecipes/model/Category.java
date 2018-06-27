package com.example.easyrecipes.easyrecipes.model;

public enum Category {

    BOLOS_E_TORTAS("bolos-e-tortas-doces", "Bolos e Tortas"),
    CARNES("carnes", "Carnes"),
    AVES("aves", "Aves"),
    PEIXES_E_FRUTOS_DO_MAR("peixes-e-frutos-do-mar", "Peixes e Frutos do Mar"),
    SALADAS_E_MOLHOS("saladas-molhos-e-acompanhamentos", "Saladas e Molhos"),
    SOPAS("sopas", "Sopas"),
    MASSAS("massas", "Massas"),
    BEBIDAS("bebidas", "Bebidas"),
    DOCES_E_SOBREMESAS("doces-e-sobremesas", "Doces e Sobremesas"),
    LANCHES("lanches", "Lanches"),
    ALIMENTACAO_SAUDAVEL("alimentacao-saudavel", "Alimentação Saudável");

    private String categoryUrlString;
    private String categoryName;

    Category(String categoryUrlString, String categoryName) {
        this.categoryUrlString = categoryUrlString;
        this.categoryName = categoryName;
    }

    public String getCategoryUrlString() {
        return this.categoryUrlString;
    }

    public String getCategoryName() {
        return categoryName;
    }
}