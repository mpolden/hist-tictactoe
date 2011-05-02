Tic-tac-toe for Android
=======================

[Martin Polden](https://github.com/martinp) and 
[Vegard Johannessen](https://github.com/vegardoj)s Android project, done as a
part of the Android course at AITeL (The Faculty of Informatics and e-Learning,
Sør-Trøndelag University College) in spring 2011.

Features
--------

* Singleplayer mode with easy difficulty
* Multiplayer with shared screen
* Multiplayer using two phones over TCP/IP
* Scalable board size ranging from 3x3 to 7x7 with variable lengths to win
* Custom menus and graphics

Compiling and deploying
-----------------------

This project uses a standard Maven project layout. To compile:

    ANDROID_HOME=/path/to/android-sdk mvn package

To deploy it on your phone or emulator:

    ANDROID_HOME=/path/to/android-sdk mvn android:deploy 
