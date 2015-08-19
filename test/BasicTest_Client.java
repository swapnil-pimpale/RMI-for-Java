package test;

import registry.LocateRegistry;
import registry.OtherRegistryImpl;
import ror.RemoteObjRef;

import java.net.Inet4Address;
import java.net.UnknownHostException;

/**
 * This client program tests the basic working of the framework.
 * It does this following:
 * 1) Takes in the registry IP and port from the command line
 * 2) Locates if there is a registry present on that IP:port
 * 3) Lookup the servicename "mathOperations" and get an ROR
 * 4) Localise a stub of the ROR
 * 5) Invoke addition and subtraction methods on the stub
 */
public class BasicTest_Client {
        public static void main(String args[]) {
                try {
                        Inet4Address regHost;

                        //get IP:port from the command line
                        regHost = (Inet4Address) Inet4Address.getByName(args[0]);
                        int regPort = Integer.parseInt(args[1]);
                        int num1 = Integer.parseInt(args[2]);
                        int num2 = Integer.parseInt(args[3]);

                        //locate registry on the IP:port
                        OtherRegistryImpl reg = LocateRegistry.getRegistry(regHost, regPort);

                        if (reg == null) {
                                System.out.println("Could not find registry. Exiting");
                                return;
                        }

                        //lookup a servicename
                        RemoteObjRef ror = reg.lookup("mathOperations");
                        if (ror == null) {
                                System.out.println("Servicename lookup failed");
                                return;
                        }

                        //localise a stub
                        testInterface tRmi = (testInterface) ror.localise();

                        //perform RMI
                        System.out.println("Addition = " + tRmi.add(num1, num2));
                        System.out.println("Subtraction = " + tRmi.subtract(num1, num2));
                        System.out.println("Division = " + tRmi.divide(num1, num2));

                } catch (UnknownHostException e) {
                        e.printStackTrace();
                }
        }
}