package comm;

import ror.RemoteObjRef;

import java.io.Serializable;
import java.rmi.Remote;
import java.util.Collection;
import java.util.Enumeration;

/**
 * Registry specific messages that are exchanged between the client
 * and the registry (locate, lookup, list, found/not-found, etc)
 */
public class RegistryMessage implements Serializable {
        //type of message
        private String msgType;

        //type of exception generated
        private Throwable exception;

        //return value for the request
        private int returnValue;

        //servicename for when performing lookup or rebinding
        private String servicename;

        //remote object reference when rebinding it to a servicename
        private RemoteObjRef ror;

        //collection of RORs in the registry
        private Enumeration<RemoteObjRef> refCollection;

        //for the registry to identify itself to the clients
        private String identity;

        //current size of the registry - For debugging purposes
        private int currSize;

        public RegistryMessage(String msgType, String servicename, RemoteObjRef ror,
                               Throwable e, int returnValue, String identity) {
                this.msgType = msgType;
                this.servicename = servicename;
                this.ror = ror;
                this.exception = e;
                this.returnValue = returnValue;
                this.identity = identity;
        }

        /**
         * Getter and setter functions for all the above fields
         */
        public Throwable getException() {
                return exception;
        }

        public void setException(Throwable exception) {
                this.exception = exception;
        }

        public String getServicename() {
                return servicename;
        }

        public void setServicename(String servicename) {
                this.servicename = servicename;
        }

        public RemoteObjRef getRor() {
                return ror;
        }

        public void setRor(RemoteObjRef ror) {
                this.ror = ror;
        }

        public String getMsgType() {
                return msgType;
        }

        public void setMsgType(String msgType) {
                this.msgType = msgType;
        }

        public int getReturnValue() {
                return returnValue;
        }

        public void setReturnValue(int returnValue) {
                this.returnValue = returnValue;
        }

        public Enumeration<RemoteObjRef> getRefCollection() {
                return refCollection;
        }

        public void setRefCollection(Enumeration<RemoteObjRef> refCollection) {
                this.refCollection = refCollection;
        }

        public String getIdentity() {
                return identity;
        }

        public void setIdentity(String identity) {
                this.identity = identity;
        }

        public void setCurrSize(int size) {
                this.currSize = size;
        }

        public int getCurrSize() {
                return this.currSize;
        }
}