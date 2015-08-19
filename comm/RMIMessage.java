package comm;

import java.io.Serializable;

/**
 * General format for messages between classes. These messages
 * can include method invocations, return values and exceptions.
 * This format helps us pass the necessary information into the
 * constructor to create a new message, send this message object
 * whole over the network to the other side, instantiate it, and
 * ask it to unpack itself.
 */

public class RMIMessage implements Serializable {
        //exception thrown during sending/receiving of the message
        private Throwable exception;

        /*
        return value from an operation. The return value could be anything
        (a primitive, an integer, a remote object reference, etc)
         */
        private Object returnValue;

        //name of the method to invoke on the server side
        private String methodName;

        //array of parameters to the function
        private Object[] params;

        /*
        array of parameter types - this helps us in finding exactly which function
        is to be called in case the functions are overloaded
         */
        private Class<?>[] paramTypes;

        /*
        This is a unique object key associated with a remote object reference.
        On the server side this key is used to find the server's local object.
         */
        private int objkey;

        public RMIMessage(String method_name, Object[] params, Class<?>[] paramTypes, int objkey) {
                this.methodName = method_name;
                this.params = params;
                this.paramTypes = paramTypes;
                this.objkey = objkey;
        }

        /**
         * Getter and setter functions for the above fields
         */
        public void setException(Throwable e) {
                this.exception = e;
        }

        public Throwable getException() {
                return this.exception;
        }

        public void setReturnValue(Object returnValue) {
                this.returnValue = returnValue;
        }

        public Object getReturnValue() {
                return this.returnValue;
        }

        public Object[] getParams() {
                return this.params;
        }

        public void setParams(Object[] params) {
                this.params = params;
        }

        public Class<?>[] getParamTypes() {
                return this.paramTypes;
        }

        public void setParamTypes(Class<?>[] paramTypes) {
                this.paramTypes = paramTypes;
        }

        public int getObjkey() {
                return this.objkey;
        }

        public void setObjkey(int objkey) {
                this.objkey = objkey;
        }

        public String getMethodName() {
                return methodName;
        }

        public void setMethodName(String methodName) {
                this.methodName = methodName;
        }
}