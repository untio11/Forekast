package com.example.forekast;

import java.util.List;

class Repository extends RepositoryInterface {

    @Override
    Weather getWeather() {
        return null;
    }

    @Override
    List<Clothing> getClothing(ClothingType type, ClothingCriteria criteria) {
        return null;
    }

    @Override
    void addClothing(Clothing clothing) {

    }

    @Override
    void removeClothing(Clothing clothing) {

    }
}
