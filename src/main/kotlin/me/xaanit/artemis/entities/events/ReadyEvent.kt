package me.xaanit.artemis.entities.events

import me.xaanit.artemis.internal.Client

class ReadyEvent(private val cli: Client) : Event(client = cli)