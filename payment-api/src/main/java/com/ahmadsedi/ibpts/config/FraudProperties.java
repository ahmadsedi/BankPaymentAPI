package com.ahmadsedi.ibpts.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Ahmad R. Seddighi (ahmadseddighi@yahoo.com)
 * Date: 09/03/2025
 * Time: 10:44
 */
@ConfigurationProperties(prefix = "fraud")
public class FraudProperties {

    private boolean detection;

    public boolean isDetection() {
        return detection;
    }

    public void setDetection(boolean detection) {
        this.detection = detection;
    }
}
