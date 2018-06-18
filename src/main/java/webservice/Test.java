package webservice;

import java.util.Calendar;

public class Test {

    private String name;
    private Calendar beginTime;
    private Calendar endTime;

    Test(String testName) {
        name = testName;
    }


    public String getName() {
        return name;
    }

    public Calendar getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Calendar time) {
        beginTime = time;
    }

    public Calendar getEndTime() {
        return endTime;
    }

    public void setEndTime(Calendar time) {
        endTime = time;
    }
}
