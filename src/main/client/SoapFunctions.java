import javax.xml.soap.*;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;

public class SoapFunctions {

    private String namespaceURI = null;
    private String serviceName = null;
    private String namespace = null;
    private String soapAction = null;
    private String arg0;
    private String arg1;

    private String serviceUrl;

    public void setSoapParams(String namespaceURI, String serviceName, String serviceUrl) {
        this.namespaceURI = namespaceURI;
        this.serviceUrl = serviceUrl;
        this.serviceName = serviceName;
        namespace = "web"; // namespace
        soapAction = namespaceURI + "/" + serviceName;
    }

    public void setArguments(String arg0, String arg1)
    {
        this.arg0 = arg0;
        this.arg1 = arg1;
    }

    public void createSoapEnvelope(SOAPMessage soapMessage) throws SOAPException {

        SOAPPart soapPart = soapMessage.getSOAPPart();

        // SOAP Envelope
        SOAPEnvelope envelope = soapPart.getEnvelope();
        envelope.addNamespaceDeclaration(namespace, namespaceURI);

        // SOAP Body
        SOAPBody soapBody = envelope.getBody();
        SOAPElement soapBodyElem;
        soapBodyElem = soapBody.addChildElement(serviceName, namespace);

        if (!arg0.isEmpty()) {
            SOAPElement arg0Element = soapBodyElem.addChildElement("arg0");
            arg0Element.addTextNode(arg0);
        }
        if(!arg1.isEmpty())
        {
            SOAPElement arg1Element = soapBodyElem.addChildElement("arg1");
            arg1Element.addTextNode(arg1);
        }
    }

    public SOAPMessage createSOAPRequest(String soapAction) throws Exception {
        MessageFactory messageFactory = MessageFactory.newInstance();
        SOAPMessage soapMessage = messageFactory.createMessage();

        createSoapEnvelope(soapMessage);

        MimeHeaders headers = soapMessage.getMimeHeaders();
        headers.addHeader("SOAPAction", soapAction);

        soapMessage.saveChanges();

        // Печать XML текста запросаА
        System.out.println("Request SOAP Message:");
        soapMessage.writeTo(System.out);
        System.out.println("\n");

        return soapMessage;
    }

    public SOAPMessage callSoapWebService() {
        SOAPConnectionFactory soapFactory = null;
        SOAPConnection soapConnect = null;
        SOAPMessage soapRequest = null;
        SOAPMessage soapResponse = null;
        try {
            // Создание SOAP Connection
            soapFactory = SOAPConnectionFactory.newInstance();
            soapConnect = soapFactory.createConnection();

            // Создание SOAP Message для отправки
            soapRequest = createSOAPRequest(soapAction);
            // Получение SOAP Message
            soapResponse = soapConnect.call(soapRequest, serviceUrl);

            // Печать SOAP Response
            printSOAPMessage(soapResponse);

            soapConnect.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return soapResponse;
    }

    private SOAPElement findSoapElement(SOAPElement parentElement, String elementNameToFind) {
        SOAPElement element;

        String nodeValue = parentElement.getNodeValue();
        if (parentElement.hasChildNodes() && nodeValue == null)
            element = (SOAPElement) parentElement.getFirstChild();
        else
            element = (SOAPElement) parentElement.getNextSibling();
        if (element.getNodeName().equals(elementNameToFind))
            return element;
        else if (parentElement.getChildElements().hasNext())
            return findSoapElement(element, elementNameToFind);
        else
            return null;
    }

    private void printSOAPMessage(SOAPMessage soapResponse) {
        TransformerFactory transformerFactory;
        Transformer transformer;
        try {
            // Создание XSLT-процессора
            transformerFactory = TransformerFactory.newInstance();
            transformer = transformerFactory.newTransformer();
            // Получение содержимого ответа
            Source content;
            content = soapResponse.getSOAPPart().getContent();
            // Определение выходного потока
            StreamResult result = new StreamResult(System.out);
            transformer.transform(content, result);
            System.out.println();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
