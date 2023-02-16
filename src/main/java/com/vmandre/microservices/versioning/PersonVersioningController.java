package com.vmandre.microservices.versioning;

import lombok.Getter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonVersioningController {

    // URI versioning - It pollutes the URI as we're creating new URIs

    @GetMapping("/v1/person")
    public PersonV1 personV1() {
        return new PersonV1(("Bob Charlie"));
    }

    @GetMapping("/v2/person")
    public PersonV2 personV2() {
        return new PersonV2(new Name("Bob", "Charlie"));
    }


    // Request parameter versioning - It pollutes the URI as we're creating new URI parameters

    @GetMapping(value = "/person/param", params = "version=1")
    public PersonV1 paramV1() {
        return new PersonV1(("Bob Charlie"));
    }

    @GetMapping(value = "/person/param", params = "version=2")
    public PersonV2 paramV2() {
        return new PersonV2(new Name("Bob", "Charlie"));
    }


    // Headers versioning - It doesn't pollute the URI as it is the same
    // for both and just change the header parameter

    @GetMapping(value = "/person/header", headers = "X-API-VERSION=1")
    public PersonV1 headerV1() {
        return new PersonV1(("Bob Charlie"));
    }

    @GetMapping(value = "/person/header", headers = "X-API-VERSION=2")
    public PersonV2 headerV2() {
        return new PersonV2(new Name("Bob", "Charlie"));
    }
    
    // Media type versioning (a.k.a "content negotiation" or "accept header")
    // It doesn't pollute the URI as it is the same
    // for both and just change the header parameter

    @GetMapping(value = "/person/produces", produces = "application/vnd.company.app-v1+json")
    public PersonV1 producesV1() {
        return new PersonV1(("Bob Charlie"));
    }

    @GetMapping(value = "/person/produces", produces = "application/vnd.company.app-v2+json")
    public PersonV2 producesV2() {
        return new PersonV2(new Name("Bob", "Charlie"));
    }
}
