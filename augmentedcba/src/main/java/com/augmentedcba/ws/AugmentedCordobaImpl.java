package com.augmentedcba.ws;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.lang.Validate;
import org.apache.commons.lang.math.DoubleRange;
import org.apache.log4j.Logger;

import com.augmentedcba.model.GetPoisReponse;
import com.augmentedcba.service.AugmentedCordobaService;

@Path("/")
public class AugmentedCordobaImpl implements AugmentedCordoba {

    private static final Logger LOG = Logger.getLogger(AugmentedCordobaImpl.class);

    private final AugmentedCordobaService service;

    private final DoubleRange LAT_RANGE;
    private final DoubleRange LON_RANGE;
    private final DoubleRange RAD_RANGE;

    public AugmentedCordobaImpl(final AugmentedCordobaService service, final int minLatitude, final int maxLatitude,
            final int minLongitude, final int maxLongitude, final int minRadius, final int maxRadius) {
        Validate.notNull(service);
        Validate.isTrue(minLatitude < maxLatitude);
        Validate.isTrue(minLongitude < maxLongitude);
        Validate.isTrue(minRadius < maxRadius);
        this.service = service;
        LAT_RANGE = new DoubleRange(minLatitude, maxLatitude);
        LON_RANGE = new DoubleRange(minLongitude, maxLongitude);
        RAD_RANGE = new DoubleRange(minRadius, maxRadius);
    }

    @Override
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public GetPoisReponse getPois(@QueryParam("lat") final String latitude, @QueryParam("lon") final String longitude,
            @QueryParam("radius") final String radius) {
        try {
            final double lat = assertParam(latitude, LAT_RANGE);
            final double lon = assertParam(longitude, LON_RANGE);
            final double rad = assertParam(radius, RAD_RANGE);
            LOG.info("Request Received= latitud:" + lat + ", longitud:" + lon + ", rad:" + rad);
            return service.getPois(lat, lon, rad);
        } catch (final Exception e) {
            LOG.error("Unexpected Exception while retrieving pois: " + e.getMessage());
            throw new WebApplicationException(Response.Status.NO_CONTENT);
        }
    }

    private double assertParam(final String param, final DoubleRange range) {
        double result;
        try {
            result = Double.parseDouble(param);
            if (!range.containsDouble(result)) {
                LOG.error("Param " + param + " is out of range. Valid range [" + range.getMinimumDouble() + ","
                        + range.getMaximumDouble() + "]");
                throw new RuntimeException("Parameter out of range");
            }
        } catch (final NumberFormatException exc) {
            LOG.error("Param " + param + " is not a valid double");
            throw new RuntimeException("Parameter is not a valid double");
        }
        return result;
    }

}
