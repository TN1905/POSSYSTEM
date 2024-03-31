/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.raven.swing;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ADMIN
 */
public class loginInAccount {
    private static final String FILE_PATH = "C:\\loggedInAccounts.txt";

    public static List<String> loadLoggedInAccounts() {
        List<String> loggedInAccounts = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                loggedInAccounts.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return loggedInAccounts;
    }

    public static void saveLoggedInAccounts(List<String> loggedInAccounts) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (String account : loggedInAccounts) {
                writer.write(account);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
