# Pudddle Jumper

Tools for setting flight paths and fixed time-of-day flights between the
Skyports of New Uruk. 

## Goals

* Define an aircraft using a WorldEdit selection.
* Schedule a flight, and define a destination and optional waypoints (max 3).
* Start the flight running.

Each time the flight runs, the aircraft and all players on board are teleported
to each waypoint, with a delay, giving a bit of a "flying time" experience.

## How to

### Setup

* Select a region with WorldEdit and issue `/flight new <flight-id>`. This will 
  define your aircraft and create a new flight.
* Issue `/flight start <flight-id> <time>` to set when the flight leaves.
* Specify a series of locations with `/flight waypoint <flight-id> <x> <y> <z>`.
  These will be the stopover locations on the route, with the last waypoint
  being the destination.
* If you want the return flight to be scheduled, set 
  `/flight return <flight-id> <time>`. Otherwise the flight will return along
  the same path after a short delay.

### Starting, stopping, deleting

* Use `/flight run <flight-id>` to set a flight running.
* Use `/flight halt <flight-id>` to suspent a flight and reset the aircraft to
  the starting position.
* Use `/flight delete <flight-id>` to clear all data relating to a flight. The
  flight must be halted for this to work.

## Ensuring a safe and pleasant journey

* `/flight annouce <flight-id> <waypoint-id> <message>` to send a message to
  passengers. Waypoint `0` is always the start. Waypoint `-1` is always the end.

## Notes on flying times

Flying times are calculated as a function of distance, not the number of
waypoints. A linear distance of, say, 1000 blocks, will always take the same
amount of time, no matter how many waypoints you add.

Long flights with no waypoints will have a delayed takeoff, so you should add at
least one.

To avoid problems with rapid teleporting, the minimum amount of time spent at
a waypoint is 10 seconds. If a flightpath has more waypoints than it needs for
a given flying time, then some (or all) may be skipped.

## Air traffic control

The New Uruk Air Traffic Control Board places restrictions on the size of
aircraft used in commercial service, as well as the mid-air distance 
requirements between filed flight plans.

Trying to place waypoints too close to each other (within 50 blocks) will is not
allowed, to prevent midair collisions. Start and end points will not be
collision-checked in this way (to avoid making skyport consturction too 
complex.)

Aircraft themselves can be no more than 50 blocks per side in any direction.