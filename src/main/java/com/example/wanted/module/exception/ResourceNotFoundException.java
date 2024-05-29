package com.example.wanted.module.exception;

public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException(String datasource, Long id){
        super(datasource + "에서 ID " + id + "를 찾을 수 없습니다.");
    }

    public ResourceNotFoundException(String datasource, String id) {
        super(datasource + "에서 ID " + id + "를 찾을 수 없습니다.");
    }

}
