<!--- https://github.com/mgroth0/deephy -->

# Deephys: Deep Electrophysiology for OOD 

This is the code for the paper _________. 

### 🚀 Getting started in 60 seconds:

1. 📖 Watch crash course on how to use Deephys
2. Install [precompiled](https://github.com/mjgroth/deephys-aio/edit/master/README.md#precompiled-app-installation) 😎 app
3. 🔥 [Learn](https://github.com/mjgroth/deephys-aio/edit/master/README.md#-neural-activity-extraction-) how to store neural activity for deephys visualization
4. 🥳 Let's deephys


## Precompiled App Installation 

### 🍏 Silicon Mac 

1. Download `deephys-mac-silicon.zip` from the latest [release](https://github.com/mgroth0/deephy/releases)
2. Unzip it
3. Drag the .app file to your Applications folder and overwrite if prompted
4. Launch by double-clicking the app

###  🍎 Intel Mac Installation

1. JDK 17 must be installed according to [JDK Setup](#jdk-setup)
2. Download the `deephys-mac-intel.zip` from the latest [release](https://github.com/mgroth0/deephy/releases)
2. Unzip it
3. Launch with `/usr/local/Cellar/openjdk@17/17.0.5/bin/java deephys.jar` (replace with appropriate JDK)

### 🍑 Linux Installation 

There is currently no pre-compiled release for linux. Please see [Development Instructions](https://github.com/mjgroth/deephys-aio/edit/master/README.md#development-instructions-)

## 🔥🔥🔥 Neural Activity Extraction 🔥🔥🔥

### I am feeling lazy 🥱, give me some neural activity
 
[This Google Drive folder](https://drive.google.com/drive/folders/1755Srmf39sBMjWa_1lEpS-FPo1ANCWFV) contains
sub-folders which each contain a set of data files that can be used together. At a minimum, the app requires
one `.model` and at least one `.test` file that correspond to each other. Multiple `.test` files that all correspond to
the same `.model` can be viewed together in order to compare the way a single network behaves with different test sets.

[//]: # (from Google Drive)

[//]: # (  - `insert_model_name_here_anirban.model`)

[//]: # (  - `CIFARV1_test.test`)

[//]: # (  - `CIFARV2.test`)

### This is not hard for me 🤓, let's extract neural activity for deephys 

Follow the demo here to extract neural activity that can be viewed in the app.

<a href="https://colab.research.google.com/github/mjgroth/deephys-aio/blob/master/Python_Tutorial.ipynb" target="_parent"><img src="https://colab.research.google.com/assets/colab-badge.svg" alt="Open Demo In Colab!"/></a>

[Read the Python API Documentation here](https://matt-groth-deephys.readthedocs-hosted.com/en/latest/)

[![Documentation Status](https://readthedocs.com/projects/matt-groth-deephys/badge/?version=latest&token=993a0e6932110ddd9080ba7fea46fda458721f5293f465bbd55054b94e30f2d9)](https://matt-groth-deephys.readthedocs-hosted.com/en/latest/?badge=latest)

## How to Report Bugs 🐛

If there is an issue or you have a feature request, please report it in https://deephys.youtrack.cloud/

For bugs, providing certain information will help solve it more quickly. In particular, copying and pasting and command
line output will help. If you are running from source or on an intel mac, this will be easy since you are already in a command
line. But on Silicon Mac's running pre-compiled code, there is no console. In order to share console output from the
pre-compiled app, please do the following:

1. Navigate in terminal to the folder `deephy.app/Contents/MacOS`
2. Run the command `./deephy`

## Development Instructions 🤓

This is kotlin/gradle project. To compile and run from source requires JDK. If you have never worked on java or kotlin code before, you likely do not have them. There are multiple ways to install these.

### JDK Setup

JDK 17 is recommended: We recommend installing openjdk17. Alternatively, JDK 17 corretto, which can be installed through IntelliJ, has worked for some users.
 
- Mac Installation: [Homebrew](https://brew.sh/) with `brew install openjdk@17`
- Linux Installation: `sudo apt install openjdk-17-jdk`


### Project Setup

1. `git clone --recurse-submodules -j10 https://github.com/mjgroth/deephys-aio`
2. `cd deephys-aio`

### Running From Source

The main command is `./gradlew :k:nn:deephys:run --stacktrace`. If experience any issues try to reset the settings and state
with `./gradlew :k:nn:deephys:run --stacktrace --args="reset"` and then try the command above again.

If you have never worked on a java project on your machine, you likely will get an error complaining about no JDK being found. There are a few ways you can handle this. One is to set your JAVA_HOME environmental variable e.g. `JAVA_HOME=/opt/homebrew/opt/openjdk@17 ./gradlew :k:nn:deephys:run`. Another is to append `org.gradle.java.home=/path/to/your/jdk/home` to your `~/.gradle/gradle.properties`. More information can be found in [Gradle documentation](https://docs.gradle.org/current/userguide/build_environment.html). 
