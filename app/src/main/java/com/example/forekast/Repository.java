package com.example.forekast;

import java.util.List;

class Repository extends RepositoryInterface {

    WeatherAPIInterface weatherAPI = new WeatherAPI();

    @Override
    Weather getWeather() {
        return weatherAPI.getWeather();
    }

    @Override
    List<ClothingInterface> getClothing(ClothingType type, ClothingCriteria criteria) {
        return null;
    }

    @Override
    void addClothing(ClothingInterface clothing) {

    }

    @Override
    void removeClothing(ClothingInterface clothing) {

    }
}
