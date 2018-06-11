/**
 * MojservisLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package com.gint.app.bisis.editor.nibis.binding;

public class MojservisLocator extends org.apache.axis.client.Service implements com.gint.app.bisis.editor.nibis.binding.Mojservis {

    // Use to get a proxy class for HelloIFPort
    private java.lang.String HelloIFPort_address = "http://160.99.21.1:8080/mojservis/mojservis";

    public java.lang.String getHelloIFPortAddress() {
        return HelloIFPort_address;
    }

	public void setHelloIFPortAddress(String address) {
	   HelloIFPort_address=address;
   }

    // The WSDD service name defaults to the port name.
    private java.lang.String HelloIFPortWSDDServiceName = "HelloIFPort";

    public java.lang.String getHelloIFPortWSDDServiceName() {
        return HelloIFPortWSDDServiceName;
    }

    public void setHelloIFPortWSDDServiceName(java.lang.String name) {
        HelloIFPortWSDDServiceName = name;
    }

    public com.gint.app.bisis.editor.nibis.binding.HelloIF getHelloIFPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(HelloIFPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getHelloIFPort(endpoint);
    }

    public com.gint.app.bisis.editor.nibis.binding.HelloIF getHelloIFPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.gint.app.bisis.editor.nibis.binding.HelloIFBindingStub _stub = new com.gint.app.bisis.editor.nibis.binding.HelloIFBindingStub(portAddress, this);
            _stub.setPortName(getHelloIFPortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.gint.app.bisis.editor.nibis.binding.HelloIF.class.isAssignableFrom(serviceEndpointInterface)) {
                com.gint.app.bisis.editor.nibis.binding.HelloIFBindingStub _stub = new com.gint.app.bisis.editor.nibis.binding.HelloIFBindingStub(new java.net.URL(HelloIFPort_address), this);
                _stub.setPortName(getHelloIFPortWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        String inputPortName = portName.getLocalPart();
        if ("HelloIFPort".equals(inputPortName)) {
            return getHelloIFPort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("urn:Foo", "Mojservis");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("HelloIFPort"));
        }
        return ports.iterator();
    }

}
