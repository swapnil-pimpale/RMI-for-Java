package registry;

import comm.RegistryMessage;
import ror.RemoteObjRef;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.InvalidParameterException;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Hashtable;


/**
 * This file contains code for the actual registry implementation.
 * The following class implements RegistryOperationsIntf
 * The registry is maintained as a hash table which maps servicenames
 * to remote object references
 */
public class ActualRegistryImpl implements RegistryOperationsIntf {

        //Hash table to map servicenames to remote object references
        private Hashtable<String, RemoteObjRef> registryHT;

        /*
        default port on which the actual registry will run
        This can be overridden by the user
         */
        private static int regPort = 12346;

        //create a new hash table when the registry is created
	public ActualRegistryImpl() {
                registryHT = new Hashtable<String, RemoteObjRef>();
	}

        /**
         * lookup into the hashtable using serviceName and return
         * and RemoteObjRef
         */
	public RemoteObjRef lookup(String serviceName) {
		if (serviceName == null)
                        return null;
                else
                        return registryHT.get(serviceName);
	}

        /**
         * rebind: bind a servicename with a given ror
         * @param serviceName
         * @param ror
         */
	public void rebind(String serviceName, RemoteObjRef ror) {
                if (serviceName == null || ror == null)
                        return;
                else
		        registryHT.put(serviceName, ror);
	}

        /**
         * unbind: remove a mapping from the registry (Not used currently since
         * objects need not be garbage collected)
         * @param serviceName
         */
	public void unbind(String serviceName) {
                if (serviceName == null)
                        return;
                else
		        registryHT.remove(serviceName);
	}

        /**
         * list all elements in the registry
         * @return
         */
	public Enumeration<RemoteObjRef> listAllROR() {
		return registryHT.elements();
	}

        /**
         * get the current size of the registry
         * @return
         */
        public int getSize() {
                return registryHT.size();
        }

        public static void main(String args[]) {
                //create new registry
                ActualRegistryImpl registry = new ActualRegistryImpl();

                if (args.length > 0) {
                        //user has provided a port for the registry to run on
                        regPort = Integer.parseInt(args[0]);
                }
                System.out.println("Registry using port: " + regPort);

                //create server socket that will listen for registry requests
                try {
                        ServerSocket servSoc = new ServerSocket(regPort);

                        while (true) {
                                Socket soc = servSoc.accept();
                                ObjectOutputStream out = new ObjectOutputStream(soc.getOutputStream());
                                ObjectInputStream in = new ObjectInputStream(soc.getInputStream());
                                RegistryMessage message = (RegistryMessage)in.readObject();

                                String msgType = message.getMsgType();
                                if (msgType.equals("lookup")) {
                                        String servicename = message.getServicename();
                                        message.setRor(registry.lookup(servicename));
                                        //a return value of 0 is interpreted as success on the sender side
                                        message.setReturnValue(0);
                                        System.out.println("Received lookup on " + servicename);
                                } else if (msgType.equals("rebind")) {
                                        String servicename = message.getServicename();
                                        RemoteObjRef ror = message.getRor();
                                        registry.rebind(servicename, ror);
                                        message.setReturnValue(0);
                                        System.out.println("Received rebind on " + servicename);
                                } else if (msgType.equals("listall")) {
                                        message.setRefCollection(registry.listAllROR());
                                        message.setReturnValue(0);
                                } else if (msgType.equals("locate")) {
                                        message.setIdentity("IamRegistry");
                                        message.setReturnValue(0);
                                } else if (msgType.equals("getsize")) {
                                        message.setCurrSize(registry.getSize());
                                        message.setReturnValue(0);
                                } else {
                                        //set an exception
                                        System.out.println("Invalid msgType");
                                        Throwable e = new InvalidParameterException("Invalid msgType");
                                        message.setException(e);
                                        message.setReturnValue(-1);
                                }

                                out.writeObject(message);
                                out.flush();

                                //Close streams and sockets
                                out.close();
                                in.close();
                                soc.close();
                        }
                } catch (IOException e) {
                        e.printStackTrace();
                } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                }
        }
	
}