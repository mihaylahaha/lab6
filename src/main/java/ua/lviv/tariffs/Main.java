package ua.lviv.tariffs;

import ua.lviv.tariffs.menu.TariffMenu;
import ua.lviv.tariffs.repository.TariffRepository;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        TariffRepository repository = new TariffRepository();
        TariffMenu menu = new TariffMenu(repository, new Scanner(System.in));
        menu.run();
    }
}
