package framework.rest.requests;

import framework.enums.RestMethod;
import framework.rest.responses.GetContributorsResponse;

public class GetContributorsRequest extends BasicRequest {

    public GetContributorsRequest(String companyName, String projectName) {
        this.request.setUrl(String.format("https://api.github.com/repos/%s/%s/stats/contributors", companyName, projectName));
        this.request.setMethod(RestMethod.GET);
    }

    public GetContributorsResponse execute(){
       return new GetContributorsResponse(this.request.executeBasic());
    }
}
