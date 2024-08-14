package com.sparta.myscheduler.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ScheduleRequestDto {
    private long id;
    private String title;
    private String inCharge;
    private String password; // not sure if this should be included
    private String notes;
}
