BlockAge
=======
**Team Name:** D3  

Key Bindings  
------------
- **SPACE:** Pause the game
- **R:** Toggle route display
- **SHIFT+R:** Toggle advanced route display
- **B:** Toggle normal block placement  
- **SHIFT+B:** Toggle sortable block placement.
- **S:** Toggle unit selection  
  
**NOTE:** Please un-select the unit with **S** after drawing route with **R**   
if you want to draw the advance route **SHIFT-R** and vice-versa.

Knows Bugs    
----------  
#### Blockade  
- ~~Cannot place blockades after starting a new game without quiting  
the game.~~  

#### Custom Game  
- ~~When redirected to map creator it pop-ups a window.~~  

#### Base Spawner
- ~~Base can be placed anywhere (on blockades, off of grid etc)~~
- ~~Base defence rendering outside the grid in edge cases.~~

#### Breadth First Search
- ~~Occasionally can't find a route even though routes are possible.~~

#### Depth First Search  
- ~~Occasionally can't find a route even though routes are possible.~~

#### AStar
- ~~Occasionally make diagonal moves.~~

#### Unit  
- ~~If boxed in the unit stops moving and the game crashes.~~ 
- ~~Cannot select unit after generating route with SHIFT+R.~~

#### SortVisualisation
- ~~Doesn't reset the visualiser for new game.~~

#### Renderer  
- ~~Route lines don't draw from the route start, but from the 2nd node~~

#### Overlay  
- ~~Hovering over the Blockade causes the grid to move (Paul is moving elements around to fix this)~~  

#### Renderer   
- ~~Gambling Error - Occasionally program thinks it isn't on the FX thread when it is. (Fixed - Duplicate line caused the error)~~  

Contributors
------------
- [Saifullah Azmi](https://github.com/saif-azmi)  
- [Dominic Walters](https://github.com/domWalters)  
- [Evgeniy Kim](https://github.com/yev1master)  
- [Anh Tuan Pham](https://github.com/istatsuki)  
- [Hung Hoang](https://github.com/ParityB1t)  
- [Paul Popa](https://github.com/PaulPopa)  
