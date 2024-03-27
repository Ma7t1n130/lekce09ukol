package cz.martin.lekce09ukol.controller;

import cz.martin.lekce09ukol.ContributionException;
import cz.martin.lekce09ukol.model.Contribution;
import cz.martin.lekce09ukol.service.DataManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("v1")
public class ContributionController {

    @Autowired
    DataManager dataManager;
    @PostMapping("contribution")
    public String createContribution(@RequestBody Contribution contribution) throws ContributionException {
        return dataManager.createContribution(contribution);
    }

    @GetMapping("contribution")
    public List<Contribution> getContribution(
            @RequestParam(value = "withInvisible", required = false) boolean withInvisible) throws ContributionException {
        return dataManager.getContribution(withInvisible);
    }
}
