# ZebraBridge
Android zebra bridge for React native.

Considerations:\
-RN app must support multi dex since this native module uses jar files which adds a big number of methods.\
-RN app must declare in the Manifest the permission CHANGE_WIFI_MULTICAST_STATE in order to be able to discover printers on the network\
-Add this to the app/build.gradle file :

 packagingOptions {
        exclude 'META-INF/LICENSE.txt'\
        exclude 'META-INF/NOTICE.txt'\
        exclude 'META-INF/NOTICE'\
        exclude 'META-INF/LICENSE'\
        exclude 'META-INF/DEPENDENCIES'\
    }

