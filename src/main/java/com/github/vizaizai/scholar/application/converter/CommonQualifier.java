package com.github.vizaizai.scholar.application.converter;

import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * @author liaochongwei
 * @date 2020/8/14 14:29
 */
@Component
public class CommonQualifier {

    @Named("dateTimeToTimestamp")
    public long localDateToTimestamp(LocalDateTime dateTime) {
        if (dateTime == null) {
            return 0;
        }
        return dateTime.toInstant(ZoneOffset.of("+8")).toEpochMilli();
    }
}
