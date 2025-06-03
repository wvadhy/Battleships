package org.example.util;

public enum Color
{
    /*
    Basic enum for storing colours for CLI version.
    */

    RED("\u001b[31m"), WHITE("\u001b[37m"), YELLOW("\u001b[33m");
    private final String hex;
    Color(String hex) { this.hex = hex; }
    public String getHex() { return hex; }
}