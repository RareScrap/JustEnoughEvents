package ru.rarescrap.gloomyfolken.hooklib.example;

import ru.rarescrap.gloomyfolken.hooklib.minecraft.HookLoader;
import ru.rarescrap.gloomyfolken.hooklib.minecraft.PrimaryClassTransformer;

public class ExampleHookLoader extends HookLoader {

    // включает саму HookLib'у. Делать это можно только в одном из HookLoader'ов.
    // При желании, можно включить ru.rarescrap.gloomyfolken.hooklib.minecraft.HookLibPlugin и не указывать здесь это вовсе.
    @Override
    public String[] getASMTransformerClass() {
        return new String[]{PrimaryClassTransformer.class.getName()};
    }

    @Override
    public void registerHooks() {
        //регистрируем класс, где есть методы с аннотацией @Hook
        registerHookContainer("ru.rarescrap.gloomyfolken.hooklib.example.AnnotationHooks");
    }
}
