package ror;

import java.util.Hashtable;

/**
 * This hashtable stores the mapping between the server-side local objects
 * to their corresponding remote object references
 * Given a local object it becomes very easy to find whether we have a ROR
 * registered for it with the registry.
 *
 * This is used to check whether a particular object is "exported" or not.
 */
public class ObjRORtbl {
        private Hashtable<Object, RemoteObjRef> objRORtbl;

        public ObjRORtbl() {
                objRORtbl = new Hashtable<Object, RemoteObjRef>();
        }

        // add a mapping to the hash table
        public void addObj(Object obj, RemoteObjRef ror) {
                objRORtbl.put(obj, ror);
        }

        // Given an object find ROR
        public RemoteObjRef findROR(Object obj) {
                return objRORtbl.get(obj);
        }
}
