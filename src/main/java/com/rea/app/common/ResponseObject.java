package com.rea.app.common;

import com.rea.app.common.model.Response;
import org.springframework.http.HttpStatus;

public class ResponseObject {
    public static <T> Response<T> dataNotSavedOrUpdate(Response<T> response){
        response.setStatus(400);
        response.setSuccess(false);
        response.setMessage("Data Not Saved or Updated. Please check your input data!!");
        response.setData(null);
        return response;
    }

    public static <T> Response<T> dataSavedOrUpdateSuccess(Response<T> response, T data, int code){
        response.setStatus(code);
        response.setSuccess(true);
        response.setMessage("Data Saved or Updated!!");
        response.setData(data);
        return response;
    }

    public static <T> Response<T> dataFoundSuccess(Response<T> response, T data){
        response.setStatus(200);
        response.setSuccess(true);
        response.setMessage("Data Found!!");
        response.setData(data);
        return response;
    }

    public static <T> Response<T> dataNotFound(Response<T> response, T data){
        response.setStatus(404);
        response.setSuccess(false);
        response.setMessage("Resource Not Found!!");
        response.setData(null);
        return response;
    }

    public static <T> Response<T> fileExtensionNotMatched(Response<T> response){
        response.setStatus(400);
        response.setSuccess(false);
        response.setMessage("File Format not supported!!");
        return response;
    }

    public static <T> Response<T> fileNotSaved(Response<T> response){
        response.setStatus(500);
        response.setSuccess(false);
        response.setMessage("File Not Saved!!");
        return response;
    }

    public static <T> Response<T> fileNotDeleted(Response<T> response){
        response.setStatus(500);
        response.setSuccess(false);
        response.setMessage("File Could not be removed!!");
        return response;
    }

    public static <T> Response<T> dataDeletedSuccess(Response<T> response){
        response.setStatus(200);
        response.setSuccess(true);
        response.setMessage("Data Deleted!!");
        return response;
    }

    public static <T> Response<T> dataSavedSuccess(Response<T> response){
        response.setStatus(200);
        response.setSuccess(true);
        response.setMessage("Data Saved!!");
        return response;
    }
}
