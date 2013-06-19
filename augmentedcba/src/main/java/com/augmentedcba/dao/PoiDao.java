package com.augmentedcba.dao;

import java.util.List;

import com.augmentedcba.exception.AugmentedCbaException;
import com.augmentedcba.model.Poi;

public interface PoiDao {

    List<Poi> getAllHotspots();

    List<Poi> getHotspots(double latitude, double longitude, double radius) throws AugmentedCbaException;

}
