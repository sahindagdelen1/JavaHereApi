package base;

import conf.ReturnMessages;

public class CustomResponse {
    private String responseAsJson;
    private ReturnMessages returnMessage;

    public CustomResponse(ReturnMessages returnMessage, String responseAsJson) {
        this.returnMessage = returnMessage;
        this.responseAsJson = responseAsJson;
    }

    public String getResponseAsJson() {
        return responseAsJson;
    }

    public void setResponseAsJson(String responseAsJson) {
        this.responseAsJson = responseAsJson;
    }

    public ReturnMessages getReturnMessage() {
        return returnMessage;
    }

    public void setReturnMessage(ReturnMessages returnMessage) {
        this.returnMessage = returnMessage;
    }
}
