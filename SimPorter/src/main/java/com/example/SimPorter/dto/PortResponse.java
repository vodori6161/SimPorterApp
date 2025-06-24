package com.example.SimPorter.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class PortResponse {
    private boolean success;
    private String message;


    // this is the success response factory method
    public static PortResponse success(String message)
    {
        return new PortResponse(true,message);
    }

    public static PortResponse error(String message)
    {
        return new PortResponse(false,message);
    }

}
