package com.team.sortapp;

import com.team.sortapp.ui.Menu;

import java.util.Scanner;

public class Aplication {

    private Aplication() {

    }

    public static void main(String[] args) {
        System.out.println("===SortApp===");
        System.out.println("Приложение для сортировки");
        System.out.println();

        Scanner scanner = new Scanner(System.in);
        Menu menu = new Menu(scanner);
        menu.run();

        scanner.close();
    }
}
