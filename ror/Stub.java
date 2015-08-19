package ror;

import ror.RemoteObjRef;

import java.io.Serializable;
import java.net.Inet4Address;

/**
 * This file contains the Stub class.
 * A stub has an associated ROR with it.
 */
public class Stub implements Serializable, StubInterface, RemoteInterface {
        RemoteObjRef ror;

        public Stub() {
                //empty constructor so that we create a new Stub instance in localise()
        }

        /**
         * Getter and setter functions for the ror and for the fields within ror
         */
        @Override
        public void setRor(RemoteObjRef ror) {
                this.ror = ror;
        }

        public RemoteObjRef getRor() {
                return this.ror;
        }

        public Inet4Address getIp() {
                return ror.getIpaddr();
        }

        public int getPort() {
                return ror.getPort();
        }

        public int getObjKey() {
                return ror.getObjKey();
        }
}