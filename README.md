# HHS RPG

Controls:
Arrow keys to move (WASD literally doesn't work on my computer, idk why)
Left click to use main attack
Right click to use abilities / secondary attack
Some abilities are activated by the spacebar or shift
In the futur, spacebar abilities can be activated by middle mouse button

Maps:
Here is an example of a map:
"A211",0:2,1:0,2:0,3:0,4:0,5:1,
11111111111111111111111111111111111111
10050000000000000005000000000000500111
10400011000000001100011000110001100001
10050011000000001100011000110001100001
11111111000000111100011000110001100001
11111111000000111100011000110001100001
10000000000004111100011000110001100001
10000000000000111100011000110001100001
10400000000000000000000000000000005001
10000000000000000000000000000000004001
10000000000110001100011000110001100001
10000000000110001100011000110001100001
11000000000110001100011000110001100001
11000000000110001100011000110001100001
11000000000110001100011000110001100001
10000000000110001100011000110001100001
10000000000000000050000000000000000001
13322001111111111111111111111111105001
11111111111111111111111111111111111111

The first line has the map name (gets displayed in top left corner of game) and it has the enemies in this format:
enemyID:amount
So for this map there is one type 0 enemy, which will spawn in a random spot.

Here is the key:
0 -> empty
1 -> wall
2 -> door to next map
3 -> player spawn point
4 -> enemy spawn point
5 -> health pack

You do not need to edit code to add a map.

To add an enemy to the map, in the top put the enemy ID followed by a colon followed by the number of that enemy followed by a comma (yes you need a trailing comma)
Make sure to right out all prior enemy IDs before, and in order.
Example:
0:0,1:1,2:0,3:0,4:0,5,2,
This will add one French teacher and two shelbys

If you want to add a cutscene, put “!!!” in the first line, your message in the second, and the image name (with file extension but not the path) on the third line.

If you want to add an enemy, follow these steps:
It’s easiest to just copy paste an existing enemy.

Extend class Enemy

In the constructor, put “super(speed, damage, hp, ID)
The ID is the number corresponding to the enemy (Darius is 0, French teacher is 1)

Create a move method, an attack method, an attack2 method, and a draw method. In the draw method, call the superclass draw method.
You are also probably going to need a projectile collision method, along with ArrayList(s) for projectiles.

Add an image to the images folder under the name “e”+ID+”.png” (ex: “e5.png” is mr shelby’s face)
Add another image to that same folder under the name “e”+ID+”m.pmg” (ex: “e5m.png” is mr shelby’s meme face with rainbow hair)
That second image will only show up if the player is playing as Claire and uses the meme ability
Two attack classes have already been programmed: Projectile and Laser
Finally go into the map class and add your new enemy to the switch statement under the same case number as the ID

If you want to create a new playable character, the steps are very similar to those of the enemies, but you are going to need to program 3 power ups.
For each power up icon, upload it under the name “h”+PlayerID+“m”+PowerUpId+”.png” (ex: “h3m0” is carl’s Unicycle)
Whenever the player steps on a power up, their “protected int mode” field automatically be set to the number corresponding to the power up ID.
Players can also use the projectile class and the Laser class
