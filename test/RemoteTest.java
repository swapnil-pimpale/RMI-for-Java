package test;

import ror.RemoteInterface;

import java.io.Serializable;

/**
 * This class implements the RemoteTestInterface.
 * This is the actual implementation of RemoteTestInterface. The other
 * implementation is done by RemoteTestInterface_stub class.
 *
 * Objects of this class will have an initial value of 100
 * The set and getMyValue() methods set and get this value.
 */
public class RemoteTest implements RemoteTestInterface, Serializable {
        public int myValue;

        public RemoteTest() {
                myValue = 100;
        }

        /**
         * Get myValue
         * @return: object's myValue
         */
        public int getMyValue() {
                System.out.println("In actual getMyValue");
                return this.myValue;
        }

        /**
         * Set myValue
         * @param value: value to be set
         */
        public void setMyValue(int value) {
                System.out.println("In actual setMyValue");
                this.myValue = value;
        }

        /**
         * getObject: This method create a local remote object which the server does not export.
         * And returns it. The newly created object will be passed by value (serialized)
         * @return
         */
        public RemoteTestInterface getObject() {
                System.out.println("In actual getObject");
                RemoteTest temp = new RemoteTest();
                return temp;
        }

        /**
         * getExportedObject: This method takes in a remote object, increments it's myValue by 1
         * and returns the same object.
         * @param obj: remote object
         * @return: same remote object
         */
        public RemoteTestInterface getExportedObject(RemoteTestInterface obj) {
                int currValue = obj.getMyValue();
                obj.setMyValue(++currValue);
                return obj;
        }

        /**
         * incrementValue: This method takes in a remote object and increments it's myValue by 1
         * @param obj; remote object
         */
        public void incrementValue(RemoteTestInterface obj) {
                int currValue = obj.getMyValue();
                obj.setMyValue(++currValue);
        }
}
