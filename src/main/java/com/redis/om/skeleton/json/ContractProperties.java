package com.redis.om.skeleton.json;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "contract.properties")
public class ContractProperties {
    static public int eurCash = 12087792;
    static public int eurCfd = 143916318;
}