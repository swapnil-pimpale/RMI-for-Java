package ror;

import java.io.Serializable;
import java.net.Inet4Address;

/**
 * This file describes how a Remote Object Reference should look like
 * It basically consists of the location information about the real object
 * 1) ipaddr: IP address of the server which has exported this object
 * 2) port: port number of the server which has exported this object
 * 3) remIntfName: name of the remote interface name implemented by this object
 * (Assumption: we assume that a remote object implements only one remote interface)
 * 4) objKey: Unique key associated with this ROR
 */
public class RemoteObjRef implements Serializable {
        Inet4Address ipaddr;
        int port;
        int objKey;
        String remIntfName;

        public RemoteObjRef(Inet4Address inIpAddr, int inPort, int inObjKey, String inRiname) {
                ipaddr = inIpAddr;
                port = inPort;
                objKey = inObjKey;
                remIntfName = inRiname;
        }

        /**
         * localise: this function localises a stub for the remote object reference
         * We assume that the stub class has the name:
         * remIntfName + _stub
         *
         * @return: returns a Stub object
         */
        public Object localise() {
                try {
                        Class c = null;

                        //get the class using reflection
                        c = Class.forName(remIntfName + "_stub");

                        //create a new instance
                        Stub o = (Stub) c.newInstance();

                        //set this as the ROR for the stub
                        o.setRor(this);

                        //return the Stub
                        return o;
                } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                } catch (InstantiationException e) {
                        e.printStackTrace();
                } catch (IllegalAccessException e) {
                        e.printStackTrace();
                }
                return null;
        }

        /**
         * Getter and setter functions for the above fields
         */
        public Inet4Address getIpaddr() {
                return ipaddr;
        }

        public int getPort() {
                return port;
        }

        public int getObjKey() {
                return objKey;
        }

        public String getRemIntfName() {
                return remIntfName;
        }
}