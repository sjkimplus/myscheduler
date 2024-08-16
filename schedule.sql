USE myscheduler;

CREATE TABLE IF NOT EXISTS SCHEDULE
(
    schedule_id BIGINT NOT NULL AUTO_INCREMENT COMMENT 'schedule_id',
    PRIMARY KEY (schedule_id),

    title VARCHAR(500) NOT NULL COMMENT 'schedule title',
    in_charge VARCHAR(50) NOT NULL COMMENT 'person in charge',
    password VARCHAR(50) NOT NULL COMMENT 'password',
    time_created DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT 'time created',
    time_edited DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'time last edited',
    notes TEXT COMMENT 'notes'
    );
