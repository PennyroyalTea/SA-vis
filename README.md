# queens problem visualizer
Visualizes one's solution in queens problem and counts energy (number of "bad" pairs).

## how to use?
The programs draws your answer to [Queens problem](http://informatics.mccme.ru/mod/statements/view.php?id=1975).
Your answer must be a permutation of numbers 1 to n like in problem statement.
Put it in a file and write its path to variable **fileRef**.
If the path is wrong, you'll see actual path in error message.

Also it writes the energy to the console.
 
## how to run in IDEA?
It's developed in IDEA, so just clone it there and it should work.
Keep in mind that relative path addresses somehow don't work here, 
so better use absolute address to your queens file.

## how to run in Eclipse?
Create new project and put Queens.java there. Compile it and it should work.

## how to run in other IDEs?
Who knows?

### Set-up
Change these variables to make it more comfortable for you:
* **fileRef** — address of file with answer.
* **screenWidth**, **screenHeight** — default size of window.
* **USE_DEFAULT_SCREEN_SIZE** — if true, uses previous window size.
Otherwise, uses your monitor resolution (can be buggy).
* ***color** — colors of corresponding elements in app. 