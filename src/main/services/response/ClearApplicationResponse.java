package services.response;

/*
Response class for the clear application service
 */
public class ClearApplicationResponse {
    /*
    message for any errors that occur
     */
    private String message;

    /*
    ClearApplication class constructor
     */
    public ClearApplicationResponse() {}

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
