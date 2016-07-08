# Extensible Platform for Structure Visualization and Navigation  - ExPreSSiVeNess

this is a graph visualisation tool made in Eclipse Equinox. 
It is consisted of one core plugin, two source and two visualisation plugins.
You can make your source plug in (Need to implemetn interface Source.) or visualisation plug in (Need to implement interface Visualiser).
When you start program from menu Graph you should pick tyope of source if you pick npm in the form you'll need to set path to the node moduel (for example body-parser),
and if you choose twitter you'll need to put your credentials for twitter api and username of person you want to visualise followers.
Supported operations: zoom(point and center), pan view, bird view, tree view, search and filtering.
All dependencies are written in MANIFEST.mf of every plug in, and won't be pushed to this repository.


