package com.sparta.myscheduler.dto;

import com.sparta.myscheduler.entity.Schedule;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ScheduleResponseDto {
    private long id;
    private String title;
    private String inCharge;
    private String password;
    private String dateCreated;
    private String dateEdited;
    private String notes;

    public ScheduleResponseDto(Schedule schedule) {
        this.id = schedule.getId();
        this.title = schedule.getTitle();
        this.inCharge = schedule.getInCharge();
        this.password = schedule.getPassword();
        this.notes = schedule.getNotes();
    }

    public ScheduleResponseDto(Long id, String title, String inCharge, String dateCreated, String dateEdited, String notes) {
        this.id = id;
        this.title = title;
        this.inCharge = inCharge;
        this.dateCreated = dateCreated;
        this.dateEdited = dateEdited;
        this.notes = notes;
    }
}
