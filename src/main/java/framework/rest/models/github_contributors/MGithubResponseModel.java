package framework.rest.models.github_contributors;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class MGithubResponseModel {

    private MGithubResponse[] responses;

    public MGithubResponseModel(String json) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        responses = mapper.readValue(json, MGithubResponse[].class);
    }

    public MGithubResponse[] getResponses() {
        return responses;
    }
}
