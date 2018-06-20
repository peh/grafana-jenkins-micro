package com.uberall

import com.uberall.grafana.QueryRequest
import com.uberall.grafana.QueryResponse
import com.uberall.grafana.SearchRequest
import com.uberall.jenkins.JenkinsService
import groovy.util.logging.Slf4j
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post

import javax.inject.Inject

@Controller("/")
@Slf4j
class GrafanaController {

    @Inject
    JenkinsService jenkinsService

    @Get("/")
    HttpStatus index() {
        return HttpStatus.OK
    }

    @Post("/search")
    List<String> search(SearchRequest searchRequest) {
        List<String> jobs = jenkinsService.getJobList()
        jobs.findAll { it.toLowerCase().contains(searchRequest.target.toLowerCase()) }.sort()
    }

    @Post("/query")
    List<QueryResponse> query(Map payload) {
        QueryRequest q = QueryRequest.fromMap(payload)
        q.targets.collect { target ->
            QueryResponse.fromJenkins(jenkinsService.getLastJobBuild(target.target))
        }
    }

    @Get("/annotations")
    HttpStatus annotations(HttpRequest r) {
        return HttpStatus.NOT_IMPLEMENTED
    }

}
