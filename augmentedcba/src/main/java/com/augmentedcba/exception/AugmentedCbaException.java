package com.augmentedcba.exception;

public class AugmentedCbaException extends Exception {

    private static final long serialVersionUID = -5134332616352691153L;

    public static enum Error {

        OK("ok", 0), NO_HOTSPOTS_IN_DB("No hotspots found in db", 20), NO_MATCHES_IN_RANGE("No hotspots within range",
                21);

        private final String description;
        private final int code;

        private Error(final String description, final int code) {
            this.description = description;
            this.code = code;
        }

        public String getDescription() {
            return this.description;
        }

        public int getCode() {
            return this.code;
        }
    }

    private final Error error;

    public AugmentedCbaException(final Error error) {
        this.error = error;
    }

    public int getErrorCode() {
        return error.code;
    }

    @Override
    public String getMessage() {
        return error.description;
    }

}
