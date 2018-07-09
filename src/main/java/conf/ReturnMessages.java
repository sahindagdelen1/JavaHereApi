package conf;

public enum ReturnMessages {

    OK(200, "Successful"),
    NOTFOUND(404, "Not found");

    private int returnCode;
    private String returnMessage;

    ReturnMessages(int returnCode, String returnMessage) {
        this.returnCode = returnCode;
        this.returnMessage = returnMessage;
    }

}
