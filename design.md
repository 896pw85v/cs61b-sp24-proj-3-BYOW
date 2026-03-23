# Project 3

The point is to manipulate a 2D grid and place:
- rooms
- paths

I don't need to worry about the rendering and stuff. Every drawing, sizing, tilling problem is handled in the skeleton coded. Thank you so much! I just need to create a Tile[][] and feed it to the renderer.  
The problem being how to place the rooms in a messy way and occupy 50% of the grid, and the other problem is how to connect every single room on the grid.   
My idea is to generate at least 3*3 blocks, and keep generating at random so that the total area exceeds some amount. And also, keep their information in some list.   
Then, by the list, every rooms is connected by a 3 block wide path, until the last one is connected, then there will be one single path that connects all the room. Of course, it's boring. Another idea is to store them in some kind of graph, that represents their actual location and distance, then use shortest path or something to find the order to connect them, then generate the paths. Hallways doesn't have to have turns, that's great news meaning that simply connect point to point is sufficient, only straight up down left right.  
Saving should work by storing seeds.   
> A very important thing to notice is that grid is bottom to top, so 0th row should be drawn at the bottom. 

## 1 Classes and Data Structure

### Room

- length
- width
- x
- y

This is enough information to create room and have location, plus some methods like area(), draw(), etc., those work with the api. This should be information and not completely related to the placed tiles on the grid. The seperation makes the modification to walls easier. 

### Hallway

- start x, y
- end x, y

This describes the hallway. Not sure if there is a need to variate width. 

### Probably Helper Class
There will be somewhere that handles with: 
- break walls for fusing overlapped rooms (if room placement are completely random)
- break walls for connecting hallway (basically breaking walls at desired blocks)

## Algorithm

### Rooms
Rooms have an origin coordinate at the bottom left. There are two ways to put them: 
- Random  
generate random origin and size, repeate until total area > 50% of grid. This bring 3 cases:
  - room A and room B are separated. Edge case they are side by side with walls touching
  - room A falls inside of room B, as room A is completely smaller than B (length and width), therefore completely surrounded by B. In this case room A should be cleared. 
  - room A intersects with room B, means A is longer or wider than B and their walls intersect. In this case fusing the rooms by breaking some walls results in one bigger room with non-rectangular room. But this only makes building hallways harder. 
- Careful  
place a room, record it, then place another which does not fall into its territory, repeat. This approach is more complicated and does not produce overlaps, which is actually a way of dismorphing room shapes. 

### Hallway
This is so much harder.  
The point is to, first, have two rooms with no other room in between, then find two points on the wall that lies on the same line, connect them, break room wall and place hallway walls. All these steps are hard.   

### Ideas
1. A way to generate rooms is to select random dots in the grid, expand them randomly until they grow to various sizes. This way it is similar to using noise function which I don't know about. Maybe should try. 
2. A hallway is also a type room, with special case that they are restricted to 3 blocks of width, or length. 

### Saving
First idea is storing the seeds used in the Random obj, in this way recreating the world means to run the whole algorithm again. 
Another idea is to really store the grid in some way, toString, serializing, encoding; however, this approach requires implementation of io operation, which is very annoying.  