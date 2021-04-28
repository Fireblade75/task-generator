package nl.firepy.taskgenerator.common.errors.exceptions;

public class TaskGenAppException extends RuntimeException {
    public TaskGenAppException(String message) {
        super(message);
    }

    public TaskGenAppException(Exception e) {
        super(e);
    }
}
