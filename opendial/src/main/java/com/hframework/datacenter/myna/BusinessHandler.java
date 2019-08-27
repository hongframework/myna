package com.hframework.datacenter.myna;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public interface BusinessHandler{
    public Map<String, Object> handle(Map<String, Object> input);
}