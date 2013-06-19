package com.augmentedcba.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;

import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.util.CollectionUtils;

import com.augmentedcba.dao.PoiDao;
import com.augmentedcba.exception.AugmentedCbaException;
import com.augmentedcba.model.Poi;
import com.augmentedcba.model.PoiAction;
import com.augmentedcba.utils.Constants;
import com.augmentedcba.utils.Utils;

public class JdbcPoiDao implements PoiDao {

    private static final Logger LOG = Logger.getLogger(JdbcPoiDao.class);

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final Properties sqlStatements;
    private final CacheManager cacheManager;
    private final EhCacheWrapper<List<Poi>, Poi> cacheWrapper;

    private static final String GET_HOTSPOTS = "poi.selectHotspots";

    // poi table columns
    private static final String COLUMN_POI_ID = "poi_id";
    private static final String COLUMN_ATTRIBUTION = "attribution";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_LATITUDE = "latitude";
    private static final String COLUMN_LONGITUDE = "longitude";
    private static final String COLUMN_IMAGE_URL = "image_url";
    private static final String COLUMN_LINE2 = "line2";
    private static final String COLUMN_LINE3 = "line3";
    private static final String COLUMN_LINE4 = "line4";
    private static final String COLUMN_TYPE = "type";

    // poi_action table columns
    private static final String COLUMN_POI_ACTION_ID = "poi_action_id";
    private static final String COLUMN_LABEL = "label";
    private static final String COLUMN_URI = "uri";
    private static final String COLUMN_METHOD = "method";
    private static final String COLUMN_CONTENT_TYPE = "content_type";
    private static final String COLUMN_ACTIVITY_TYPE = "activity_type";

    private static final String CACHE_POIS = "pois";
    private static final String SEQUENCE_PARAM = "{sequence}";
    private static final String CACHE_KEY_PREFIX = "pois:{sequence}@all_pois";

    public JdbcPoiDao(final DataSource datasource, final Properties sqlStatements, final CacheManager cacheMgr,
            final long timeToLiveInSeconds, final int cacheSequence) {
        Validate.notNull(datasource);
        Validate.notNull(sqlStatements);
        Validate.notNull(cacheMgr);
        this.jdbcTemplate = new NamedParameterJdbcTemplate(datasource);
        this.sqlStatements = sqlStatements;
        this.cacheManager = cacheMgr;
        final Ehcache ehCache = cacheManager.getEhcache(CACHE_POIS);
        ehCache.getCacheConfiguration().setTimeToLiveSeconds(timeToLiveInSeconds);
        final String cacheKey = CACHE_KEY_PREFIX.replace(SEQUENCE_PARAM, String.valueOf(cacheSequence));
        this.cacheWrapper = new EhCacheWrapper<List<Poi>, Poi>(ehCache, cacheKey);
    }

    public void init() {
        getAllHotspots();
    }

    public void shutdown() {
        cacheManager.removeCache(CACHE_POIS);
    }

    private String getSql(final String name) {
        return sqlStatements.getProperty(name);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Poi> getAllHotspots() {
        List<Poi> pois = cacheWrapper.getAll();
        if (pois == null) {
            LOG.info("Hitting database");
            final Map<Integer, Poi> resultMap = new HashMap<Integer, Poi>();
            jdbcTemplate.query(getSql(GET_HOTSPOTS), Collections.EMPTY_MAP, new PoiRowMapper(resultMap));
            pois = CollectionUtils.isEmpty(resultMap) ? null : new ArrayList<Poi>(resultMap.values());
            if (pois != null) {
                cacheWrapper.addAll(pois);
            }
        }
        return pois;
    }

    @Override
    public List<Poi> getHotspots(final double latitude, final double longitude, final double radius)
            throws AugmentedCbaException {
        final List<Poi> pois = getAllHotspots();
        if (pois == null) {
            LOG.warn("No hotspot found in the db");
            throw new AugmentedCbaException(AugmentedCbaException.Error.NO_HOTSPOTS_IN_DB);
        }
        final List<Poi> hotspots = new ArrayList<Poi>();
        for (final Poi poi : pois) {
            final double distance = Utils.calculateDistance(poi.getDoubleLatitude(), poi.getDoubleLongitude(),
                    latitude, longitude);
            if (distance < radius) {
                LOG.info("Adding hotspot " + poi.getTitle() + " to response list");
                final Poi newPoi = new Poi(poi);
                newPoi.setDistance(distance);
                newPoi.setLat(Utils.getDecimalGpsCoordinate(poi.getDoubleLatitude()));
                newPoi.setLon(Utils.getDecimalGpsCoordinate(poi.getDoubleLongitude()));
                hotspots.add(newPoi);
            } else {
                LOG.info("Hotspot '" + poi.getTitle() + "' not in range");
            }
        }
        if (hotspots.isEmpty()) {
            throw new AugmentedCbaException(AugmentedCbaException.Error.NO_MATCHES_IN_RANGE);
        }
        return hotspots;
    }

    private static final class PoiRowMapper implements RowMapper<Poi> {

        private final Map<Integer, Poi> map;

        public PoiRowMapper(final Map<Integer, Poi> map) {
            Validate.notNull(map);
            this.map = map;
        }

        @Override
        public Poi mapRow(final ResultSet rs, final int rowNum) throws SQLException {
            final int poiId = rs.getInt(COLUMN_POI_ID);
            Poi poi = map.get(poiId);
            if (poi == null) {
                poi = new Poi();
                poi.setId(Constants.POI_PREFIX + String.valueOf(poiId));
                poi.setAttribution(rs.getString(COLUMN_ATTRIBUTION));
                poi.setTitle(rs.getString(COLUMN_TITLE));
                poi.setDoubleLatitude(rs.getDouble(COLUMN_LATITUDE));
                poi.setDoubleLongitude(rs.getDouble(COLUMN_LONGITUDE));
                poi.setImageURL(rs.getString(COLUMN_IMAGE_URL));
                poi.setLine2(rs.getString(COLUMN_LINE2));
                poi.setLine3(rs.getString(COLUMN_LINE3));
                poi.setLine4(rs.getString(COLUMN_LINE4));
                poi.setType(rs.getInt(COLUMN_TYPE));
            }
            final int poiActionId = rs.getInt(COLUMN_POI_ACTION_ID);
            if (poiActionId != 0) {
                final PoiAction action = new PoiAction();
                action.setLabel(rs.getString(COLUMN_LABEL));
                action.setUri(rs.getString(COLUMN_URI));
                action.setContentType(rs.getString(COLUMN_CONTENT_TYPE));
                action.setMethod(rs.getString(COLUMN_METHOD));
                action.setActivityType(rs.getInt(COLUMN_ACTIVITY_TYPE));
                poi.addAction(action);
            }
            map.put(poiId, poi);
            return null;
        }
    }

}
