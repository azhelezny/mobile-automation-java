package framework.rest.requests;

import framework.rest.RestRequestStructure;

class BasicRequest {
    protected RestRequestStructure request;

    public BasicRequest() {
        this.request = new RestRequestStructure();
    }
}
