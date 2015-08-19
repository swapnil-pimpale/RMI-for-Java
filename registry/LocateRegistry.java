package registry;

import comm.RMIMessage;
import comm.RegistryMessage;

import java.io.*;
import java.net.Inet4Address;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by swapnil on 9/30/14.
 */
public class LocateRegistry {
        public static OtherRegistryImpl getRegistry(Inet4Address host, int port) {
                try {
                        OtherRegistryImpl reg;
                        Socket soc = new Socket(host, port);
                        assert soc != null;

                        ObjectInputStream in = new ObjectInputStream(soc.getInputStream());
                        ObjectOutputStream out = new ObjectOutputStream(soc.getOutputStream());
                        RegistryMessage message = new RegistryMessage("locate", null, null, null, -1, null);

                        //write out the message over the socket
                        out.writeObject(message);
                        //flush the stream
                        out.flush();
                        //check response
                        RegistryMessage returnMessage = (RegistryMessage) in.readObject();
                        if (returnMessage.getIdentity().equals("IamRegistry")) {
                                //found a registry on this host/port
                                reg = new OtherRegistryImpl(host, port);
                        } else {
                                //did not find a registry
                                reg = null;
                        }

                        //close streams and socket
                        out.close();
                        in.close();
                        soc.close();

                        return reg;
                } catch (UnknownHostException e) {
                        e.printStackTrace();
                } catch (IOException e) {
                        e.printStackTrace();
                } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                }
                return null;
        }
}