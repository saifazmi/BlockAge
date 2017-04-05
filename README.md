BlockAge
======= 

**NOTE:** To compile, run and play the game please refer to: [final/README.md](https://github.com/saifazmi/BlockAge/blob/master/final/README.md)

### Project Structure 
```
final/src
├── core
│   ├── BaseSpawner.java
│   ├── CoreEngine.java
│   ├── GameRunTime.java
│   └── UnitSpawner.java
├── entity
│   ├── Base.java
│   ├── Blockade.java
│   ├── Entity.java
│   ├── SortableBlockade.java
│   └── Unit.java
├── graph
│   ├── Graph.java
│   └── GraphNode.java
├── gui
│   ├── CoreGUI.java
│   ├── GameInterface.java
│   └── Renderer.java
├── maps
│   ├── EditorParser.java
│   ├── MapChooserInterface.java
│   ├── MapEditor.java
│   ├── MapEditorInterface.java
│   └── MapParser.java
├── menus
│   ├── EndGameMenu.java
│   ├── MainMenu.java
│   ├── Menu.java
│   ├── MenuHandler.java
│   ├── Options.java
│   ├── OptionsMenu.java
│   └── PauseMenu.java
├── resources
│   ├── audio
│   │   ├── Spell.mp3
│   │   └── a_ninja_among_culturachippers.mp3
│   ├── fonts
│   │   └── basis33.ttf
│   ├── maps
│   │   ├── 0.map
│   │   ├── 1.map
│   │   ├── 2.map
│   │   └── template.map
│   └── sprites
│       ├── backgrounds
│       │   ├── GrassBackground.png
│       │   ├── MainMenu_Glow.png
│       │   ├── MainMenu_Idle.png
│       │   ├── OptionsMenu.png
│       │   ├── Pane.png
│       │   ├── PauseMenu.png
│       │   ├── SandBackground.png
│       │   ├── hell_background.png
│       │   └── yesNoPane.png
│       ├── buttons
│       │   ├── Back-small.png
│       │   ├── Back-small_Hovered.png
│       │   ├── Back_Hover.png
│       │   ├── Back_Idle.png
│       │   ├── Clear.png
│       │   ├── Clear_Hovered.png
│       │   ├── CustomGame_Hover.png
│       │   ├── CustomGame_Idle.png
│       │   ├── MapEditor.png
│       │   ├── MapEditor_Hover.png
│       │   ├── NewGame_Hover.png
│       │   ├── NewGame_Idle.png
│       │   ├── No.png
│       │   ├── No_Hovered.png
│       │   ├── Off_Hover.png
│       │   ├── Off_Idle.png
│       │   ├── On_Hover.png
│       │   ├── On_Idle.png
│       │   ├── Options_Hover.png
│       │   ├── Options_Idle.png
│       │   ├── Pause_Hover.png
│       │   ├── Pause_Idle.png
│       │   ├── Play_Hover.png
│       │   ├── Play_Idle.png
│       │   ├── Quit_Hover.png
│       │   ├── Quit_Idle.png
│       │   ├── ResumeGame_Hover.png
│       │   ├── ResumeGame_Idle.png
│       │   ├── Save.png
│       │   ├── Save_Hovered.png
│       │   ├── Yes.png
│       │   └── Yes_Hovered.png
│       ├── entities
│       │   ├── Base.png
│       │   ├── Base3.png
│       │   ├── blockades
│       │   │   ├── Blockade_Sortable.png
│       │   │   ├── Blockade_UnSortable.png
│       │   │   ├── UnSortable_Blockade.jpg
│       │   │   ├── cyberBlock.png
│       │   │   ├── sandBlock.png
│       │   │   ├── sortableBlock.png
│       │   │   └── sortableBlockSorting.png
│       │   └── units
│       │       ├── AStar_Idle.png
│       │       ├── AStar_Selected.png
│       │       ├── BFS_Idle.png
│       │       ├── BFS_Selected.png
│       │       ├── DFS_Idle.png
│       │       └── DFS_Selected.png
│       ├── labels
│       │   ├── NoStartBlockade.png
│       │   ├── ShowSearch.png
│       │   ├── Sound.png
│       │   └── Tutorial.png
│       └── misc
│           ├── grass640.png
│           ├── grass_background.png
│           ├── hell_background.png
│           └── sand_background.png
├── sceneElements
│   ├── ButtonProperties.java
│   ├── ElementsHandler.java
│   ├── LabelProperties.java
│   ├── Score.java
│   └── SpriteImage.java
├── searches
│   ├── AStar.java
│   ├── BreadthFirstSearch.java
│   └── DepthFirstSearch.java
├── sorts
│   ├── logic
│   │   ├── BubbleSort.java
│   │   ├── InsertSort.java
│   │   ├── SelectionSort.java
│   │   └── SortableComponent.java
│   └── visual
│       ├── SortVisual.java
│       ├── SortVisualBar.java
│       └── Tuple.java
├── sound
│   ├── CircularBufferNode.java
│   └── SoundManager.java
├── stores
│   ├── ImageStore.java
│   └── LambdaStore.java
└── tutorial
    └── Tutorial.java

26 directories, 117 files
```
Knows Bugs    
----------  
The bugs which have been solved are ~~striked~~ off the list.  

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
