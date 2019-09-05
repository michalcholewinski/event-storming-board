package com.eventstorming.stickynote;

public enum StickyNoteType {
    COMMAND, DOMAIN_EVENT  //TODO(mcholewi) add more types
    ;

    public static StickyNoteType getValue(String name) {
        return valueOf(name);
    }
}
