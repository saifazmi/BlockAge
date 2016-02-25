BestRTS
=======
**Team Name:** D3  
[Link](https://github.com/adam-p/markdown-here/wiki/Markdown-Cheatsheet) to the Markdown cheat sheet for editing this file.  

Key Bindings  
------------
- **P:** pause units
- **R:** toggle route display  

Knows Bugs    
----------
#### Breadth First Search
- Occasionally can't find a route even though routes are possible.  

#### Depth First Search  
- Occasionally can't find a route even though routes are possible.  

#### AStar
- Occasionally make diagonal moves.  

#### Unit  
- Transitions don't work if unit doesn't spawn at (0,0)

#### Renderer  
 - Route lines don't draw from the route start, but from the 2nd node

#### Overlay  
 - ~~Hovering over the Blockade causes the grid to move (Paul is moving elements around to fix this)~~  

#### Renderer   
 - ~~Gambling Error - Occasionally program thinks it isn't on the FX Thread when it is. (Fixed - Duplicate line caused the error)~~
