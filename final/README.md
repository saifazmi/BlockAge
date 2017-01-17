BlockAge
========

Key Bindings  
------------
- **SHIFT+SPACE:** Pause the game.
- **R:** Displays the route.
- **SHIFT+R:** Displays advanced route.
- **B:** Toggles normal block placement.  
- **SHIFT+B:** Toggles sortable block placement.
- **S:** Un-selects units, sortable blockades & removes routes.
- **ESC:** Opens pause menu (when in game).

**NOTE:** Please un-select the unit with **S** after drawing route with **R**   
if you want to draw the advance route **SHIFT-R** and vice-versa.

Dependencies
------------

- JDK 8 and above
- JavaFX API

How to Run
----------

### Prerequisites  

- Open a terminal.
- ```$``` represents a terminal prompt.
- Make sure the scripts "compile", "run", "run_tests" and "clean" are executable.  
```
$ chmod +x compile run run_tests clean
```

### Compile  
  
To compile the project, run script "compile".    
```
$ ./compile
```  

### Run  
  
To run the project, run the script "run".  
```
$ ./run
```  

Running TestNG Suite
--------------------
  
To run TestNG suite of tests use script "run_tests".  
```
$ ./run_tests
```
  
Clean the directory
-------------------
To clean the directory, run script "clean".  
```
$ ./clean
```  
This removes the following:  
- `out` folder
- `test-output` folder
- `.source` file
