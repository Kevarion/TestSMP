package com.axurionstudios.testSMP.manager;

import com.axurionstudios.testSMP.TestSMP;
import lombok.Setter;

public class CooldownManager {

    private final TestSMP instance;
    @Setter private GemManager gemManager;

    public CooldownManager(TestSMP instance){
        this.instance = instance;
        this.gemManager = TestSMP.getGemManager();
    }


}
