package com.example.wanted.module.exception;

public class ResourceAlreadyException extends RuntimeException{
    public ResourceAlreadyException(String datasource, String type, String data){
        super(datasource + "에서 " + type + " " + data + "가 이미 존재합니다.");
    }

    public ResourceAlreadyException(String datasource, Long id){
        super(datasource + "에서 ID " + id + "가 이미 존재합니다.");
    }
}
