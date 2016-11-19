#!/bin/bash

brew update
brew uninstall apktool && true; brew install apktool
brew uninstall ideviceinstaller && true; brew install ideviceinstaller 
brew uninstall libimobiledevice && true; brew install libimobiledevice 
brew uninstall carthage && true; brew install carthage 
sudo rm -rf /usr/local/{lib/node{,/.npm,_modules},bin,share/man}/{npm*,node*,man1/node*}
sudo rm -rf '/usr/local/lib/dtrace/node.d'
sudo rm -rf '/usr/local/etc/bash_completion.d/npm'
sudo rm -rf '/usr/local/include/node'
brew uninstall node && true
brew uninstall node4-lts && true
brew uninstall node6-lts && true
brew tap homebrew/versions
brew search node
brew install homebrew/versions/node6-lts
node -v
npm -v
npm cache clean
npm uninstall -g deviceconsole && true
npm uninstall -g ios-deploy && true
npm install -g -f deviceconsole
npm install -g -f ios-deploy