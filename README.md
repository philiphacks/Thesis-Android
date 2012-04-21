# Collaborative Modeling Framework

This modeling framework uses the [MetaDepth](http://astreo.ii.uam.es/~jlara/metaDepth/) framework to model and generate collaborative Android applications. The following modeling components are included in the framework:

* Chat component
* Dropbox component
* Foursquare component
* Geo component
* List component
* Login component
* SMS component
* Timer component
* Twitter component

The chat, dropbox, list and login components can communicate with a server written in [Node.js](http://www.nodejs.org/). Both the components and the server can be modeled, after which an Android client and a Node.js server implementation will be generated.

## Requirements

You can download the modeling framework from this Github repository. Dependencies:

* [Node.js](http://www.nodejs.org/)
* [Java JDK](http://www.oracle.com/technetwork/java/javase/downloads/index.html)
* [Android SDK 8](http://developer.android.com/index.html)
* [Metadepth](http://astreo.ii.uam.es/~jlara/metaDepth/)

## Getting started

In the samples directory, you can find several example models that will generate working Android applications.
To initiate Metadepth, use the following script

```
set DIR "collaborative/"
load "samples/collaborative"
context myApp
load EGL "templates/egl/codeGenServer.egl"
load EGL "templates/egl/codeGen.egl"
quit
```

and execute 

```
java -jar metaDepth.jar < script
```

The variable DIR, the samples file and the context have to be set manually depending on the example that should be generated.

## Contributors

* [Philip De Smedt](http://www.github.com/philipdesmedt) (author of the modeling framework)
* [Juan de Lara](http://www.ii.uam.es/~jlara) (author of Metadepth and co-promotor of my thesis)
* [Esther Guerra](http://www.ii.uam.es/~eguerra) (author of Metadepth)

* [Hans Vangheluwe](http://msdl.cs.mcgill.ca/people/hv/) (promotor of my thesis)
* [Bart Meyers](http://msdl.cs.mcgill.ca/people/bart/) (PhD candidate at University of Antwerp and co-guide for my thesis :-))
