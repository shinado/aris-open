# Aris Open Project

Aris Open Project is initially designed for Aris Launcher, a terminal launcher where you use your Android with commands. The fundamental idea of Aris Launcher is to expand the capability of data sharing in Android to make things easier, int the same way which you finish a complex task with shell in Linux, rahter than using clicking and dragging. For instance, it takes a lot of steps to 'extract an app's apk, and upload it to server, get a link, and sent this link to some of your friends'. In Aris Launcher, you do it with one single command. Sounds cool, right? Now, since we've decided to relaunch a few apps and include them into Aris Open Universe(AOU), we're working on more features and open source Berris(An elegant simplistic Launcher) and Cherries. 

# Aris Launcher
[CoolAPK](https://www.coolapk.com/apk/shinado.indi.piping) 
[Google Play](https://play.google.com/store/apps/details?id=com.ss.aris) 
# Berris(Trailer)
[CoolAPK](https://www.coolapk.com/apk/197272) 
[Google Play](https://play.google.com/store/apps/details?id=com.ss.berris.trailer) 
# Cherries(coming soon)

You can develope plugins that can run on Aris Launcher, Berris, Cherries, and all coming apps in AOU. You can even develop a new app based on Aris open project and become part of AOU!   

Before you get started, you should probably know what kind of pipe you are going to develop. If you don't know what it can do, please first go to Pipe Store in Aris and check out all the pipes, the source code of which are available in module "pipes". 

Now, let me just walk you through this project. 

The core module is "open-source", simple and straightforward. You probably don't want to make any changes on this unless you'd like to make a pull request(and join Aris core team:) ). If you'd just like to develop pipes for Aris, please focus on module "pipes" and "sample".

Based on "open" module, we have module "sample" which applies "android" scale. The code in this module is really simple and all you need to concern about is the class "YourPipe".
Before you start making any changes on thie module, it's important to understand how Aris works and the most important concept for Aris--connection. Please click this link to learn about this concept before you know what you are going to develop.

After you've ran the "sample" module, made some changes, let's move to module "pipes" where you can check the source code for every pipe available on Pipe Store. 

