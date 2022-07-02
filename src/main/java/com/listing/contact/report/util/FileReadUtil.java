package com.listing.contact.report.util;

import com.listing.contact.report.enums.APIErrorCode;
import com.listing.contact.report.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class FileReadUtil {
    private static final Logger LOG = LoggerFactory.getLogger(FileReadUtil.class);

    public static URI getUri(String path) {
        URL resourceUrl = FileReadUtil.class.getClassLoader().getResource(path);
        URI uri = null;

        if (resourceUrl == null) {
            LOG.error("No resource found in path: {}", path);
            throw new ResourceNotFoundException(APIErrorCode.RESOURCE_NOT_FOUND);
        }

        try {
            uri = resourceUrl.toURI();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        return uri;
    }
}
