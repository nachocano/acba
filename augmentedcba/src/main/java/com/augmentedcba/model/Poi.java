package com.augmentedcba.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlTransient;

import org.apache.commons.lang.Validate;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class Poi {

    // mandatory params
    private String id;
    private double distance;
    private String title;
    private int type;
    private long lat;
    private long lon;
    private List<PoiAction> actions = new ArrayList<PoiAction>();

    // optional params
    private String attribution;
    private String line2;
    private String line3;
    private String line4;
    private String imageURL;

    private double doubleLatitude;
    private double doubleLongitude;

    // default constructor required by JAXB
    public Poi() {
    }

    // copy constructor
    public Poi(final Poi that) {
        Validate.notNull(that);
        this.id = that.id;
        this.title = that.title;
        this.type = that.type;
        this.attribution = that.attribution;
        this.line2 = that.line2;
        this.line3 = that.line3;
        this.line4 = that.line4;
        this.imageURL = that.imageURL;
        for (final PoiAction action : that.actions) {
            this.actions.add(new PoiAction(action));
        }
    }

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public String getAttribution() {
        return attribution;
    }

    public void setAttribution(final String attribution) {
        this.attribution = attribution;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public long getLat() {
        return lat;
    }

    public void setLat(final long latitude) {
        this.lat = latitude;
    }

    public long getLon() {
        return lon;
    }

    public void setLon(final long longitude) {
        this.lon = longitude;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(final String imageUrl) {
        this.imageURL = imageUrl;
    }

    public String getLine2() {
        return line2;
    }

    public void setLine2(final String line2) {
        this.line2 = line2;
    }

    public String getLine3() {
        return line3;
    }

    public void setLine3(final String line3) {
        this.line3 = line3;
    }

    public String getLine4() {
        return line4;
    }

    public void setLine4(final String line4) {
        this.line4 = line4;
    }

    public int getType() {
        return type;
    }

    public void setType(final int type) {
        this.type = type;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(final double distance) {
        this.distance = distance;
    }

    public void setActions(final List<PoiAction> actions) {
        this.actions = actions;
    }

    public List<PoiAction> getActions() {
        return actions;
    }

    public void addAction(final PoiAction action) {
        actions.add(action);
    }

    public void setDoubleLatitude(final double latitude) {
        this.doubleLatitude = latitude;
    }

    public void setDoubleLongitude(final double longitude) {
        this.doubleLongitude = longitude;
    }

    @XmlTransient
    public double getDoubleLatitude() {
        return doubleLatitude;
    }

    @XmlTransient
    public double getDoubleLongitude() {
        return doubleLongitude;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(31, 17).append(id).toHashCode();
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Poi)) {
            return false;
        }
        final Poi that = (Poi) obj;
        return new EqualsBuilder().append(id, that.id).isEquals();
    }

}
