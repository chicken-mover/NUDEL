# Jehova

Lightning/thunder and storm-related tools.

## Commands

* `/strike [x y z|player]` - Strike the specified player, coordinates, or (if 
  no arguments provided) the current caster's location with a bolt of 
  lightning.
* `/boom [x y z|player]` - Zap the specified player, coordinates, or (if 
  no arguments provided) the current caster's location with a bolt of 
  harmless lightning.
* `/storm [on|off]` - Set the storm state on or off, or (if no arguments are
  provided) toggle it.

## Other features

* Localizes all "fake" lightning events to 100 blocks of its origin. This
  only effects lightning created with `/boom`, or using the lightning effect
  Spigot API methods, and doesn't effect natural lightning or `/strike` 
  commands.
