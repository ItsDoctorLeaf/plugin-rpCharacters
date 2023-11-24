# RP Character Creator 
### A Character creator for minecraft java.
<br>

Want the pre-setup version? Check download this:


## Features
This is a plugin for minecraft java edition [1.20] and allows you to create roleplay characters for your server
Upon startup, you are provided with a config that lets you set things such as the starting position of the player (preferably a void box) aswell as the position for the main hub. You can also set whether the spawn particles appear and whether the character creator is active upon startup.<br>
You can set your players nickname, which can be limited by a "banned phrases" text file in the config area. Each line is a new banned phrases, it is NOT case sensitive but DOES recognise spaces and special characters.<br>
You can set a players gender based off of 5 selectable options, MALE, FEMALE, NONBINARY, GENDERFLUID and GENDERNEUTRAL.
<br><br>
## Commands
### /new-characer
Lets a player create a new character, can be turned on or off in config file.<br>
/new-character<br>
No permissions required.<br>

### /realname
Lets you get the realname of a player based upon their RP name<br>
/realname \<RPNAME\><br>
No permissions required.<br>

### /rpname
Lets you get the roleplay name of a player based upon their realname.<br>
/rpname \<PLAYER\><br>
No permissions required.<br>

### /playerInfo
Gives you information about a player, their realname, their rp name, their gender.<br>
/playerinfo \<PLAYER\><br>
No permissions required.<br>

### /reloadBannedWords
Reloads the list of banned username phrases without the need of restarting the server.<br>
/reloadbannedwords<br>
Requires OP.<br>

### /cc-reloadconfig
Reloads the config file without the need of restarting the server.<br>
/cc-reloadconfig<br>
Requires OP.<br>

## Setup
To set up the server, place in the config and the banned phrases into the file. Then run the server.<br>
The config setup is:<br><br>

### Start Position
To setup the start position, set the following variables<br>
<b>startX</b>: \<INTEGER\><br>
<b>startY</b>: \<INTEGER\><br>
<b>startZ</b>: \<INTEGER\><br>
