package com.hframework.datacenter.myna.parser.date;

import opendial.bn.values.Value;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class MapVal<K, V> implements Value {
    final Map<K, V> map ;
    final int hashcode;

    public MapVal(Map<K, V> map) {
        this.map = map;
        hashcode = map.hashCode();
    }

    @Override
    public Value copy() {
        return new MapVal(new HashMap(map));
    }

    @Override
    public boolean contains(Value subvalue) {
        return false;
    }

    @Override
    public Collection<Value> getSubValues() {
        return null;
    }

    @Override
    public Value concatenate(Value value) {
        return null;
    }

    @Override
    public int length() {
        return map.size();
    }

    @Override
    public int compareTo(Value o) {
        return 0;
    }

    public void add(K key, V value) {
        map.put(key, value);
    }

    public Map<K, V> getMap(){
        return map;
    }

}
