#!/bin/bash

cd $TRAVIS_BUILD_DIR

export CHROME_BIN=chromium-browser
export DISPLAY=:99.0
sh -e /etc/init.d/xvfb start
nvm install 6.9.4
nvm use 6.9.4
