package registry;

import comm.RegistryMessage;
import ror.RemoteObjRef;

import java.io.*;
import java.net.Inet4Address;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Enumeration;

/**
 * This file contains registry implementation for nodes which talk to the registry
 * Such nodes could be the clients or the server.
 * This class also implements the RegistryOperationsIntf
 * The implementation basically consists of
 * 1) Creating a RegistryMessage with appropriate parameters
 * 2) Creating a socket connection to the actual registry server
 * 3) Sending the message over the connection
 * 4) Receiving response over the connection and relaying it back to the actual requester
 */
public class OtherRegistryImpl implements RegistryOperationsIntf {

        Inet4Address regHost;
        int regPort;

        public OtherRegistryImpl(Inet4Address host, int port) {
                regHost = host;
                regPort = port;
        }

        public OtherRegistryImpl(String host, int port) {
                try {
                        regHost = (Inet4Address) Inet4Address.getByName(host);
                        regPort = port;
                } catch (UnknownHostException e) {
                        e.printStackTrace();
                }
        }

        /**
         * This function does the job of constructing a message, creating a socket connection
         * to the actual registry server, sending the message and receiving the response
         * @param msgType: type of message to be sent
         * @param serviceName: servicename to be used for the request
         * @param ror: ror to be used for the request
         * @return: the return message from the registry server
         */
        private RegistryMessage sendReceive(String msgType, String serviceName, RemoteObjRef ror) {
                try {
                        Socket soc;
                        soc = new Socket(regHost, regPort);
                        assert soc != null;

                        ObjectInputStream in = new ObjectInputStream(soc.getInputStream());
                        ObjectOutputStream out = new ObjectOutputStream(soc.getOutputStream());

                        //send a locate request with a servicename
                        RegistryMessage message = new RegistryMessage(msgType, serviceName, ror, null, -1, null);
                        //send this message to the registry
                        out.writeObject(message);
                        out.flush();

                        //read response
                        RegistryMessage returnMessage = (RegistryMessage) in.readObject();

                        //Close streams and socket
                        out.close();
                        in.close();
                        soc.close();

                        return returnMessage;
                } catch (IOException e) {
                        e.printStackTrace();
                } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                }
                return null;
        }

        /**
         * lookup: create a lookup request for the given servicename
         * @param serviceName
         * @return
         */
        public RemoteObjRef lookup(String serviceName) {
                RemoteObjRef ror;
                ror = null;

                RegistryMessage returnMessage = sendReceive("lookup", serviceName, null);

                ror = returnMessage.getRor();
                if (ror != null && returnMessage.getReturnValue() == 0) {
                       System.out.println("ROR found on lookup");
                } else {
                        System.out.println("ROR not found on lookup");
                        ror = null;
                }

                return ror;
        }

        /**
         * Create a rebind request for the given servicename and ror
         */
        public void rebind(String servicename, RemoteObjRef ror) {
                //send a locate request with a servicename
                RegistryMessage returnMessage = sendReceive("rebind", servicename, ror);
                if (returnMessage.getReturnValue() == 0) {
                        System.out.println("Rebind request was successful");
                } else {
                        System.out.println("Rebind request failed");
                }
        }

        /**
         * list: Should return an array containing all the registered RORs
         */
        public Enumeration<RemoteObjRef> listAllROR() {
                Enumeration<RemoteObjRef> refs = null;

                //send a listall request
                RegistryMessage returnMessage = sendReceive("listall", null, null);
                refs = returnMessage.getRefCollection();
                if (returnMessage.getReturnValue() == 0 && refs != null) {
                        System.out.println("listall request was successful");
                } else {
                        System.out.println("listall request failed");
                }

                return refs;
        }

        /**
         * getSize: create a request to get the registry's current size
         * @return
         */
        public int getSize() {
                int currSize = 0;
                //send a locate request with a servicename
                RegistryMessage returnMessage = sendReceive("getsize", null, null);
                if (returnMessage.getReturnValue() == 0) {
                        System.out.println("get size request successful");
                } else {
                        //ror not found
                        System.out.println("get size request failed");
                }

                currSize = returnMessage.getCurrSize();
                return currSize;
        }
}