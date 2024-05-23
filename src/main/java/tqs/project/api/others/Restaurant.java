package tqs.project.api.others;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import lombok.Getter;

// Opted to not add Setters since it does not fit our purpose here.
@Getter
public class Restaurant {
    private Long totalTables;

    private List<LocalTime> dailySlots; 

    // Restaurant opens at 11:00 and closes at 15:00. It re-opens at 18:00 up until 23:00
    public Restaurant(){
        this.totalTables = 10L;
        this.dailySlots = new LinkedList<>(Arrays.asList(
            LocalTime.of(11, 0),
            LocalTime.of(12, 0),
            LocalTime.of(13, 0),
            LocalTime.of(14, 0),
            LocalTime.of(18, 0),
            LocalTime.of(19, 0),
            LocalTime.of(20, 0),
            LocalTime.of(21, 0),
            LocalTime.of(22, 0)
        ));
    }
}
