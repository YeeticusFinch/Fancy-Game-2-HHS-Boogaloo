# HHS RPG

Controls:
Arrow keys to move (WASD litterally doesn't work on my computer, idk why)
Left click to use main attack
Right click to use ability
In the futur characters may be able to use unlockable abilities with different keyboard buttons

Maps:
Here is an example of a map:
"Courtyard",0:1,
11111111111111111111111111111
10000000000000000000000000111
10300000000000000000000000111
10000000000000000111000000111
10000111000000000111000000111
10000111000000000111000000021
10000111000000000000000000021
10000000000000000000000000021
10000000000111000000000000021
10000000000111000000000000111
10000000000111000000000000111
10000000000000000000000000111
10000000000000000000000000111
11111111111111111111111111111

The first line has the map name (gets displayed in top left corner of game) and it has the enemies in this format:
enemyType:amount
So for this map there is one type 0 enemy, which will spawn in a random spot.

Here is the key:
0 -> empty
1 -> wall
2 -> door to next map
3 -> player spawn point
