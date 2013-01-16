package com.lugar.steelbird.controllers;

import com.lugar.steelbird.mathengine.Helicopter;

public interface GameController {

    public void playerKillAI(Helicopter helicopter);

    public void damageToOpponents(Helicopter helicopter, float damageSum);

    public void carriedDamage(Helicopter helicopter, float damageSum);
}
