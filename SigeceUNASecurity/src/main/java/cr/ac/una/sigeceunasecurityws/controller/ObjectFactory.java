
package cr.ac.una.sigeceunasecurityws.controller;

import javax.xml.namespace.QName;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.annotation.XmlElementDecl;
import jakarta.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the cr.ac.una.sigeceunasecurityws.controller package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _DeleteSystemByID_QNAME = new QName("http://controller.sigeceunasecurityws.una.ac.cr/", "deleteSystemByID");
    private final static QName _DeleteSystemByIDResponse_QNAME = new QName("http://controller.sigeceunasecurityws.una.ac.cr/", "deleteSystemByIDResponse");
    private final static QName _GetSystemById_QNAME = new QName("http://controller.sigeceunasecurityws.una.ac.cr/", "getSystemById");
    private final static QName _GetSystemByIdResponse_QNAME = new QName("http://controller.sigeceunasecurityws.una.ac.cr/", "getSystemByIdResponse");
    private final static QName _GetSystems_QNAME = new QName("http://controller.sigeceunasecurityws.una.ac.cr/", "getSystems");
    private final static QName _GetSystemsByID_QNAME = new QName("http://controller.sigeceunasecurityws.una.ac.cr/", "getSystemsByID");
    private final static QName _GetSystemsByIDResponse_QNAME = new QName("http://controller.sigeceunasecurityws.una.ac.cr/", "getSystemsByIDResponse");
    private final static QName _GetSystemsResponse_QNAME = new QName("http://controller.sigeceunasecurityws.una.ac.cr/", "getSystemsResponse");
    private final static QName _SaveSystem_QNAME = new QName("http://controller.sigeceunasecurityws.una.ac.cr/", "saveSystem");
    private final static QName _SaveSystemResponse_QNAME = new QName("http://controller.sigeceunasecurityws.una.ac.cr/", "saveSystemResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: cr.ac.una.sigeceunasecurityws.controller
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link DeleteSystemByID }
     * 
     */
    public DeleteSystemByID createDeleteSystemByID() {
        return new DeleteSystemByID();
    }

    /**
     * Create an instance of {@link DeleteSystemByIDResponse }
     * 
     */
    public DeleteSystemByIDResponse createDeleteSystemByIDResponse() {
        return new DeleteSystemByIDResponse();
    }

    /**
     * Create an instance of {@link GetSystemById }
     * 
     */
    public GetSystemById createGetSystemById() {
        return new GetSystemById();
    }

    /**
     * Create an instance of {@link GetSystemByIdResponse }
     * 
     */
    public GetSystemByIdResponse createGetSystemByIdResponse() {
        return new GetSystemByIdResponse();
    }

    /**
     * Create an instance of {@link GetSystems }
     * 
     */
    public GetSystems createGetSystems() {
        return new GetSystems();
    }

    /**
     * Create an instance of {@link GetSystemsByID }
     * 
     */
    public GetSystemsByID createGetSystemsByID() {
        return new GetSystemsByID();
    }

    /**
     * Create an instance of {@link GetSystemsByIDResponse }
     * 
     */
    public GetSystemsByIDResponse createGetSystemsByIDResponse() {
        return new GetSystemsByIDResponse();
    }

    /**
     * Create an instance of {@link GetSystemsResponse }
     * 
     */
    public GetSystemsResponse createGetSystemsResponse() {
        return new GetSystemsResponse();
    }

    /**
     * Create an instance of {@link SaveSystem }
     * 
     */
    public SaveSystem createSaveSystem() {
        return new SaveSystem();
    }

    /**
     * Create an instance of {@link SaveSystemResponse }
     * 
     */
    public SaveSystemResponse createSaveSystemResponse() {
        return new SaveSystemResponse();
    }

    /**
     * Create an instance of {@link SystemsDto }
     * 
     */
    public SystemsDto createSystemsDto() {
        return new SystemsDto();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteSystemByID }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link DeleteSystemByID }{@code >}
     */
    @XmlElementDecl(namespace = "http://controller.sigeceunasecurityws.una.ac.cr/", name = "deleteSystemByID")
    public JAXBElement<DeleteSystemByID> createDeleteSystemByID(DeleteSystemByID value) {
        return new JAXBElement<DeleteSystemByID>(_DeleteSystemByID_QNAME, DeleteSystemByID.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteSystemByIDResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link DeleteSystemByIDResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://controller.sigeceunasecurityws.una.ac.cr/", name = "deleteSystemByIDResponse")
    public JAXBElement<DeleteSystemByIDResponse> createDeleteSystemByIDResponse(DeleteSystemByIDResponse value) {
        return new JAXBElement<DeleteSystemByIDResponse>(_DeleteSystemByIDResponse_QNAME, DeleteSystemByIDResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSystemById }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link GetSystemById }{@code >}
     */
    @XmlElementDecl(namespace = "http://controller.sigeceunasecurityws.una.ac.cr/", name = "getSystemById")
    public JAXBElement<GetSystemById> createGetSystemById(GetSystemById value) {
        return new JAXBElement<GetSystemById>(_GetSystemById_QNAME, GetSystemById.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSystemByIdResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link GetSystemByIdResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://controller.sigeceunasecurityws.una.ac.cr/", name = "getSystemByIdResponse")
    public JAXBElement<GetSystemByIdResponse> createGetSystemByIdResponse(GetSystemByIdResponse value) {
        return new JAXBElement<GetSystemByIdResponse>(_GetSystemByIdResponse_QNAME, GetSystemByIdResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSystems }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link GetSystems }{@code >}
     */
    @XmlElementDecl(namespace = "http://controller.sigeceunasecurityws.una.ac.cr/", name = "getSystems")
    public JAXBElement<GetSystems> createGetSystems(GetSystems value) {
        return new JAXBElement<GetSystems>(_GetSystems_QNAME, GetSystems.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSystemsByID }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link GetSystemsByID }{@code >}
     */
    @XmlElementDecl(namespace = "http://controller.sigeceunasecurityws.una.ac.cr/", name = "getSystemsByID")
    public JAXBElement<GetSystemsByID> createGetSystemsByID(GetSystemsByID value) {
        return new JAXBElement<GetSystemsByID>(_GetSystemsByID_QNAME, GetSystemsByID.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSystemsByIDResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link GetSystemsByIDResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://controller.sigeceunasecurityws.una.ac.cr/", name = "getSystemsByIDResponse")
    public JAXBElement<GetSystemsByIDResponse> createGetSystemsByIDResponse(GetSystemsByIDResponse value) {
        return new JAXBElement<GetSystemsByIDResponse>(_GetSystemsByIDResponse_QNAME, GetSystemsByIDResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSystemsResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link GetSystemsResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://controller.sigeceunasecurityws.una.ac.cr/", name = "getSystemsResponse")
    public JAXBElement<GetSystemsResponse> createGetSystemsResponse(GetSystemsResponse value) {
        return new JAXBElement<GetSystemsResponse>(_GetSystemsResponse_QNAME, GetSystemsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SaveSystem }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link SaveSystem }{@code >}
     */
    @XmlElementDecl(namespace = "http://controller.sigeceunasecurityws.una.ac.cr/", name = "saveSystem")
    public JAXBElement<SaveSystem> createSaveSystem(SaveSystem value) {
        return new JAXBElement<SaveSystem>(_SaveSystem_QNAME, SaveSystem.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SaveSystemResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link SaveSystemResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://controller.sigeceunasecurityws.una.ac.cr/", name = "saveSystemResponse")
    public JAXBElement<SaveSystemResponse> createSaveSystemResponse(SaveSystemResponse value) {
        return new JAXBElement<SaveSystemResponse>(_SaveSystemResponse_QNAME, SaveSystemResponse.class, null, value);
    }

}
