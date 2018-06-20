package com.uberall.grafana

import com.uberall.jenkins.JenkinsJob

class QueryResponse {

    String target
    List<List> datapoints

    static QueryResponse fromJenkins(JenkinsJob status) {
        long dp = 0
        if (status.result == JenkinsJob.Status.RUNNING) {
            def runningFor = System.currentTimeMillis() - status.timestamp
            dp = status.estimatedDuration - runningFor
        }

        new QueryResponse(
                target: status.target,
                datapoints: [[dp, status.timestamp]]
        )
    }

}
