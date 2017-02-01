# domotics
A web application to operate i/o on micro controllers like Raspberry Pi or Arduino.
##Introduction
So yes, another domotics project, why is this project different from all th eothers?
Purpose of this project is to have a generic software platform compatible with different hardware systems and use cases. Second, we would like to give the users of this platform a comprehensive guide to explain how to buid a domotics system starting from required hardware to a frindly user interface.
Personally I jumped into this project when my buiding has refurbished the thermal power plant and a new valve system has been installed to the apartments' radiators.
Those kind of valves require to be operated manually, several times a day, so I thought that some kind of app was necessary.
I have found some great commercial solution to do that ma definitely expensive.
At the same time I realised that a lot of simple and cheaper devices for domotics are widely available in the web. I decided to develop my own solution and to share it with everyone's interested.
##Software Architecture
The architecture of the system is based on three different software layers, the base level is the software installed on hardware controllers (i.e. Arduino or Raspberry Pi), let's call it a sort of "firmware", and it is written in a low level language, for example to program Arduino (or Arduino compatible) boards a specific language is available.
A second component of the system is a web application that exposes the devices controlled by the low level controllers in a user friendly web interface. It's a web application written in Java language that can be accessed by any device connected to the home LAN and that can be used to monitor the status of the sensors, the status of the devices configured in the system and that can operate them.
A Third component is another web app, this time the application is available by Internet and represents the cloud operating mode of the local application.
The _Home_ Application can be installed on any kind of computer which is always available (always up and running) on your home network. The _Cloud_ component is currently deployed on [Red Hat Openshift](http://openshift.redhat.com).

