package dev.hafnerp.runnable;

public abstract class InvalidMove implements Runnable {

    private String message = "";

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
