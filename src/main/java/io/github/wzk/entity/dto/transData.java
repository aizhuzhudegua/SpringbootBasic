package io.github.wzk.entity.dto;

import java.io.Serializable;

public class transData<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    private String ID;
    private String targetID;
    private String fileName;
    private String type;
    private byte msgType;
    private T data;
    private long SendTime;

    public transData(String id, String targetID, T data, String fileNameWithoutExtension, byte msgType) {
        this.ID = id;
        this.targetID = targetID;
        this.data = data;
        this.fileName = fileNameWithoutExtension;
        this.msgType = msgType;
    }
    public transData(T data){
        this.data = data;
    }
    @Override
    public String toString() {
        return "transData{" +
                "ID='" + ID + '\'' +
                ", targetID='" + targetID + '\'' +
                ", fileName='" + fileName + '\'' +
                ", type='" + type + '\'' +
                ", msgType='" + msgType + '\'';
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getTargetID() {
        return targetID;
    }

    public void setTargetID(String targetID) {
        this.targetID = targetID;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public byte getMsgType() {
        return msgType;
    }

    public void setMsgType(byte msgType) {
        this.msgType = msgType;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public long getSendTime() {
        return SendTime;
    }

    public void setSendTime(long sendTime) {
        SendTime = sendTime;
    }
}
