package sparkboot.spec;

/**
 * Created by misha on 24.05.2016.
 */
public class HttpBuilderResult {
    private int responseCode;
    private String responseContent;


    public int getResponseCode() {
        return responseCode;
    }

    public HttpBuilderResult setResponseCode(int responseCode) {
        this.responseCode = responseCode;
        return this;
    }

    public String getResponseContent() {
        return responseContent;
    }

    public HttpBuilderResult setResponseContent(String responseContent) {
        this.responseContent = responseContent;
        return this;
    }
}
