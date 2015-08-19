package test;

import comm.RMIMessage;
import ror.RemoteInterface;
import ror.RemoteObjRef;
import ror.Stub;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * This file contains the stub class implementation of the RemoteTestInterface.
 * The stub methods do the following:
 * 1) Create a socket connection to the ROR's location
 * 2) Marshal the parameters
 * 3) Create an RMIMessage with the methodName, list of parameters and their types and
 * the objKey for the ROR
 * 4) Send this message on the socket
 * 5) Receive response on the socket and unmarshal it
 * 6) Send the final response to the caller
 */
public class RemoteTestInterface_stub extends Stub implements RemoteTestInterface {
        @Override
        public int getMyValue() {
                try {
                        System.out.println("In stub's getMyValue");
                        Socket soc = new Socket(super.getIp(), super.getPort());
                        ObjectInputStream in = new ObjectInputStream(soc.getInputStream());
                        ObjectOutputStream out = new ObjectOutputStream(soc.getOutputStream());

                        Object[] params = new Object[0];

                        Class<?>[] paramTypes = new Class[0];

                        RMIMessage message = new RMIMessage("getMyValue", params, paramTypes, super.getObjKey());
                        out.writeObject(message);
                        out.flush();

                        RMIMessage returnMessage = (RMIMessage) in.readObject();

                        out.close();
                        in.close();
                        soc.close();

                        Throwable exception = returnMessage.getException();
                        if (exception != null)
                                throw exception;

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

        @Override
        public void setMyValue(int value) {
                try {
                        System.out.println("In stub's setMyValue");
                        Socket soc = new Socket(super.getIp(), super.getPort());
                        ObjectInputStream in = new ObjectInputStream(soc.getInputStream());
                        ObjectOutputStream out = new ObjectOutputStream(soc.getOutputStream());

                        Object[] params = new Object[1];
                        params[0] = value;

                        Class<?>[] paramTypes = new Class[1];
                        paramTypes[0] = params[0].getClass();

                        RMIMessage message = new RMIMessage("setMyValue", params, paramTypes, super.getObjKey());
                        out.writeObject(message);
                        out.flush();

                        RMIMessage returnMessage = (RMIMessage) in.readObject();

                        out.close();
                        in.close();
                        soc.close();

                        Throwable exception = returnMessage.getException();
                        if (exception != null)
                                throw exception;
                } catch (IOException e) {
                        e.printStackTrace();
                } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                } catch (Throwable throwable) {
                        throwable.printStackTrace();
                }
        }

        @Override
        public RemoteTestInterface getObject() {
                try {
                        System.out.println("In stub's getObject");
                        Socket soc = new Socket(super.getIp(), super.getPort());
                        ObjectInputStream in = new ObjectInputStream(soc.getInputStream());
                        ObjectOutputStream out = new ObjectOutputStream(soc.getOutputStream());

                        Object[] params = new Object[0];
                        Class<?>[] paramTypes = new Class[0];

                        RMIMessage message = new RMIMessage("getObject", params, paramTypes, super.getObjKey());
                        out.writeObject(message);
                        out.flush();

                        RMIMessage returnMessage = (RMIMessage) in.readObject();

                        out.close();
                        in.close();
                        soc.close();

                        Throwable exception = returnMessage.getException();
                        if (exception != null)
                                throw exception;

                        RemoteTestInterface remoteTestInterface;
                        if (returnMessage.getReturnValue() instanceof RemoteObjRef) {
                                RemoteObjRef ror = (RemoteObjRef) returnMessage.getReturnValue();
                                remoteTestInterface = (RemoteTestInterface) ror.localise();
                        } else {
                                remoteTestInterface = (RemoteTestInterface) returnMessage.getReturnValue();
                        }
                        return remoteTestInterface;
                } catch (IOException e) {
                        e.printStackTrace();
                } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                } catch (Throwable throwable) {
                        throwable.printStackTrace();
                }
                return null;
        }

        @Override
        public void incrementValue(RemoteTestInterface obj) {
                try {
                        System.out.println("In stub's incrementValue");
                        Socket soc = new Socket(super.getIp(), super.getPort());
                        ObjectInputStream in = new ObjectInputStream(soc.getInputStream());
                        ObjectOutputStream out = new ObjectOutputStream(soc.getOutputStream());

                        Object[] params = new Object[1];
                        Class<?>[] paramTypes = new Class[1];

                        if (obj instanceof Stub) {
                                Object temp = obj;
                                Stub stubObj = (Stub) temp;
                                RemoteObjRef ror = stubObj.getRor();
                                params[0] = ror;
                                paramTypes[0] = Class.forName(ror.getRemIntfName());

                        } else {
                                params[0] = obj;
                                paramTypes[0] = params[0].getClass().getInterfaces()[0];
                        }

                        RMIMessage message = new RMIMessage("incrementValue", params, paramTypes, super.getObjKey());
                        out.writeObject(message);
                        out.flush();

                        RMIMessage returnMessage = (RMIMessage) in.readObject();

                        out.close();
                        in.close();
                        soc.close();

                        Throwable exception = returnMessage.getException();
                        if (exception != null)
                                throw exception;

                } catch (IOException e) {
                        e.printStackTrace();
                } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                } catch (Throwable throwable) {
                        throwable.printStackTrace();
                }
        }

        public RemoteTestInterface getExportedObject(RemoteTestInterface obj) {
                try {
                        System.out.println("In stub's incrementValue");
                        Socket soc = new Socket(super.getIp(), super.getPort());
                        ObjectInputStream in = new ObjectInputStream(soc.getInputStream());
                        ObjectOutputStream out = new ObjectOutputStream(soc.getOutputStream());

                        Object[] params = new Object[1];
                        Class<?>[] paramTypes = new Class[1];

                        if (obj instanceof Stub) {
                                Object temp = obj;
                                Stub stubObj = (Stub) temp;
                                RemoteObjRef ror = stubObj.getRor();
                                params[0] = ror;
                                paramTypes[0] = Class.forName(ror.getRemIntfName());

                        } else {
                                params[0] = obj;
                                paramTypes[0] = params[0].getClass().getInterfaces()[0];
                        }

                        RMIMessage message = new RMIMessage("getExportedObject", params, paramTypes, super.getObjKey());
                        out.writeObject(message);
                        out.flush();

                        RMIMessage returnMessage = (RMIMessage) in.readObject();

                        out.close();
                        in.close();
                        soc.close();

                        Throwable exception = returnMessage.getException();
                        if (exception != null)
                                throw exception;

                        RemoteTestInterface remoteTestInterface;
                        if (returnMessage.getReturnValue() instanceof RemoteObjRef) {
                                RemoteObjRef ror = (RemoteObjRef) returnMessage.getReturnValue();
                                remoteTestInterface = (RemoteTestInterface) ror.localise();
                        } else {
                                remoteTestInterface = (RemoteTestInterface) returnMessage.getReturnValue();
                        }
                        return remoteTestInterface;
                } catch (IOException e) {
                        e.printStackTrace();
                } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                } catch (Throwable throwable) {
                        throwable.printStackTrace();
                }
                return null;
        }
}