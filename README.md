# RP Character Creator [ALPHA]
### A Character creator for minecraft java.
<br>

> :warning: This plugin in currently in alpha. It will contain bugs and is subject to many changes!
Want the pre-setup version? Check download this:<br>
https://github.com/ItsDoctorLeaf/plugin-rpCharacters/tree/main/dragAndDrop

## Features
This is a plugin for minecraft java edition [1.20] and allows you to create role play characters for your server
Upon startup, you are provided with a config that lets you set things such as the starting position of the player (preferably a void box) aswell as the position for the main hub. You can also set whether the spawn particles appear and whether the character creator is active upon startup.<br>
You can set your players nickname, which can be limited by a "banned phrases" text file in the config area. Each line is a new banned phrases, it is NOT case sensitive but DOES recognise spaces and special characters.<br>
You can set a players gender based off of 5 selectable options:<br> MALE, FEMALE, NONBINARY, GENDERFLUID and GENDERNEUTRAL.
<br><br>
## Commands
### /new-character
Lets a player create a new character, can be turned on or off in config file.<br>
/new-character<br>
No permissions required.<br>

### /realname
Lets you get the real name of a player based upon their RP name<br>
/realname \<RPNAME\><br>
No permissions required.<br>

### /rpname
Lets you get the role play name of a player based upon their real name.<br>
/rpname \<PLAYER\><br>
No permissions required.<br>

### /playerInfo
Gives you information about a player, their real name, their rp name, their gender.<br>
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

### Server Name
When a player starts to create a character, this is the name of the server that appears upon character creation and in the welcome message:<br>
<b>serverDisplayName</b>: \<STRING\><br>
<br>
### Do Welcome Message
A toggle for if the welcome message is shown in the chat:<br>
<b>doWelcomeMessage</b>: \<BOOL\><br>
<br>
### Start Position
To setup the start position (Where a void box should be), set the following variables:<br>
<b>startX</b>: \<INTEGER\><br>
<b>startY</b>: \<INTEGER\><br>
<b>startZ</b>: \<INTEGER\><br>
<br>
### Spawn Position
To set up the spawn position (Where the main spawn should be), set the following variables:<br>
<b>spawnX</b>: \<INTEGER\><br>
<b>spawnY</b>: \<INTEGER\><br>
<b>spawnZ</b>: \<INTEGER\><br>
<br>
### Particles
If you want particles to spawn upon the player finishing their character setup, toggle the following variable:<br>
<b>spawnParticles</b>: \<BOOLEAN\>
<br>
### Create Characters
If you want players to be able to create characters, toggle the following variable:<br>
<b>characterCreation</b>: \<BOOLEAN\>
