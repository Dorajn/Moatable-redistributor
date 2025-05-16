package org.example;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {

        try {
            var parsedData = DataReader.readThresholdAndAccounts("data.txt");
            var transfers =  Redistributor.redistributeMoney(parsedData.accounts(), parsedData.threshold());
            transfers.forEach(System.out::println);
        }
        catch (IOException e){
            System.out.println("Error while reading file: " + e.getMessage());
        }

    }
}

