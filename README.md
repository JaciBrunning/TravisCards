# TravisCards
Easy, Customizable Badges for the Travis CI Build System.  

## What is this?  
Travis' regular build status images can be a bit small and ordinary. Sometimes they don't match up with other badges or are just a pain to distinguish exactly which branch each one of them comes from. This is why TravisCards exists.  

TravisCards are customizable in their colours, and are substantially bigger than the regular badge. Here are some examples.  
![The Default Theme](https://raw.githubusercontent.com/JacisNonsense/TravisCards/master/samples/default.png)  


![The Alternate Theme](https://raw.githubusercontent.com/JacisNonsense/TravisCards/master/samples/alternate.png)  

![I'm not very good at design](https://raw.githubusercontent.com/JacisNonsense/TravisCards/master/samples/light.png)  

![In case you're a fan of cmatrix](https://raw.githubusercontent.com/JacisNonsense/TravisCards/master/samples/h4x0r.png)  

*Got one you want to submit? PR it, or make a new issue with the 'card' label!*  

The colours of every card can be changed in the .travis.yml file of the GitHub Repository, as well as the title of the card!  

## How?
Creating your own card is very simple. First, edit your .travis.yml file to resemble the following:  
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

Now let's break down what this block does.  
``` cards: ```  The main 'container' block for all the TravisCards settings.  
``` enabled: ```  A list of all the branches that should be included on the card. If nothing is specified, the card will be blank. The card will auto-size itself to fit all the branches. This also works for tags.  
``` title: ```  Optional. The title for the card. If this isn't specified, the card will automatically use the repository's name.  
``` colors: ``` Optional. This contains all the custom colours to make your card unique. If this isn't set, it will automatically use the default values. More info on the colors block can be found in the [formatting guide]()  

To view your card, go to ``` http://dev.imjac.in/travis/GithubUsername/RepositoryName ```  
*NOTE: This page will be blank the first time you visit it. This is because the card is being generated. Refresh the page and you should see your card generated.*  

To include the card on your website or Readme, do the following:  
- Markdown: ``` ![Build Status](http://dev.imjac.in/travis/username/repo) ```
- HTML: ``` <img src="http://dev.imjac.in/travis/username/repo" alt="Build Status" /> ```  

## Offline Use
All of the generation for TravisCards is done through my webserver, which detects when a repo has changed build state and will remake the card accordingly. The web server parses the actual request, and will only launch this java program if the card needs to be re-rendered. This saves on response time and CPU cycles.  

Because of this, the java program requires run arguments to work. Here is the standard format:  
- Arg 0: A Base64 Encoded JSON String containing build data. A standard JSON format looks as follows:  
```json
{
  "title":"Title",
  "repo_name":"My Repo",
  "repo":"Username/Repo",
  "branches": [
    {
      "branch":"master",
      "build":"57",
      "status":"passed",
      "duration":281
    },
    {
      "branch":"development",
      "build":"87",
      "status":"errored",
      "duration":125
    },
    {
      "branch":"testing",
      "build":"91",
      "status":"started",
      "duration":null
    }
  ],
  "colors": {
    "top-border":"#096100",
    "passed":"#17c700"
  }
}
```  
- Arg 1: The directory to place the rendered images. For most purposes, this should just be a '.' (period)
