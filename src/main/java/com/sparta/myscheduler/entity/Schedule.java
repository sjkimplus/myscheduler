package com.sparta.myscheduler.entity;

import com.sparta.myscheduler.dto.ScheduleRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class Schedule {
    private long id;
    private String title;
    private String inCharge;
    private String password;
    private String notes;

    public Schedule(ScheduleRequestDto requestDto) {
        this.id = requestDto.getId();
        this.title = requestDto.getTitle();
        this.inCharge = requestDto.getInCharge();
        this.password = requestDto.getPassword();
        this.notes = requestDto.getNotes();
    }
}
