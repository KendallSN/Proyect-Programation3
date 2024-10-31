package cr.ac.una.sigeceunasecurityws.controller;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import jakarta.xml.ws.WebEndpoint;
import jakarta.xml.ws.WebServiceClient;
import jakarta.xml.ws.WebServiceFeature;
import jakarta.xml.ws.Service;

/**
 * This class was generated by Apache CXF 4.0.2
 * 2024-10-31T16:00:38.859-06:00
 * Generated source version: 4.0.2
 *
 */
@WebServiceClient(name = "SystemsController",
                  wsdlLocation = "http://localhost:8080/SigeceUNASecurityWs/SystemsController?wsdl",
                  targetNamespace = "http://controller.sigeceunasecurityws.una.ac.cr/")
public class SystemsController_Service extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("http://controller.sigeceunasecurityws.una.ac.cr/", "SystemsController");
    public final static QName SystemsControllerPort = new QName("http://controller.sigeceunasecurityws.una.ac.cr/", "SystemsControllerPort");
    static {
        URL url = null;
        try {
            url = new URL("http://localhost:8080/SigeceUNASecurityWs/SystemsController?wsdl");
        } catch (MalformedURLException e) {
            java.util.logging.Logger.getLogger(SystemsController_Service.class.getName())
                .log(java.util.logging.Level.INFO,
                     "Can not initialize the default wsdl from {0}", "http://localhost:8080/SigeceUNASecurityWs/SystemsController?wsdl");
        }
        WSDL_LOCATION = url;
    }

    public SystemsController_Service(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public SystemsController_Service(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public SystemsController_Service() {
        super(WSDL_LOCATION, SERVICE);
    }

    public SystemsController_Service(WebServiceFeature ... features) {
        super(WSDL_LOCATION, SERVICE, features);
    }

    public SystemsController_Service(URL wsdlLocation, WebServiceFeature ... features) {
        super(wsdlLocation, SERVICE, features);
    }

    public SystemsController_Service(URL wsdlLocation, QName serviceName, WebServiceFeature ... features) {
        super(wsdlLocation, serviceName, features);
    }




    /**
     *
     * @return
     *     returns SystemsController
     */
    @WebEndpoint(name = "SystemsControllerPort")
    public SystemsController getSystemsControllerPort() {
        return super.getPort(SystemsControllerPort, SystemsController.class);
    }

    /**
     *
     * @param features
     *     A list of {@link jakarta.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns SystemsController
     */
    @WebEndpoint(name = "SystemsControllerPort")
    public SystemsController getSystemsControllerPort(WebServiceFeature... features) {
        return super.getPort(SystemsControllerPort, SystemsController.class, features);
    }

}
