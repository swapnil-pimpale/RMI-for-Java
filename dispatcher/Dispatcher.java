package dispatcher;

import comm.RMIMessage;
import registry.LocateRegistry;
import registry.OtherRegistryImpl;
import ror.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Inet4Address;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * The server side Dispatcher.
 * The command line arguments for the Dispatcher will look as below:
 * <ServerPort> <RegistryHost> <RegistryPort> <ClassName> <NumObjects> <servicenames...>
 * It performs the following tasks:
 * 1) Create local objects of the Class passed in from the command line
 * 2) Locates the registry on the host and port passed in from the command line
 * 3) Creates RORs for the objects and stores them in the registry
 */
public class Dispatcher {
        public static Inet4Address serverHost;
        private static Inet4Address regHost;
        public static int serverPort;
        private static int regPort;
        private static int numObjects;
        private static String ClassName;
        private static String[] serviceNames;
        private static Object[] objects;
        private static Object[] params;
        private static RemoteObjRef[] refs;
        private static RORtbl rtbl;
        private static ObjRORtbl objRORtbl;
        private static Class<?>[] retParamTypes;
        private static Object localObj;
        private static Object result;
        private static Method method;

        public static void main(String args[]) {
                try {
                        serverHost = (Inet4Address) Inet4Address.getLocalHost();

                        //parse command line arguments
                        parseCmdlineArgs(args);

                        constructAndBindObjects();

                        runDispatcher();
                } catch (UnknownHostException e) {
                        e.printStackTrace();
                }
        }

        private static void parseCmdlineArgs(String args[]) {
                try {
                        int argc;

                        serverPort = Integer.parseInt(args[0]);
                        regHost = (Inet4Address) Inet4Address.getByName(args[1]);
                        regPort = Integer.parseInt(args[2]);
                        ClassName = args[3];
                        numObjects = Integer.parseInt(args[4]);
                        serviceNames = new String[numObjects];
                        objects = new Object[numObjects];
                        refs = new RemoteObjRef[numObjects];
                        argc = 5;
                        for (int i = 0; i < numObjects; i++) {
                                serviceNames[i] = args[argc++];
                        }
                } catch (UnknownHostException e) {
                        e.printStackTrace();
                }
        }

        private static void constructAndBindObjects() {
                Class c;
                int i;
                try {
                        //locate registry
                        OtherRegistryImpl reg = LocateRegistry.getRegistry(regHost, regPort);
                        if (reg != null)
                                System.out.println("Dispatcher: Registry Located");
                        else
                                System.out.println("Dispatcher: Registry locate failed");

                        //create RORTable which maps key to local objects
                        rtbl = new RORtbl();

                        //create ObjRORTbl which maps objects to their RORs
                        objRORtbl = new ObjRORtbl();


                        c = Class.forName(ClassName);
                        for (i = 0; i < numObjects; i++) {
                                //create new instances for Class c
                                objects[i] = c.newInstance();

                                //add the above object to ROR table and to the registry
                                refs[i] = rtbl.addObj(serverHost, serverPort, objects[i]);
                                if (refs[i] != null) {
                                        assert reg != null;
                                        //bind the RORs to their respective servicenames
                                        reg.rebind(serviceNames[i], refs[i]);
                                }

                                //add a mapping between local objects and their respective RORs
                                objRORtbl.addObj(objects[i], refs[i]);
                        }
                } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                } catch (InstantiationException e) {
                        e.printStackTrace();
                } catch (IllegalAccessException e) {
                        e.printStackTrace();
                }
        }

        private static Class<?> isPrimitive(Class<?> c) {
                if (c == Byte.class) {
                        return Byte.TYPE;
                } else if (c == Short.class) {
                        return Short.TYPE;
                } else if (c == Integer.class) {
                        return Integer.TYPE;
                } else if (c == Long.class) {
                        return Long.TYPE;
                } else if (c == Float.class) {
                        return Float.TYPE;
                } else if (c == Double.class) {
                        return Double.TYPE;
                } else if (c == Boolean.class) {
                        return Boolean.TYPE;
                } else if (c == Character.class) {
                        return Character.TYPE;
                } else {
                        return c;
                }
        }

        private static void handlePrimitives(Class<?>[] paramTypes) {
                retParamTypes = new Class<?>[paramTypes.length];

                for (int i = 0; i < paramTypes.length; i++) {
                        retParamTypes[i] = isPrimitive(paramTypes[i]);
                }
        }

        private static void runDispatcher() {
                try {
                        ServerSocket servSoc = new ServerSocket(serverPort);
                        while (true) {
                                Socket soc = servSoc.accept();
                                ObjectOutputStream out = new ObjectOutputStream(soc.getOutputStream());
                                ObjectInputStream in = new ObjectInputStream(soc.getInputStream());

                                //read incoming request
                                RMIMessage message = (RMIMessage) in.readObject();

                                //unmarshal incoming request and invoke the method
                                unmarshalAndInvoke(message);

                                //marshal result
                                marshalResult(message);

                                //send the message back to the client
                                out.writeObject(message);
                                out.flush();

                                //close the sockets
                                out.close();
                                in.close();
                                soc.close();
                        }
                } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                } catch (IOException e) {
                        e.printStackTrace();
                }
        }

        private static void marshalResult(RMIMessage message) {
                //check if the result is a remote object and handle marshalling accordingly
                if (result instanceof RemoteInterface) {
                        if (result instanceof Stub) {
                                message.setReturnValue(((Stub) result).getRor());
                        }
                        else {
                                //check if this object has a corresponding ror in objRORtbl
                                RemoteObjRef ror = objRORtbl.findROR(result);
                                if (ror != null) {
                                        message.setReturnValue(ror);
                                } else {
                                        /*
                                        This remote object is not present in the table.
                                        This means that it is not exported so send it normally
                                         */
                                        message.setReturnValue(result);
                                }
                        }
                } else if (result instanceof Throwable) {
                        message.setException((Throwable) result);

                } else {
                        message.setReturnValue(result);
                }

        }

        private static void unmarshalAndInvoke(RMIMessage message) {
                try {
                        //get the key from the message
                        int objKey = message.getObjkey();
                        //using the objKey get the local object from the table
                        localObj = rtbl.findObj(objKey);

                        //get parameters
                        params = message.getParams();

                        //get parameter Types
                        handlePrimitives(message.getParamTypes());

                        //check if the parameters are RORs
                        for (int i = 0; i < params.length; i++) {
                                if (params[i] instanceof RemoteObjRef) {
                                        RemoteObjRef ror = (RemoteObjRef) params[i];
                                        Object paramObj = rtbl.findObj(ror.getObjKey());
                                        if (paramObj != null) {
                                                params[i] = paramObj;
                                        } else {
                                                Stub paramStub = (Stub) ror.localise();
                                                paramStub.setRor(ror);
                                                params[i] = paramStub;
                                        }
                                        retParamTypes[i] = Class.forName(ror.getRemIntfName());
                                }
                        }

                        //get the method name to invoke
                        method = localObj.getClass().getMethod(message.getMethodName(), retParamTypes);

                        //invoke this method on the local object and get result
                        result = method.invoke(localObj, params);
                } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                } catch (InvocationTargetException e) {
                        result = e;
                } catch (IllegalAccessException e) {
                        e.printStackTrace();
                } catch (IllegalArgumentException e) {
                        result = e;
                }
        }
}