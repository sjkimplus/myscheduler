package com.sparta.myscheduler.controller;

import com.sparta.myscheduler.dto.ScheduleRequestDto;
import com.sparta.myscheduler.dto.ScheduleResponseDto;
import com.sparta.myscheduler.entity.Schedule;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.web.bind.annotation.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ScheduleController {
    private final JdbcTemplate jdbcTemplate;

    public ScheduleController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // level 1 : 일정 포스팅
    @PostMapping("/schedule")
    public ScheduleResponseDto createSchedule(@RequestBody ScheduleRequestDto requestDto) {
        // test
        // RequestDto -> Entity
        Schedule schedule = new Schedule(requestDto);
        System.out.println(schedule);
        // DB 저장
        KeyHolder keyHolder = new GeneratedKeyHolder(); // 기본 키를 반환받기 위한 객체

        String sql = "INSERT INTO schedule (title, in_charge, password, notes) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update( con -> {
                    PreparedStatement preparedStatement = con.prepareStatement(sql,
                            Statement.RETURN_GENERATED_KEYS);

                    preparedStatement.setString(1, schedule.getTitle());
                    preparedStatement.setString(2, schedule.getInCharge());
                    preparedStatement.setString(3, schedule.getPassword());
                    preparedStatement.setString(4, schedule.getNotes());
                    return preparedStatement;
                },
                keyHolder);

        // DB Insert 후 받아온 기본키 확인
        Long id = keyHolder.getKey().longValue();
        schedule.setId(id);

        // Entity -> ResponseDto
        ScheduleResponseDto scheduleResponseDto = new ScheduleResponseDto(schedule);

        return scheduleResponseDto;
    }

    // level 2 : 특정일정 조회
    @GetMapping("/schedule/{id}")
    public ScheduleResponseDto getSchedule(@PathVariable Long id) {
        // DB 조회
        String sql = "SELECT * FROM schedule WHERE schedule_id = ?";

        // locate the specific object
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, new RowMapper<ScheduleResponseDto>() {
            @Override
            public ScheduleResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                // SQL 의 결과로 받아온 Schedule 데이터들을 ScheduleResponseDto 타입으로 변환해줄 메서드
                Long schedule_id = rs.getLong("schedule_id");
                String title = rs.getString("title");
                String inCharge = rs.getString("in_charge");
                String time_created = rs.getString("time_created");
                String time_edited = rs.getString("time_edited");
                String notes = rs.getString("notes");

                return new ScheduleResponseDto(schedule_id, title, inCharge, time_created, time_edited, notes);
            }
        });
    }

    // level 3 : 모든일정 조회
    @GetMapping("/schedule")
    public List<ScheduleResponseDto> getSchedules() {
        // DB 조회
        String sql = "SELECT * FROM schedule";

        return jdbcTemplate.query(sql, new RowMapper<ScheduleResponseDto>() {
            @Override
            public ScheduleResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                // SQL 의 결과로 받아온 Schedule 데이터들을 ScheduleResponseDto 타입으로 변환해줄 메서드
                Long schedule_id = rs.getLong("schedule_id");
                String title = rs.getString("title");
                String inCharge = rs.getString("in_charge");
                String time_created = rs.getString("time_created");
                String time_edited = rs.getString("time_edited");
                String notes = rs.getString("notes");

                return new ScheduleResponseDto(schedule_id, title, inCharge, time_created, time_edited, notes);
            }
        });
    }

    // level 4: 특정 일정 수정
    @PutMapping("/schedule/{id}")
    public ScheduleResponseDto updateSchedule(@PathVariable Long id, @RequestBody ScheduleRequestDto requestDto) {
        if(scheduleIsValid(id, requestDto.getPassword())) {
            // memo 내용 수정
            String sql = "UPDATE schedule SET title = ?, in_charge = ?, notes = ? WHERE schedule_id = ?";
            jdbcTemplate.update(sql, requestDto.getTitle(), requestDto.getInCharge(), requestDto.getNotes(), id);
            return getSchedule(id);
        } else {
            throw new IllegalArgumentException("선택한 일정은 존재하지 않거나 비밀번호가 일치하지 않습니다.");
        }
    }

    private boolean scheduleIsValid(Long id, String password) {
        // DB 조회
        String sql = "SELECT COUNT(*) FROM schedule WHERE schedule_id = ? AND password = ?";

        // Execute the query and return true if count > 0, otherwise false
        Integer count = jdbcTemplate.queryForObject(sql, new Object[]{id, password}, Integer.class);
        return count != null && count > 0;
    }

    // level 5: 특정일정 삭제
    @DeleteMapping("/schedule/{id}")
    public void deleteSchedule(@PathVariable Long id, @RequestParam String password) {
        if (scheduleIsValid(id, password)) {
            String sql = "DELETE FROM schedule WHERE schedule_id = ?";
            jdbcTemplate.update(sql, id);
        } else {
            throw new IllegalArgumentException("선택한 일정은 존재하지 않거나 비밀번호가 일치하지 않습니다.");
        }

    }
}
