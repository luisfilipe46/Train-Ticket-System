package com.example.joao.revisor_app;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by joao on 09-11-2015.
 */
public class StationsMap {
    HashMap<String, String> stationsMap = new HashMap<String, String>();
    private static StationsMap ourInstance = new StationsMap();

    public static StationsMap getInstance() {
        return ourInstance;
    }

    private StationsMap() {
        stationsMap.put("11","S. Joao");
        stationsMap.put("12","IPO");
        stationsMap.put("21","Aliados");
        stationsMap.put("22","Faria Guimaraes");
        stationsMap.put("31", "Azurara");
        stationsMap.put("32", "Vila do Conde");
        stationsMap.put("01", "Trindade");
    }

    public String getStationRealName(String idStation)
    {
        return stationsMap.get(idStation);
    }

    public String getStationId( String stationRealName) {

        for (String o : stationsMap.keySet()) {
            if (stationsMap.get(o).equals(stationRealName)) {
                return o;
            }
        }
        return null;
    }
}
