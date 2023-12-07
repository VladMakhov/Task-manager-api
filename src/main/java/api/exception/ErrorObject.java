package api.exception;

import lombok.Data;

import java.util.Date;

/**
 * Concise representation of Exception or Error that users see
 * */
@Data
public class ErrorObject {
    private Integer statusCode;
    private String message;
    private Date timestamp;
}
