package com.uberall.jenkins

import io.micronaut.context.annotation.Value
import io.micronaut.http.HttpRequest
import io.micronaut.http.client.RxHttpClient

import javax.inject.Singleton

@Singleton
class JenkinsService {

    @Value('${application.jenkins.baseurl}')
    String jenkinsBaseUrl

    @Value('${application.jenkins.user}')
    String jenkinsUser

    @Value('${application.jenkins.apikey}')
    String jenkinsApiKey

    RxHttpClient httpClient

    List<String> getJobList() {
        Map resp = client.retrieve(
                HttpRequest.GET("/api/json")
                        .basicAuth(jenkinsUser, jenkinsApiKey),
                Map.class
        ).blockingFirst()


        resp.jobs.collect { it.name } as List<String>
    }

    JenkinsJob getLastJobBuild(String target) {
        Map resp = client.retrieve(
                HttpRequest.GET("/job/$target/lastBuild/api/json")
                        .basicAuth(jenkinsUser, jenkinsApiKey),
                Map.class
        ).blockingFirst()

        JenkinsJob.fromJenkins(target, resp)
    }

    RxHttpClient getClient() {
        if (!httpClient) {
            httpClient = RxHttpClient.create(new URL(jenkinsBaseUrl))
        }
        httpClient
    }
}
