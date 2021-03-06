package com.mrhaki.grails.plugin.xframeoptions.config

import grails.config.Config
import groovy.transform.CompileStatic

import static com.mrhaki.grails.plugin.xframeoptions.web.XFrameOptionsHeaderValues.ALLOW_FROM
import static com.mrhaki.grails.plugin.xframeoptions.web.XFrameOptionsHeaderValues.DENY
import static com.mrhaki.grails.plugin.xframeoptions.web.XFrameOptionsHeaderValues.SAME_ORIGIN

@CompileStatic
class XFrameOptionsConfiguration {
    final static boolean DEFAULT_ENABLED = true
    final static boolean DEFAULT_DENY = false
    final static boolean DEFAULT_SAME_ORIGIN = false
    final static String DEFAULT_ALLOW_FROM = ''
    final static String DEFAULT_URL_PATTERN = '/*'

    boolean enabled = DEFAULT_ENABLED
    boolean deny = DEFAULT_DENY
    boolean sameOrigin = DEFAULT_SAME_ORIGIN
    String allowFrom = DEFAULT_ALLOW_FROM
    String urlPattern = DEFAULT_URL_PATTERN

    XFrameOptionsConfiguration() {}

    XFrameOptionsConfiguration(Config config) {
        final String prefix = 'grails.plugin.xframeoptions'
        enabled = config.getProperty("${prefix}.enabled", Boolean, DEFAULT_ENABLED)
        deny = config.getProperty("${prefix}.deny", Boolean, DEFAULT_DENY)
        sameOrigin = config.getProperty("${prefix}.sameOrigin", Boolean, DEFAULT_SAME_ORIGIN)
        allowFrom = config.getProperty("${prefix}.allowFrom", String, DEFAULT_ALLOW_FROM)
        urlPattern = config.getProperty("${prefix}.urlPattern", String, DEFAULT_URL_PATTERN)
    }

    boolean isFilterEnabled() {
        enabled || (hasCustomSettings() && !enabled)
    }

    private boolean hasCustomSettings() {
        deny || sameOrigin || allowFrom
    }

    /**
     * Determine response header value based on configuration properties.
     *
     * @return Response header value.
     */
    String determineHeaderValue() {
        if (deny) {
            return DENY
        }

        if (sameOrigin) {
            return SAME_ORIGIN
        }

        if (allowFrom) {
            return "${ALLOW_FROM} ${allowFrom}" as String
        }

        return DENY
    }
}
