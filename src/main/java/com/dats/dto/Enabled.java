package com.dats.dto;

public enum Enabled {
    True(true),
    False(false);

    private boolean enable;

    Enabled(Boolean e)
    {
        enable = e;
    }

    boolean getEnable()
    {
        return enable;
    }
}
