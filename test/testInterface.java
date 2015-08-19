package test;

import ror.RemoteInterface;

/**
 * This interface declares the add and subtract methods which are implemented by
 * the testRMI class
 */
public interface testInterface extends RemoteInterface {
        public int add(int a, int b);
        public int subtract(int a, int b);
        public int divide(int a, int b);
}
