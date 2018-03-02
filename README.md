# AQA framework for mobile automation #

### Components of framework ###

* apache maven - build tool
* testNG - as test management system, added as Maven dependency
* Appium - tool to interact with mobile devices
    * added as maven dependency
    * should be installed to OS as console application
* Android SDK - used by Appium to interact with Android devices. 

### Additional tools ###

* genymotion emulator of Android devices

### How to prepare environment ###

#### Android SDK ####

Appium uses several components of Android SDK for accessing of UI elements and installation of .apk files

first of all you need to download version of Android SKD for your platform. you can find necessary links in the bottom
side of next [page](https://developer.android.com/studio/index.html)
 
unzip the content of archive to some location (/opt/android/sdk for example)
create environment variable named ANDROID_HOME and target it to location of unzipped SDK (/opt/android/sdk)
add next locations to PATH system variable  **%ANDROID_HOME%/tools;%ANDROID_HOME%/tools/bin;**

dependently on version of Android you're going to use install next components using sdkmanager tool (example for Android 6):

* sdkmanager "platform-tools" "platforms;android-23"
* sdkmanager "build-tools;23.0.3"


#### Genymotion ####

install personal version of Genimotion using next [link](https://www.genymotion.com/fun-zone/)
create an virtual Android device (Nexus 5s)

#### Appium ####

there are actually two of "Appiums" - desktop application and console program.
desktop version can be used to learning. in last versions it does not have ability to run with predefined desired
capabilities. and our desired capabilities should not have parameters (restriction of AWS Device farm). So the main
tool here is console version.

to install desktop application use following [link](http://appium.io/)

to install console version you should download and install Node.js
you can use next useful [instruction](http://www.automationtestinghub.com/download-and-install-appium-1-6/).
to make long story short:

start Node.js command prompt and execute next command (Warning - command execution is long ~ 10 minutes):

**npm install -g appium**

to start appium with predefined desired capabilities use next command syntax:

**appium --pre-launch --device-name pish --platform-name Android --app path-to-apk**