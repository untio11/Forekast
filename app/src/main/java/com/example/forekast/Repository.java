package com.example.forekast;

import java.util.List;

class Repository extends RepositoryInterface {

    WeatherAPIInterface weatherAPI = new WeatherAPI();

    @Override
    Weather getWeather() {
        return weatherAPI.getWeather();
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
