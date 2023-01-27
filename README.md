 <!--- https://github.com/mgroth0/deephy -->

# Deephys: Deep Electrophysiology for OOD 

This is the code for the paper _________. 

### 🚀 Getting started in 60 seconds:

1. 📖 Watch crash course on how to use Deephys
2. Install [precompiled](#precompiled-app-installation) 😎 app
3. 🔥 [Learn](#-neural-activity-extraction-) to extract neural activity for Deephys
4. 🥳 Let's deephys


## Precompiled App Installation 

### 🍏 Silicon Mac 

1. Download `deephys-mac-silicon.zip` from the latest [release](https://github.com/mjgroth/deephys/releases)
2. Unzip it
3. Drag the .app file to your Applications folder and overwrite if prompted
4. Launch by double-clicking the app

###  🍎 Intel Mac Installation

1. Download `deephys-mac-intel.zip` from the latest [release](https://github.com/mjgroth/deephys/releases)
2. Unzip it
3. Drag the .app file to your Applications folder and overwrite if prompted
4. Launch by double-clicking the app

### 🍑 Linux Installation 

1. Download `deephys.linux_x86_64.zip` or `deephys.linux-arm64.zip` from the latest [release](https://github.com/mjgroth/deephys/releases)
2. Unzip it
3. Launch the app by double clicking "deephys/bin/deephys"

### 🍇 Windows 

1. Download `deephys.windows.zip` from the latest [release](https://github.com/mjgroth/deephys/releases)
2. Unzip it
3. Launch the app by double clicking "deephys.exe"


## 🔥🔥🔥 Neural Activity Extraction 🔥🔥🔥

### 🥱 I am feeling lazy, give me some neural activity
 
[This Google Drive folder](https://drive.google.com/drive/folders/1755Srmf39sBMjWa_1lEpS-FPo1ANCWFV) contains
sub-folders with different datasets and networks. Select one `.model` and visualize several `.test` files that correspond to different distributions. 

[//]: # (from Google Drive)

[//]: # (  - `insert_model_name_here_anirban.model`)

[//]: # (  - `CIFARV1_test.test`)

[//]: # (  - `CIFARV2.test`)

### 🤓 Extracting neural activity is not hard for me, let's do it and plug it to Deephys 

First, extract all the activity and images (size should be 32x32 pixels) using your favourite ML library. You should obtain the following:
```python
    [all_activs,all_outputs] #Lists containing neural activity for intermediate and output layer
          # each is a multidimensional list of dimensions  [#layers, #neurons, #images]. 
          #The output layer is always mandatory to be present.
    all_images #List containing images resized to 32x32 pixels, it has size [#images,#channels,32,32].
    all_cats #Labels is a 1-dimensional list of ground-truth label number
```

Next, install our pip package. Importantly, the version of the pip package you install must correspond with the app version that you are using. Each app release version says the "Compatible pip package" at the top. Please install that exact version. For example, if you are using app version [1.27.1](https://github.com/mjgroth/deephys/releases/tag/1.27.1), you will need pip version 0.8.6 like so:

`pip install deephys==0.8.6`

Then, you can visualize this data in Deephys using just few lines of code:

```python
import deephys as dp
test = dp.import_test_data(
    name = "CIFAR10",
    classes = classes, #List with all category names
    state = [all_activs,all_outputs], #List with neural activity
    model = model, #Structure describing the model (see documentation)
    pixel_data = all_images, #Images resized to 32x32 pixels
    ground_truths = all_cats.numpy().tolist() #Labels
    )
test.suffix = None
test.save()
# The data is now saved to a file called "CIFAR10.test"
```

📖 Learn here: 

<a href="https://colab.research.google.com/github/mjgroth/deephys-aio/blob/master/Python_Tutorial.ipynb" target="_parent"><img src="https://colab.research.google.com/assets/colab-badge.svg" alt="Demo"/></a>
[![Documentation Status](https://readthedocs.org/projects/deephys/badge/?version=latest)](https://deephys.readthedocs.io/en/latest/?badge=latest)




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
		- Linux '<your home folder>/.matt/Deephys/Log'
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

