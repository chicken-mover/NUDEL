# Huwawa

Strict game-mode restrictions in WorldEdit regions.

## Goals

* Easy specification of a region `/region-mode <game-mode> <region-id>`
* Region sets game mode an all entering (or logging in into that region) and prevents switching game mode (even for Ops and admins)
* Disable certain commands within a region (`/give`, `/tp`, `/kill`, `/butcher`, `/clear`, `/heal`, all WorldEdit commands, etc.)
* Announcements to players when entering/leaving a region. Possibly adding region descriptions, too (`/region-desc <text>`, or as conf files).

## Dependencies

* WorldEdit
* WorldGuard

These should be forked and versioned builds created, to ensure we always have a Bukkit/Spigot compatible version to hand.
