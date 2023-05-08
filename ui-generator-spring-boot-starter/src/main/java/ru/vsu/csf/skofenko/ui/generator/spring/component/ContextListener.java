package ru.vsu.csf.skofenko.ui.generator.spring.component;

import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class ContextListener {

    @EventListener({ContextRefreshedEvent.class})
    public void onContextRefreshedEvent() {
        System.out.println("a context refreshed event happened");
    }
}
