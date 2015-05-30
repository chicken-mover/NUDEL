# St Clemens

Tools for things at certain times of day. Mainly focussed on bell tower 
mechanics.

## Goals

### Bells and bell-towers

* `/toll <time> <bell-id> <tune-id>` - Play the notes from the designated tune 
  as a prelude, and then chime the hour using the designated bell sound. Plays 
  at the location the command was invoked, and must be invoked by a player (to
  prevent command blocks from trying to schedule identical bells repeatedly). 
  [Only people within a certain distance will be able to hear the bell](http://en.wikipedia.org/wiki/St_Mary-le-Bow).
* `/toll <tune-id>` - Play a specified tune here and now. This command is 
  suitable for command blocks. Only people within a certain distance will be 
  able to hear the bell.
* `/tune <id> [notes...]` - (Re-)define a tune. For example: `/tune orfeo E4 B5 
  E5 E5 D5 E5 D5 B5 B5 D5 A4 F#4`. All notes are played at the same speed (this 
  is for bell towers, after all). Empty tunes are valid (`/tune 4-33` will still
  define a tune). *Not sure how good Minecraft's note specifications are, so we
  might be limited to certain octaves here.*
* `/play <tune-id>` - Check how a tune sounds. This will only play it for you.

## Other functions

* `/switchout <time>` - This command requires a WorldEdit selection to be 
  defined for the current player. It will cause the contents of that selection 
  to be emplaced at the specified time of day. So, for example, if you define a 
  large gate at the edge of a city, and you want it to open and close at certain
  times of day, you could select the doors and issue `/switchout 6:00`; then 
  delete the doors and issue `/switchout 23:00`. *This will need some kind of 
  upper limit on the number of blocks it will manipulate, to prevent people 
  going crazy and DOSing the server accidentally. It should also require at 
  least X number of "hours" between commands, to prevent people trying to make 
  animations with it.*<br><br>This command will provide no animation, of course,
  so it shouldn't be used for any kind of complex movement. Really, it should be
  used for the kind of thing you want to change when no one is looking.