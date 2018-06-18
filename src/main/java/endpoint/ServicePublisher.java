package endpoint;

import javax.xml.ws.Endpoint;

import webservice.WebServiceImpl;

public class ServicePublisher {

    final static public void main(String args[])
    {
        Endpoint.publish("http://localhost:1986/wss/testservice", new WebServiceImpl());
    }
}
