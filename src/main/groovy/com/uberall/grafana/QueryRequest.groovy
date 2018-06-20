package com.uberall.grafana

class QueryRequest {
    int panelId
    String interval
    long intervalMs
    List<QueryRequestTarget> targets
    QueryRequstRange range
    QueryRequstRangeRaw rangeRaw
    int maxDataPoints
    String timezone
    Map scopedVars

    class QueryRequstRange {
        String from
        String to
        QueryRequstRangeRaw raw
    }

    class QueryRequestTarget {
        String target
        String refId
        String type
    }

    class QueryRequstRangeRaw {
        String from
        String to
    }

    static QueryRequest fromMap(Map map) {
        return new QueryRequest(map)
    }
}
