package com.example.googleit.dto.response;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ResponseDto {
    private String status;
    private String message;
    private Object data;

    public ResponseDto(String status, String message, Object data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public ResponseDto(HttpStatus status, String message, Object data) {
        this.status = status.getReasonPhrase();
        this.message = message;
        this.data = data;
    }

    public ResponseDto(Object data) {
        this.status = HttpStatus.OK.toString();
        this.message = HttpStatus.OK.getReasonPhrase();
        this.data = data;
    }

    public ResponseDto() {
    }

    public String getStatus() {
        return this.status;
    }

    public String getMessage() {
        return this.message;
    }

    public Object getData() {
        return this.data;
    }

    public void setStatus(final String status) {
        this.status = status;
    }

    public void setMessage(final String message) {
        this.message = message;
    }

    public void setData(final Object data) {
        this.data = data;
    }

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof ResponseDto)) {
            return false;
        } else {
            ResponseDto other = (ResponseDto)o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                label47: {
                    Object this$status = this.getStatus();
                    Object other$status = other.getStatus();
                    if (this$status == null) {
                        if (other$status == null) {
                            break label47;
                        }
                    } else if (this$status.equals(other$status)) {
                        break label47;
                    }

                    return false;
                }

                Object this$message = this.getMessage();
                Object other$message = other.getMessage();
                if (this$message == null) {
                    if (other$message != null) {
                        return false;
                    }
                } else if (!this$message.equals(other$message)) {
                    return false;
                }

                Object this$data = this.getData();
                Object other$data = other.getData();
                if (this$data == null) {
                    if (other$data != null) {
                        return false;
                    }
                } else if (!this$data.equals(other$data)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(final Object other) {
        return other instanceof ResponseDto;
    }

    public String toString() {
        return "ResponseDTO(status=" + this.getStatus() + ", message=" + this.getMessage() + ", data=" + this.getData() + ")";
    }
}
