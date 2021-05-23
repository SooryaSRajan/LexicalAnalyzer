package com.ssr_projects.lexicalanalyzer;

class FlagClass {

    private static boolean isFlashEnabled = false;

    public static boolean isFlashEnabled() {
        return isFlashEnabled;
    }

    public static void setFlashEnabled(boolean flashEnabled) {
        isFlashEnabled = flashEnabled;
    }
}
