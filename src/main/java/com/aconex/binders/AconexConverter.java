package com.aconex.binders;

import java.io.FileNotFoundException;

/**
 * Generic converter interface
 */
public interface AconexConverter {
    public boolean doXML() throws FileNotFoundException;        /** Should implement text to xml converter */
}
