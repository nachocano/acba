package com.augmentedcba.service.impl;

import java.util.List;

import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;

import com.augmentedcba.dao.PoiDao;
import com.augmentedcba.exception.AugmentedCbaException;
import com.augmentedcba.model.GetPoisReponse;
import com.augmentedcba.model.Poi;
import com.augmentedcba.service.AugmentedCordobaService;

public class AugmentedCordobaServiceImpl implements AugmentedCordobaService {

    private static final Logger LOG = Logger.getLogger(AugmentedCordobaServiceImpl.class);

    private final PoiDao poiDao;

    public AugmentedCordobaServiceImpl(final PoiDao poiDao) {
        Validate.notNull(poiDao);
        this.poiDao = poiDao;
    }

    @Override
    public GetPoisReponse getPois(final double latitude, final double longitude, final double radius) {
        final GetPoisReponse response = new GetPoisReponse();
        try {
            final List<Poi> pois = poiDao.getHotspots(latitude, longitude, radius);
            response.addAllHotspots(pois);
        } catch (final AugmentedCbaException e) {
            LOG.error("Exception while getting hotspots: " + e.getMessage());
            response.setErrorString(e.getMessage());
            response.setErrorCode(e.getErrorCode());
        }
        return response;
    }
}
