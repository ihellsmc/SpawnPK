package me.ihellsmc.spawnpk.active.manager;

import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter @Setter
public class ActiveManager {

    public Set<UUID> activePlayers = new HashSet<>();

}
