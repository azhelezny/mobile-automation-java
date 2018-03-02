package framework.rest.responses;

import framework.rest.RestResponseStructure;

class BasicResponse {
    protected RestResponseStructure response;

    protected BasicResponse(RestResponseStructure response) {
        this.response = response;
    }

    public RestResponseStructure getResponse() {
        return response;
    }
}
