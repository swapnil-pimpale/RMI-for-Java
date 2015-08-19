package test;

import ror.RemoteInterface;

/**
 * This class implements the testInterface.
 * This is the actual implementation of testInterface. The other
 * implementation is done by testInterface_stub class.
 */
public class testRMI implements testInterface {
        public testRMI() {

        }

        public int add(int a, int b) {
                return a + b;
        }

        public int subtract(int a, int b) {
                return a - b;
        }

        public int divide(int a, int b) {
                if (b == 0) {
                        throw new IllegalArgumentException("Divide by 0");
                }

                return (a / b);
        }
}
