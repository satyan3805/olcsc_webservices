This module is for Integration testing the EJB implementation only!  There should only be Test code in this
module, no source code for distribution.  This module should ONLY have integration tests in it.

To run the tests, 'mvn verify'.

Jetty Note:
    Jetty 6.1.9, while it supports OpenEJB quite nicely, hangs the
    JVM during the maven integration tests. OpenEJB & Jetty 6.1.22
    are not compatible with each other. 
    Working Jetty/OpenEjb combos: Jetty 6.1.9 - 6.1.11 
    Broken Jetty/OpenEjb combos: 
    Jetty 6.1.12 - 6.1.22 
    Additional details;
    http://mail-archives.apache.org/mod_mbox/openejb-users/201001.mbox/%3C8a884a801001221637q5730a14cl65de74542c49ccd5@mail.gmail.com%3E
In addition, Jetty will launch in its own process, so caution with JUnit tests.
