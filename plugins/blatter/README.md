# Blatter

Tools for the international pastime of the world of New Uruk.

## Goals

* Arena management commands and quick map saving/loading.
* Team settings and score keeping.
* Relocating of major tournaments to countries with poor human rights records,
  for a fee.

## Commands

* `/arena <region id> <arena name>` to declare a WorldGuard region as a Death
  Cricket arena.
* `/arena save <arena> <map name>` to save the current map.
* `/arena load <arena> <map name>` to load a map.
* `/team <team id> [player1 [player2 [...]]]` to set up a team.
* `/scorekeeper [arena]` to start score keeping in an arena. Friendly fire will
  be disabled, and the match will start after a countdown. All players/teams in
  the arena will be included in the match. Leaving the arena is an instant kill,
  and dead players will be revived as spectators who can't affect the arena or
  other players until the match is over, or an active player has issued a
  `/scorekeeper reset [arena]` command. If no arena is specified, the one the
  player is currently in will be used.
* `/scorekeeper status [arena]` to show current team setups and score for the
  specified arena (or the arena you are currently in).

TBD: How to make scoreboards light up.