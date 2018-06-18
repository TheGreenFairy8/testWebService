package webservice;

import java.util.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Date;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import javax.jws.WebService;


@WebService(endpointInterface = "webservice.WebServiceInterface")
public class WebServiceImpl implements WebServiceInterface {

    List<Test> testList = new ArrayList<Test>();

    public WebServiceImpl() {
    }

    public void addFeature(String featureName, String startTime) {
        Test feature = new Test(featureName);

        feature.setBeginTime(convertTime(startTime));
        testList.add(feature);
    }

    public void getTestTable() {
        new HtmlFunctions().createHtmlPage(testList);
    }

    public void setEndTime(String feature, String endTime) {
        for (Test test : testList) {
            if (test.getName().equals(feature)) {

                test.setEndTime(convertTime(endTime));
            }
        }
    }

    private Calendar convertTime(String time) {
        Calendar result = new GregorianCalendar();

        Date date = new Date();
        DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        try {
            date = formatter.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        result.setTime(date);

        return result;
    }
}
