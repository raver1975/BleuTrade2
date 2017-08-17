package com.alphatica.genotick.ui;

import com.alphatica.genotick.genotick.MainSettings;

@SuppressWarnings("unused")
class DefaultInputs extends BasicUserInput {


    @Override
    public MainSettings getSettings() {
        return MainSettings.getSettings();
    }
}
