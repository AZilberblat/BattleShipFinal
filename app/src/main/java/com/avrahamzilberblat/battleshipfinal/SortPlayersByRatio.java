package com.avrahamzilberblat.battleshipfinal;

import java.util.Comparator;

class SortPlayersByRatio implements Comparator<PlayerDetails>
{
    /**
     *  Used for sorting in ascending order of
     * Ratio score
     * @param a
     * @param b
     * @return
     */
    public int compare(PlayerDetails a, PlayerDetails b)
    {
        if (a.getRatio() < b.getRatio()) return -1;
        if (a.getRatio() > b.getRatio()) return 1;
        return 0;
    }
}
