package com.demo.adnetwork.importer.exception;

public class UrlResourceIoException extends RuntimeException {
    private String url;

    public UrlResourceIoException(final String url, final Throwable throwable) {
        super(throwable);
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
