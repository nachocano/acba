package com.augmentedcba.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.augmentedcba.exception.AugmentedCbaException;
import com.augmentedcba.utils.Constants;

@XmlRootElement
public class GetPoisReponse {

    private List<Poi> hotspots = new ArrayList<Poi>();;
    private String layer;
    private String errorString;
    private int errorCode;

    public GetPoisReponse() {
        this.layer = Constants.LAYER_NAME;
        this.errorString = AugmentedCbaException.Error.OK.getDescription();
        this.errorCode = AugmentedCbaException.Error.OK.getCode();
    }

    public void addAllHotspots(final Collection<Poi> hotspots) {
        this.hotspots.addAll(hotspots);
    }

    public void setHotspots(final List<Poi> hotspots) {
        this.hotspots = hotspots;
    }

    public String getLayer() {
        return layer;
    }

    public void setLayer(final String layer) {
        this.layer = layer;
    }

    public String getErrorString() {
        return errorString;
    }

    public void setErrorString(final String errorString) {
        this.errorString = errorString;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(final int errorCode) {
        this.errorCode = errorCode;
    }

    public List<Poi> getHotspots() {
        return hotspots;
    }

}
