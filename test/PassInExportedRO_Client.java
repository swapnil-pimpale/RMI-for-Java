package test;

import registry.LocateRegistry;
import registry.OtherRegistryImpl;
import ror.RemoteObjRef;

import java.net.Inet4Address;
import java.net.UnknownHostException;

/**
 * In this case the server has exported 2 remote objects.
 * Their servicenames are "obj1" and "obj2"
 * The following client program does the following:
 * 1) Locate registry on the IP:port received from the command line
 * 2) Lookup registry for obj1 and localise a stub for it
 * 3) Lookup registry for obj2 and localise a stub for it
 * 4) It then invokes incrementValue() function on obj1
 * incrementValue takes in a remote object as a parameter
 * 5) Here since obj2 is an "exported remote object" it will be passed by reference
 * i.e., it will be marshalled.
 * 6) obj2's value gets incremented on the server side which can be verified from the client
 * by making an RMI to obj2's getMyValue()
 */
public class PassInExportedRO_Client {
        public static void main(String args[]) {
                try {
                        Inet4Address regHost = null;
                        regHost = (Inet4Address) Inet4Address.getByName(args[0]);
                        int regPort = Integer.parseInt(args[1]);

                        //locate registry
                        OtherRegistryImpl reg = LocateRegistry.getRegistry(regHost, regPort);
                        int regSize;

                        if (reg == null) {
                                System.out.println("Could not find registry. Exiting");
                                return;
                        }

                        //lookup servicename obj1
                        RemoteObjRef obj1ROR = reg.lookup("obj1");
                        if (obj1ROR == null) {
                                System.out.println("Servicename lookup failed");
                                return;
                        }

                        //localise a stub for obj1
                        RemoteTestInterface obj1Stub = (RemoteTestInterface) obj1ROR.localise();

                        //lookup servicename obj2
                        RemoteObjRef obj2ROR = reg.lookup("obj2");
                        if (obj2ROR == null) {
                                System.out.println("Servicename lookup failed");
                                return;
                        }

                        //localise a stub for obj2
                        RemoteTestInterface obj2Stub = (RemoteTestInterface) obj2ROR.localise();

                        System.out.println("obj1 value = " + obj1Stub.getMyValue());
                        System.out.println("obj2 value = " + obj2Stub.getMyValue());
                        System.out.println("calling incrementValue");
                        obj1Stub.incrementValue(obj2Stub);
                        System.out.println("obj2's new value = " + obj2Stub.getMyValue());
                } catch (UnknownHostException e) {
                        e.printStackTrace();
                }
        }
}