package com.augmentedcba.service;

import com.augmentedcba.model.GetPoisReponse;

public interface AugmentedCordobaService {

    GetPoisReponse getPois(double latitude, double longitude, double radius);

}
