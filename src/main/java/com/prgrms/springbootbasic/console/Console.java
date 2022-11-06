package com.prgrms.springbootbasic.console;

import com.prgrms.springbootbasic.handler.menu.dto.MenuInputResult;
import org.springframework.stereotype.Component;

@Component
public class Console {

    private final Reader reader;
    private final Printer printer;

    public Console(Reader reader, Printer printer) {
        this.reader = reader;
        this.printer = printer;
    }

    public MenuInputResult printAndGetCommand(String message) {
        printer.printMessage(message);
        return new MenuInputResult(reader.read());
    }

    public void printMessage(String message){
        printer.printMessage(message);
    }
}
