package com.lugar.steelbird.controllers;

import com.lugar.steelbird.mathengine.Helicopter;

public class StatisticController implements GameController {

    @Override
    public void playerKillAI(Helicopter helicopter) {
        helicopter.addFrag();
    }

    @Override
    public void damageToOpponents(Helicopter helicopter, float damage) {
        helicopter.addDamage(damage);
    }

    @Override
    public void carriedDamage(Helicopter helicopter, float damage) {
        helicopter.carriedDamage(damage);
    }
}
