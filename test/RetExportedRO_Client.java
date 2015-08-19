package test;

import registry.LocateRegistry;
import registry.OtherRegistryImpl;
import ror.RemoteObjRef;

import java.net.Inet4Address;
import java.net.UnknownHostException;

/**
 * obj1 and obj2 are both exported remote object references
 * Hence obj2 will be passed in as a reference (marshalled) to getExportedObject()
 * getExportedObject() increments the value of the object passed in and returns the same object back
 * Thus obj2 and obj3 are basically references to the same remote object.
 * This can be verified by invoking getMyValue() function on both of them. They have the same value
 * This can also be checked by printing out the elements in the registry. There are only 2 elements in the
 * registry (obj1 and obj2)
 */
public class RetExportedRO_Client {
        public static void main(String args[]) {
                try {
                        Inet4Address regHost = null;
                        regHost = (Inet4Address) Inet4Address.getByName(args[0]);
                        int regPort = Integer.parseInt(args[1]);

                        //locate registry
                        OtherRegistryImpl reg = LocateRegistry.getRegistry(regHost, regPort);

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
                        RemoteTestInterface obj3 = obj1Stub.getExportedObject(obj2Stub);
                        System.out.println("obj2's new value = " + obj2Stub.getMyValue());
                        System.out.println("obj3's value should be same as obj2's = " + obj3.getMyValue());
                } catch (UnknownHostException e) {
                        e.printStackTrace();
                }
        }
}