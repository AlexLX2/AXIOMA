package md.akdev_service_management.sm.exceptions;
public class CstErrorResponse {
    private String result;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public CstErrorResponse(String result) {
        this.result = result;
    }
}