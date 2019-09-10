package com.eventstorming.domain.board;

enum StickyNoteType {
    COMMAND, DOMAIN_EVENT;

    public static StickyNoteType getValue(String name) {
        return valueOf(name);
    }
}
