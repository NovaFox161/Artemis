package me.xaanit.artemis.entities

class PermissionOverwrite(val allow: Array<Permission>, val deny: Array<PermissionOverwrite>)