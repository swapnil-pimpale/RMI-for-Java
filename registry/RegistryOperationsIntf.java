package registry;

import ror.RemoteObjRef;

import java.util.Collection;
import java.util.Enumeration;

/**
 * This interface declares the registry operations.
 * This interface is implemented by the actual registry or the one's
 * communicating with a actual registry (Client/Server)
 */
public interface RegistryOperationsIntf {
        public RemoteObjRef lookup(String servicename);
        public void rebind(String servicename, RemoteObjRef ror);
        public Enumeration<RemoteObjRef> listAllROR();
        public int getSize();
}
