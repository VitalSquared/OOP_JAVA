package ru.nsu.spirin.chess.game.message;

import java.io.Serializable;

public final class Message implements Serializable {
    private final MessageType type;
    private final Object content;

    public Message(MessageType type, Object content) {
        this.type = type;
        this.content = content;
    }

    public MessageType getType() {
        return this.type;
    }

    public Object getContent() {
        return this.content;
    }
}
