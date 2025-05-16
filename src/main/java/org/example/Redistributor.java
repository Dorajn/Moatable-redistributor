package org.example;

import java.util.*;

public class Redistributor {

    public static List<Transfer> redistributeMoney(Map<String, Integer> accounts, int threshold){
        if(!validData(accounts, threshold))
            return List.of();

        List<Transfer> transfers = new ArrayList<>();
        PriorityQueue<Map.Entry<String, Integer>> donors =
                new PriorityQueue<>(Comparator.comparingInt(o -> -(o.getValue() - threshold)));

        PriorityQueue<Map.Entry<String, Integer>> recipients =
                new PriorityQueue<>(Comparator.comparingInt(o -> -(threshold - o.getValue())));


        for(var entry : accounts.entrySet()){
            int amount = entry.getValue();
            if(amount > threshold){
                donors.offer(entry);
            }
            else if(amount < threshold){
                recipients.offer(entry);
            }
        }

        while(!donors.isEmpty() && !recipients.isEmpty()){
            var donor = donors.poll();
            var recipient = recipients.poll();

            int surplus = donor.getValue() - threshold;
            int deficiency = threshold - recipient.getValue();
            int transferAmount = Math.min(surplus, deficiency);

            if(transferAmount <= 0)
                continue;

            int recipientBalance = recipient.getValue() + transferAmount;
            int donorBalance = donor.getValue() - transferAmount;

            transfers.add(new Transfer(donor.getKey(), recipient.getKey(), transferAmount));
            accounts.put(donor.getKey(), donorBalance);
            accounts.put(recipient.getKey(), recipientBalance);

            if(recipientBalance < threshold)
                recipients.offer(Map.entry(recipient.getKey(), recipientBalance));

            if(donorBalance > threshold)
                donors.offer(Map.entry(donor.getKey(), donorBalance));

        }

        return transfers;
    }

    private static boolean validData(Map<String, Integer> accounts, int threshold){
        if (threshold < 0) {
            System.out.println("Threshold must be a positive integer.");
            return false;
        }

        boolean allPositiveValues = accounts.values().stream()
                .allMatch(value -> value >= 0);

        if(!allPositiveValues){
            System.out.println("Account balances must be positive or equal to zero.");
            return false;
        }

        return true;
    }

}
