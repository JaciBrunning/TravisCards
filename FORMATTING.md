# TravisCards - Formatting Guide
This document will contain the basics of how to format your TravisCards in the .travis.yml file and customize it to your heart's content.  

## Standard Format
Below is a sample .travis.yml extract for TravisCards  
```yaml
cards:
  enabled:
    - "master"
    - "development"
  title: "My Repo"
  colors:
    background: "#000000"
    passed: "#17c700"
```
``` cards: ```  The main 'container' block for all the TravisCards settings.  
``` enabled: ```  A list of all the branches that should be included on the card. If nothing is specified, the card will be blank. The card will auto-size itself to fit all the branches. This also works for tags.  
``` title: ```  Optional. The title for the card. If this isn't specified, the card will automatically use the repository's name.  
``` colors: ``` Optional. This contains all the custom colours to make your card unique. If this isn't set, it will automatically use the default values.

## Colours  
In the ``` colors ``` block, there are different values you can use to change different 'groups' of colours. Following is a list of all the colours you can change and what they affect.  
- ``` background: ``` Self-explanatory. This is the background colour of the card. Default: ```#0f0f0f```
- ``` top-border: ``` The top, 8px tall border on top of the card. This can be the same as the background colour for a uniform background. Default: ```#ff0fca```
- ``` text: ``` The text colour used for the title and branch headings. Default: ``` #e2e2e2 ```
- ``` passed: ``` The text colour for builds that are passing. Default: ``` #6af677 ```
- ``` failed: ``` The text colour for builds that are failing (errored multiple times). Default: ``` #ff0030 ```
- ``` errored: ``` The text colour for builds that have errored (first fail after a passing build). Default: ``` #ff7878 ```
- ``` created: ``` The text colour for builds that are waiting to be started. Default: ``` #ffe670 ```
- ``` started: ``` The text colour for builds that are currently in progress. Default: ``` #ffb329 ```
- ``` cancelled: ``` The text colour for builds that have been cancelled by the user. Default: ``` #ff0063 ```
