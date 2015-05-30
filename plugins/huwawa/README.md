# Huwawa

Strict game-mode restrictions in WorldEdit regions.

## Goals

* Easy specification of a region `/region-mode <game-mode> <region-id>`. Checks
  should be hierarchically aware, though, so that "safe-zone" towns can be set 
  up (like [Dadŝ City](http://uruk.d3mok.net/wiki/Dad%C5%9D).)
* Region sets game mode an all entering (or logging in into that region) and 
  prevents switching game mode (even for Ops and admins)
* Disable certain commands within a region (`/give`, `/tp`, `/kill`, `/butcher`,
  `/clear`, `/heal`, all WorldEdit commands, etc.)
* Announcements to players when entering/leaving a region. Possibly adding 
  region descriptions, too (`/region-desc <text>`, or as conf files).