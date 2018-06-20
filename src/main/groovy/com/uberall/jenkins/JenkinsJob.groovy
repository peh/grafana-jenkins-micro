package com.uberall.jenkins

class JenkinsJob {
    String target
    long estimatedDuration
    long duration
    long timestamp
    Status result

    static JenkinsJob fromJenkins(String target, Map json) {
        return new JenkinsJob(
                target: target,
                result: json.result ? Status.valueOf(json.result) : Status.RUNNING,
                timestamp: json.timestamp,
                duration: json.duration,
                estimatedDuration: json.estimatedDuration,
        )
    }

    static enum Status {
        FAILURE,
        SUCCESS,
        RUNNING
    }
}
