package test;

import ror.RemoteInterface;

/**
 * Created by swapnil on 10/4/14.
 */
public interface RemoteTestInterface extends RemoteInterface {
        public int getMyValue();
        public void setMyValue(int value);
        public RemoteTestInterface getObject();
        public RemoteTestInterface getExportedObject(RemoteTestInterface obj);
        public void incrementValue(RemoteTestInterface obj);
}
