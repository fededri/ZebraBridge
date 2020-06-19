package com.ezcorp.ezroam.zebraBridge;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.ezcorp.ezroam.zebraBridge.di.DependencyFactory;
import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.ViewManager;
import com.facebook.react.bridge.JavaScriptModule;

public class ZebraBridgePackage implements ReactPackage {
    @Override
    public List<NativeModule> createNativeModules(ReactApplicationContext reactContext) {
        ZebraBridgeModule module = DependencyFactory.INSTANCE.getZebraModule(reactContext);
        return Arrays.<NativeModule>asList(module);
    }

    public List<Class<? extends JavaScriptModule>> createJSModules() {
        return null;
    }

    @Override
    public List<ViewManager> createViewManagers(ReactApplicationContext reactContext) {
        return Collections.emptyList();
    }
}
