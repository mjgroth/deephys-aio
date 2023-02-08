 <!--- https://github.com/mgroth0/deephy -->
 
[![main](https://github.com/mjgroth/deephys/actions/workflows/main.yml/badge.svg)](https://github.com/mjgroth/deephys/actions/workflows/main.yml)

# Deephys: Neural Activity Visualizer

This is the code for the paper _________. 

### 🚀 Getting started: [https://deephys.org](https://deephys.org)

### 🔥 Importing Neural Activity to Deephys: [![Documentation Status](https://readthedocs.org/projects/deephys/badge/?version=latest)](https://deephys.readthedocs.io/en/latest/?badge=latest) <a href="https://colab.research.google.com/github/mjgroth/deephys-aio/blob/master/Python_Tutorial.ipynb" target="_parent"><img src="https://colab.research.google.com/assets/colab-badge.svg" alt="Demo"/></a>

## How to Report Bugs 🐛

If there is an issue or you have a feature request, please report it in https://deephys.youtrack.cloud/

If there is a bug, please report it to https://deephys.youtrack.cloud/. 

In order for us to most effectively resolve the issue, please provide the following information in your report:
1. Your operating system
2. A description of what you expected to happen
3. A description of what happened instead
4. **The full error output**. This is very critical for us to fix an error. 
	- For most errors, a pop up will show with the error output. Please copy and paste this.
	- For some errors, there is no error pop up. For example, if the app crashes. If this is the case, please open up the app and go to the settings (click the gear). In the settings, there is a button that will open the logs folder. Please find the error text here. If you are not sure which file to send, please zip the entire "log" folder and send attach it to the issue.
	- If you cannot open the app, you won't be able to click the button to find the log folder. Here is where you might find it on each os:
		- Mac: `/Users/<you>/Library/Application Support/Deephys/Log`
		- Windows: `C:\\Users\<you>\AppData\Roaming\Deephys\Log`
		- Linux `<your home folder>/.matt/Deephys/Log`
5. **A complete list of steps in order to reproduce the issue**. Sometimes this is not possible or easy. But if you can tell us, step by step, how to reproduce the error on our end it makes the bug-fixing process much easier.
	- If you generated your own data (.test and .model files) and you think that the bug has something to do with these files:
		- please share the files
		- If possible, please share the code that generated these files. Ideally, share a functional google colab.

The **error output** and **reproduce steps** are the most important. With both of these provided, we can provide a speedy bug-extermination 😳🐛😵


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
- Windows Installation: (TBD)


### Project Setup

1. `git clone --recurse-submodules -j10 https://github.com/mjgroth/deephys-aio`
2. `cd deephys-aio`

### Running From Source

The main command is `./gradlew :k:nn:deephys:run --stacktrace`. If experience any issues try to reset the settings and state
with `./gradlew :k:nn:deephys:run --stacktrace --args="reset"` and then try the command above again.

If you have never worked on a java project on your machine, you likely will get an error complaining about no JDK being found. There are a few ways you can handle this. One is to set your JAVA_HOME environmental variable e.g. `JAVA_HOME=/opt/homebrew/opt/openjdk@17 ./gradlew :k:nn:deephys:run`. Another is to append `org.gradle.java.home=/path/to/your/jdk/home` to your `~/.gradle/gradle.properties`. More information can be found in [Gradle documentation](https://docs.gradle.org/current/userguide/build_environment.html). 

