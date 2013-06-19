package com.augmentedcba.model;

import org.apache.commons.lang.Validate;

public class PoiAction {

    private enum Method {
        GET, POST;
    };

    // mandatory params
    private String uri;
    private String label;
    // optional params
    private String contentType;
    private Method method;
    private int activityType;

    public PoiAction() {
        // needed by JAXB
    }

    // copy constructor
    public PoiAction(final PoiAction action) {
        Validate.notNull(action);
        this.uri = action.uri;
        this.label = action.label;
        this.contentType = action.contentType;
        this.method = action.method;
        this.activityType = action.activityType;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(final String label) {
        this.label = label;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(final String uri) {
        this.uri = uri;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(final String contentType) {
        this.contentType = contentType;
    }

    public String getMethod() {
        return method.toString();
    }

    public void setMethod(final String method) {
        this.method = Method.valueOf(method);
    }

    public int getActivityType() {
        return activityType;
    }

    public void setActivityType(final int activityType) {
        this.activityType = activityType;
    }
}
