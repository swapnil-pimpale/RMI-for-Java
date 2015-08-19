package test;

import comm.RMIMessage;
import ror.Stub;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * This file contains the stub class implementation of the testInterface.
 * The stub methods do the following:
 * 1) Create a socket connection to the ROR's location
 * 2) Marshal the parameters
 * 3) Create an RMIMessage with the methodName, list of parameters and their types and
 * the objKey for the ROR
 * 4) Send this message on the socket
 * 5) Receive response on the socket and unmarshal it
 * 6) Send the final response to the caller
 */
public class testInterface_stub extends Stub implements testInterface {
        public int add(int a, int b) {
                try {
                        System.out.println("In stub's add function");

                        //Create socket and I/O streams
                        Socket soc = new Socket(super.getIp(), super.getPort());
                        ObjectInputStream in = new ObjectInputStream(soc.getInputStream());
                        ObjectOutputStream out = new ObjectOutputStream(soc.getOutputStream());

                        //the parameters here are non remote objects. So no need to marshal them
                        Object[] params = new Object[2];
                        params[0] = a;
                        params[1] = b;

                        Class<?>[] paramTypes = new Class[2];
                        paramTypes[0] = params[0].getClass();
                        paramTypes[1] = params[1].getClass();

                        //create an RMIMessage
                        RMIMessage message = new RMIMessage("add", params, paramTypes, super.getObjKey());
                        out.writeObject(message);
                        out.flush();

                        //read the response
                        RMIMessage returnMessage = (RMIMessage) in.readObject();

                        //close the streams and socket
                        out.close();
                        in.close();
                        soc.close();

                        //check if there was an exception set
                        Throwable exception = returnMessage.getException();
                        if (exception != null)
                                throw exception;

                        //here the response is a non remote object so no need to unmarshal it
                        return (Integer) returnMessage.getReturnValue();
                } catch (IOException e) {
                        e.printStackTrace();
                } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                } catch (Throwable throwable) {
                        throwable.printStackTrace();
                }
                return -1;
        }

        public int subtract(int a, int b) {
                try {
                        System.out.println("In stub's subtract function");

                        //Create socket and I/O streams
                        Socket soc = new Socket(super.getIp(), super.getPort());
                        ObjectInputStream in = new ObjectInputStream(soc.getInputStream());
                        ObjectOutputStream out = new ObjectOutputStream(soc.getOutputStream());

                        //the parameters here are non remote objects. So no need to marshal them
                        Object[] params = new Object[2];
                        params[0] = a;
                        params[1] = b;

                        Class<?>[] paramTypes = new Class[2];
                        paramTypes[0] = params[0].getClass();
                        paramTypes[1] = params[1].getClass();

                        //create an RMIMessage
                        RMIMessage message = new RMIMessage("subtract", params, paramTypes, super.getObjKey());
                        out.writeObject(message);
                        out.flush();

                        //read the response
                        RMIMessage returnMessage = (RMIMessage) in.readObject();

                        //close the streams and socket
                        out.close();
                        in.close();
                        soc.close();

                        //check if there was an exception set
                        Throwable exception = returnMessage.getException();
                        if (exception != null)
                                throw exception;

                        //here the response is a non remote object so no need to unmarshal it
                        return (Integer) returnMessage.getReturnValue();
                } catch (IOException e) {
                        e.printStackTrace();
                } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                } catch (Throwable throwable) {
                        throwable.printStackTrace();
                }
                return -1;
        }

        public int divide(int a, int b) {
                try {
                        System.out.println("In stub's divide function");

                        //Create socket and I/O streams
                        Socket soc = new Socket(super.getIp(), super.getPort());
                        ObjectInputStream in = new ObjectInputStream(soc.getInputStream());
                        ObjectOutputStream out = new ObjectOutputStream(soc.getOutputStream());

                        //the parameters here are non remote objects. So no need to marshal them
                        Object[] params = new Object[2];
                        params[0] = a;
                        params[1] = b;

                        Class<?>[] paramTypes = new Class[2];
                        paramTypes[0] = params[0].getClass();
                        paramTypes[1] = params[1].getClass();

                        //create an RMIMessage
                        RMIMessage message = new RMIMessage("divide", params, paramTypes, super.getObjKey());
                        out.writeObject(message);
                        out.flush();

                        //read the response
                        RMIMessage returnMessage = (RMIMessage) in.readObject();

                        //close the streams and socket
                        out.close();
                        in.close();
                        soc.close();

                        //check if there was an exception set
                        Throwable exception = returnMessage.getException();
                        if (exception != null) {
                                System.out.println("Exception occurred on the server side: " + exception.getCause());
                                return -1;
                        }

                        //here the response is a non remote object so no need to unmarshal it
                        return (Integer) returnMessage.getReturnValue();
                } catch (IOException e) {
                        e.printStackTrace();
                } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                } catch (Throwable throwable) {
                        throwable.printStackTrace();
                }
                return -1;
        }
}