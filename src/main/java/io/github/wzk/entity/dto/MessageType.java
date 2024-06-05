package io.github.wzk.entity.dto;

public enum MessageType {
    TYPE_AUTH((byte)0),TYPE_LOGOUT((byte)1),TYPE_TEXT((byte)2),TYPE_EMPTY((byte)3),TYPE_FILE((byte)4),TYPE_IMAGE((byte)5),TYPE_VIDEO((byte)6),TYPE_AUDIO((byte)7),TYPE_LOCATION((byte)8),TYPE_STICKER((byte)9),TYPE_CONTACT((byte)10),TYPE_START((byte)11);
    private byte value;
    MessageType(byte value){
        this.value = value;
    }
    public byte value(){
        return this.value;
    }
}