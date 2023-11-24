# RP Character Creator 
### A Character creator for minecraft java.
<br>

Want the pre-setup version? Check download this:


## Features
This is a plugin for minecraft java edition [1.20] and allows you to create roleplay characters for your server
Upon startup, you are provided with a config that lets you set things such as the starting position of the player (preferably a void box) aswell as the position for the main hub. You can also set whether the spawn particles appear and whether the character creator is active upon startup.
You can set your players nickname, which can be limited by a "banned phrases" text file in the config area. Each line is a new banned phrases, it is NOT case sensitive but DOES recognise spaces and special characters.
You can set a players gender based off of 5 selectable options, MALE, FEMALE, NONBINARY, GENDERFLUID and GENDERNEUTRAL.

## Commands
### /new-characer
Lets a player create a new character, can be turned on or off in config file.
/new-character
No permissions required.

### /realname
Lets you get the realname of a player based upon their RP name
/realname <RPNAME>
No permissions required.

### /rpname
Lets you get the roleplay name of a player based upon their realname.
/rpname <PLAYER>
No permissions required.

### /playerInfo
Gives you information about a player, their realname, their rp name, their gender.
/playerinfo <PLAYER>
No permissions required.

### /reloadBannedWords
Reloads the list of banned username phrases without the need of restarting the server.
/reloadbannedwords
Requires OP.

### /cc-reloadconfig
Reloads the config file without the need of restarting the server.
/cc-reloadconfig
Requires OP.

## Setup
To set up the server, place in the config and the banned phrases into the file. Then run the server.
The config setup is:

### Start Position
To setup the start position, set the following variables
<b>startX</b>: <INTEGER>
<b>startY</b>: <INTEGER>
<b>startZ</b>: <INTEGER>
