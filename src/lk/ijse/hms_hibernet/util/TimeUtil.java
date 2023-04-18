package lk.ijse.hms_hibernet.util;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class TimeUtil {
    public static String getTime(){
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss "));
    }

    public static String getDate(){
        return new SimpleDateFormat("yyyy:MM:dd").format(new Date());
    }
}
