package com.augmentedcba.ws;

import com.augmentedcba.model.GetPoisReponse;

public interface AugmentedCordoba {

    GetPoisReponse getPois(String latitude, String longitude, String radius);
}
