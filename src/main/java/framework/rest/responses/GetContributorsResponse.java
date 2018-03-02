package framework.rest.responses;

import framework.rest.RestResponseStructure;
import framework.rest.models.github_contributors.MGithubResponseModel;

import java.io.IOException;

public class GetContributorsResponse extends BasicResponse {

    private MGithubResponseModel model = null;

    public GetContributorsResponse(RestResponseStructure response) {
        super(response);
    }

    public MGithubResponseModel getModel() throws IOException {
        if (model == null)
            model = new MGithubResponseModel(this.response.getBody());
        return model;
    }
}