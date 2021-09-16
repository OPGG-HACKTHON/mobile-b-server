package co.mobile.b.server.enums;

public enum MessageType {
    CHAT(""),
    JOIN(" 님이 입장하셨습니다."),
    LEAVE(" 님이 퇴장하셨습니다.");

    private String message;

    MessageType(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
