# Drone
Navigation system for a Quadcopter.

![drone](https://user-images.githubusercontent.com/55064602/147283396-807aca62-a7a8-4963-b87f-1ae133738b42.png)

## Project Description:
This project simulates a control software for a drone navigation system. The following components are the main components of the Drone navigation system.

### Airframe Status:
The Airframe Status of the Drone tracks the Drone in a 3 dimensional co-ordinate system. It consists of the following components:
* **Inertial Measurement Unit(IMU):**
    - Gyroscope – Measures the rotational attributes of the Drone (Roll, Pitch & Yaw).
    - Accelerometer – Determines the acceleration of the Drone in all three dimensions.
    - Magnetometer – Establishes cardinal direction for the Drone.
* **Altimeter:** - Detects the current altitude of the Drone.
* **GPS:** - Tracks the Location of the Drone

### Collision Detectors:
The Drone consists of periferal and ground collision detectors to help detect if any object or terrain is in the collision proximity of the collision sesnors.
* **Periferal Collision Detectors:** The Drone consists of 4 Collision sensors around the surface of the Drone in each cardinal direction (North, East, South, West).
* **Ground Collision Detector:** The Drone consists of a collision detector that detects collision in the bottom of the drone. **Note** This collision detector is turned off when the drone takes of, and when the Drone is trying to return to its home location.

### Drone State:
The Drone system consists of different states that describe the behavior of the system at a particular time. These states capture the information about the drone which is required by the microcontroller to make control decisions.
* **Battery** of type Int - represents the battery status of the Drone
* **HomeLocation** of type Location - represents the takeoff location of the Drone
* **Acc** of type Acceleration - represents the accelerations of the drone in x,y, and z dimension.
* **Alt** of type Double - represents the altitude of the Drone from the takeoff point.
* **CurrVector** of type Vector - represents where the drone is currently pointing at.
* **Storage** of type Boolean - represents if the Drone has sufficient storage.

### Radio:
The Drone control system uses a transceiver to communicate and receive signals back and forth from the remote controller. These signals are interpreted as control commands by the Drone controller which helps in operating the Drone as instructed by the User using the Remote Controller.

### Camera:
The Drone consists of a mountable Camera to take picture with. In this Software Demo, we have simulated a camera as a function of storage, which will be full after we take 5 pictures.

## Tutorial:
### Remote Control: 

![RemoteController](https://user-images.githubusercontent.com/55064602/147294424-703e99b2-54bb-4d5b-8b69-2c5e87f5e785.png)

#### Commands
* **setVector** - Sets the current vector of the Drone. Format: x,y,z (Where, x,y, and z represents the speed in respective dimension)   
* **GoHome** - Commands the Drone to return home using the AutoPilot feature.
* **Capture** - Commands the Drone to take a picture.

### Collisions:

![collisiondetector](https://user-images.githubusercontent.com/55064602/147294825-43b68970-1e0e-45fd-8259-10f948bfd029.png)

You can manually set up collisions for the Drone using the buttons in the Collision Detector panel that represent respective collision sensors mounted on the Drone. In case a Collision is detected in a certain direction, the Drone controller restricts the movement of the drone in that direction.

### Storage:

![storage](https://user-images.githubusercontent.com/55064602/147295342-ce3a693a-36d9-45d5-a7dd-6bfae071e495.png)

For the Software Demo, the Drone is currently set up to take a maximum of 5 pictures. Anymore than that, will restrict the Drone to take more pictures which is signaled by the change of button color for Storage from green to red. You can also manually set the Drone to have low storage by pressing the storage button.

### Battery:

![battery](https://user-images.githubusercontent.com/55064602/147295021-a028a284-ac08-4ab2-be39-de206e22fae8.png)

For the Drone to be operable, it must have sufficient battery power. If the Drone's battery power becomes low while flying it will automatically trigger the return home feature, and will return to the homelocation using the AutoPilot. You can also manually set the battery power to low by pressing the Battery button in the Drone State Panel.

### AirFrame Status:

![airframestatus](https://user-images.githubusercontent.com/55064602/147295562-92bda255-5fe2-4651-bc78-ff230131a24d.png)

The Airframe status panel keeps you updated about the current position of the Drone. It records the takeoff location and provides information about the current location of the Drone along with the current altitude of the Drone.

### Drone log:

![log](https://user-images.githubusercontent.com/55064602/147296654-73206082-87b9-4d3a-806a-770996b13e79.png)

The Drone log provides information about the different events communicated by the Drone Navigation System. 
For more information about the different types of events communicated by the Drone controller, please look up the SRS (Software Requirement Specification) document.

## Instructions:
- The Signal Handler(Radio) for the drone uses the Ipv4 address of the system you are working on. So, change the server socket host using the Ipv4 address of your current network, which you can find using ip config in cmd. Also, configure the socket for the remote controller using the same Ipv4 address.
       
