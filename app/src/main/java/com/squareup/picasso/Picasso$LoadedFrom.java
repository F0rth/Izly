package com.squareup.picasso;

public enum Picasso$LoadedFrom {
    MEMORY(-16711936),
    DISK(-16776961),
    NETWORK(-65536);
    
    final int debugColor;

    private Picasso$LoadedFrom(int i) {
        this.debugColor = i;
    }
}
