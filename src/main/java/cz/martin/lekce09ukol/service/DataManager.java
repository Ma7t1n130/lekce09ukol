package cz.martin.lekce09ukol.service;

import cz.martin.lekce09ukol.ContributionException;
import cz.martin.lekce09ukol.Settings;
import cz.martin.lekce09ukol.model.Contribution;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

@Service
public class DataManager{

    private final String fileName="contributions.txt";
    private List<Contribution> contributions=new ArrayList<>();

    public DataManager() {
    }

    public String createContribution(Contribution contribution) throws ContributionException {
        this.loadFromFile();
        for(Contribution c : this.contributions){
            if(c.getContributionId()==contribution.getContributionId()){
                throw new ResponseStatusException(HttpStatus.CONFLICT);
            }
        }
        this.addContribution(contribution);
        this.saveToFile();
        return "Contribution successfully created.";
    }

    public List<Contribution> getContribution(boolean withInvisible) throws ContributionException {
        this.loadFromFile();
        if (withInvisible) {
            return this.getContributions();
        } else {
            return this.getContributions()
                    .stream()
                    .filter((Contribution currentElement) -> {
                        return currentElement.isVisible() == true;
                    })
                    .toList();
        }
    }

    private void loadFromFile() throws ContributionException {
        this.contributions.clear();
        int lineCounter=0;
        try (Scanner scanner = new Scanner(new BufferedReader(new FileReader(fileName)))) {
            while(scanner.hasNextLine()){
                lineCounter++;
                String fileLine = scanner.nextLine();
                String[] parts = fileLine.split(Settings.SEPARATOR());
                if (parts.length!=4){
                    throw new ContributionException("Nesprávný formát řádku.");
                }
                String contribution=parts[0];
                int contributionId=Integer.parseInt(parts[1]);
                boolean isVisible=Boolean.parseBoolean(parts[2]);
                String user=parts[3];
                addContribution(new Contribution(contribution,contributionId,isVisible,user));
            }
        } catch (ContributionException e) {
            throw new ContributionException("Nesprávný formát řádku " + lineCounter + ". " + e.getLocalizedMessage());
        } catch (FileNotFoundException e) {
            System.out.println("Soubor " +fileName+ " zatím neexistuje.");
        } catch (NumberFormatException e) {
            throw new ContributionException("Chyba při čtení číselné hodnoty na řádku " + lineCounter + " " + e.getLocalizedMessage());
        }
    }

    private void addContribution(Contribution contribution){
        this.contributions.add(contribution);
    }
    private void saveToFile() throws ContributionException {
        try(PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(fileName)))){
            for(Contribution contribution : contributions){
                writer.println(contribution.getFileLine());
            }
        } catch (IOException e) {
            throw new ContributionException("Chyba při zápisu do souboru " + fileName + " " + e.getLocalizedMessage());
        }
    }

    public List<Contribution> getContributions() {
        return this.contributions;
    }
}

