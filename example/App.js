/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 *
 * @format
 * @flow strict-local
 */

import React from 'react';
import {
  SafeAreaView,
  StyleSheet,
  ScrollView,
  View,
  Text,
  StatusBar,
  PermissionsAndroid,
  NativeEventEmitter,
  NativeModules
} from 'react-native';

import {
  Header,
  LearnMoreLinks,
  Colors,
  DebugInstructions,
  ReloadInstructions,
} from 'react-native/Libraries/NewAppScreen';

import ZebraBridge from 'react-native-zebra-bridge';
import ImagePicker from 'react-native-image-picker';

const pickerOptions = {
  title: 'Select Image',
  customButtons: [{name: 'fb', title: 'Choose Photo from Facebook'}],
  storageOptions: {
    path: 'images'
  }
}



//mainTest() //tests connection and disconnection
//test() //this connects and prints a "TEST" text

//requestCameraPermission() //check camera permissions, connects  and when user selects an image, it sends the absolute path to print
observePrinterStatus()
//mainTest()

const App: () => React$Node = () => {
  return (
    <>
      <StatusBar barStyle="dark-content" />
      <SafeAreaView>
        <ScrollView
          contentInsetAdjustmentBehavior="automatic"
          style={styles.scrollView}>
          <Header />
          {global.HermesInternal == null ? null : (
            <View style={styles.engine}>
              <Text style={styles.footer}>Engine: Hermes</Text>
            </View>
          )}
          <View style={styles.body}>
            <View style={styles.sectionContainer}>
              <Text style={styles.sectionTitle}>Step One</Text>
              <Text style={styles.sectionDescription}>
                Edit <Text style={styles.highlight}>App.js</Text> to change this
                screen and then come back to see your edits.
              </Text>
            </View>
            <View style={styles.sectionContainer}>
              <Text style={styles.sectionTitle}>See Your Changes</Text>
              <Text style={styles.sectionDescription}>
                <ReloadInstructions />
              </Text>
            </View>
            <View style={styles.sectionContainer}>
              <Text style={styles.sectionTitle}>Debug</Text>
              <Text style={styles.sectionDescription}>
                <DebugInstructions />
              </Text>
            </View>
            <View style={styles.sectionContainer}>
              <Text style={styles.sectionTitle}>Learn More</Text>
              <Text style={styles.sectionDescription}>
                Read the docs to discover what to do next:
              </Text>
            </View>
            <LearnMoreLinks />
          </View>
        </ScrollView>
      </SafeAreaView>
    </>
  );
};

async function mainTest() {
  await connect()
  await isConnected()
  await disconnect()
  await isConnected()
}

async function printImage(uri){
  try{
   let response = await ZebraBridge.printImage(uri)
   console.log("Print response: ", response)
  }catch(error){
    console.log(error)
  }
}

function showImagePicker(){
  ImagePicker.showImagePicker(pickerOptions, (response) => {
    if (response.didCancel) {
      console.log('User cancelled image picker');
    } else if (response.error) {
      console.log('ImagePicker Error: ', response.error);
    } else if (response.customButton) {
      console.log('User tapped custom button: ', response.customButton);
    } else {
      let path = response.path
      console.log(path)
      printImage(path)
    }

  })
}

async function connect(){
  try{
    console.log('Trying to connect')
    await ZebraBridge.connect("192.168.0.13",6101)
    console.log("Connected to printer")
  }catch(e){
    console.log(e)
  }
}

async function discoverPrinters(){
  try{
    const eventEmitter = new NativeEventEmitter(ZebraBridge);
    eventEmitter.addListener('PrintersSearch', (event) => {
       console.log(event)
    });
    const response = await ZebraBridge.discoverPrintersOnNetwork()
    console.log("Discover printers response: " + response)
  }catch(e){
    console.log(e)
  }
}

async function isConnected(){
  try{
    console.log("Asking if I am connected to the printer")
    let response = await ZebraBridge.isConnectedToPrinter()
    console.log("connected to printer " + response)
  }catch(e){
    console.log(e)
  }
}

async function disconnect(){
  try{
    console.log("Trying to disconnect")
    let response = await ZebraBridge.disconnect()
    console.log("disconnected " + response)
  }catch(e){
    console.log(e)
  }
}

async function sendFile(){
  try{
    let response = await ZebraBridge.sendFile("C:/archives")
    console.log("file sent " + response)
  }catch(e){
    console.log(e)
  }
}

async function startListeningPrintUpdates(){
  try{
    console.log('Requesting to start listening  printer status')
    let response = await ZebraBridge.startListeningPrinterStatus()
    console.log("request response " + response)
  }catch(e){
    console.log(e)
  }
}

async function test(){
  console.log("Running print test")
  try{
    let response = await ZebraBridge.testPrinter("192.168.0.13")
    console.log("test: " + response)
  }catch(e){
    console.log(e)
  }
}


function observePrinterStatus(){
  console.log("Setting up app to listen ZebraPrinterStatus topic")
  try{
    const eventEmitter = new NativeEventEmitter(ZebraBridge);
    eventEmitter.addListener('ZebraPrinterStatus', (event) => {
       console.log(event)
    });
  }catch(e){
    console.log(e)
  }
}

async function requestCameraPermission() {
  await connect()
  try {
    let isGranted = await PermissionsAndroid.check(PermissionsAndroid.PERMISSIONS.CAMERA)
    if(isGranted){
      console.log("Permission is granted:", isGranted)
      showImagePicker()
    } else {
      const granted = await PermissionsAndroid.request(
        PermissionsAndroid.PERMISSIONS.CAMERA,
        {
          title: "Camera Permission",
          message:
            "need access to your camera " +
            "so you can take awesome pictures.",
          buttonNeutral: "Ask Me Later",
          buttonNegative: "Cancel",
          buttonPositive: "OK"
        }
      );
      if (granted === PermissionsAndroid.RESULTS.GRANTED) {
        showImagePicker()
      } else {
        console.log("Camera permission denied");
      }
    }
  } catch (err) {
    console.warn(err);
  }
};



const styles = StyleSheet.create({
  scrollView: {
    backgroundColor: Colors.lighter,
  },
  engine: {
    position: 'absolute',
    right: 0,
  },
  body: {
    backgroundColor: Colors.white,
  },
  sectionContainer: {
    marginTop: 32,
    paddingHorizontal: 24,
  },
  sectionTitle: {
    fontSize: 24,
    fontWeight: '600',
    color: Colors.black,
  },
  sectionDescription: {
    marginTop: 8,
    fontSize: 18,
    fontWeight: '400',
    color: Colors.dark,
  },
  highlight: {
    fontWeight: '700',
  },
  footer: {
    color: Colors.dark,
    fontSize: 12,
    fontWeight: '600',
    padding: 4,
    paddingRight: 12,
    textAlign: 'right',
  },
});

export default App;
