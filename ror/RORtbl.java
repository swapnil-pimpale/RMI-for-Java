package ror;

import java.net.Inet4Address;
import java.util.Hashtable;

/**
 * Every ROR needs a unique identifier. This is implemented as a simple
 * counter. We assume that a remote object implements a single remote interface
 * The ROR table is a mapping between a unique key and a local object on the server.
 *
 * This table is queried when we receive a network request over the socket connection.
 * We get an objKey in the RMIMessage and we look for the corresponding local object
 * in this table
 */
public class RORtbl {
        private Hashtable<Integer, Object> ror_tbl;
        private int uniqueKey;

        //create a new ROR table
        public RORtbl() {
                ror_tbl = new Hashtable<Integer, Object>();
                //start the counter from 10 say
                uniqueKey = 10;
        }

        /**
         * Add an object  to the table.
         * Given an object, we get it's class and hence it's interfaces
         * @param ip
         * @param port
         * @param o
         * @return returns an ROR for this object
         */
        public RemoteObjRef addObj(Inet4Address ip, int port, Object o) {
                //add object to the table
                ror_tbl.put(uniqueKey, o);

                /*
                Get the remote interface this object implements
                We assume that there is only one remote interface implemented
                 */
                String intfName = o.getClass().getInterfaces()[0].getName();
                String className = o.getClass().getName();

                /**
                 * Create a ROR using this information.
                 * Return the ROR. The caller will use it to bind it in registry.
                 */
                RemoteObjRef ref = new RemoteObjRef(ip, port, uniqueKey, intfName);

                //increment uniquekey
                uniqueKey++;

                //return the RemoteObjRef just created
                return ref;
        }

        /**
         * Given a uniqueKey find a local server Object from the hashTable
         * @param uniqueKey
         * @return local server Object
         */
        public Object findObj(int uniqueKey) {
                return ror_tbl.get(uniqueKey);
        }

        /**
         * print the current hashtable (For debugging purposes)
         */
        public void printTbl() {
                System.out.println(this.ror_tbl);
        }
}