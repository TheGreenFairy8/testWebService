package webservice;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@WebService
public interface WebServiceInterface {

    @WebMethod
    public void getTestTable();

    @WebMethod
    public void addFeature(String featureName, String startTime);

    @WebMethod
    public void setEndTime(String feature, String endTime);
}
