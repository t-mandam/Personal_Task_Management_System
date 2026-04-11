package com.taskmanagement;

import com.taskmanagement.ui.Console;

/**
 * Runnable entry point for the Personal Task Management System.
 * All startup setup is coordinated here and delegated to Console.
 */
public class PersonalTaskManagementSystem {

    private static void printBanner() {
        System.out.println("============================================================");
        System.out.println("           PERSONAL TASK MANAGEMENT SYSTEM");
        System.out.println("============================================================");
    }

    public static void main(String[] args) {
        printBanner();
        Console console = new Console();
        console.initialize();
        console.start();
    }
}
