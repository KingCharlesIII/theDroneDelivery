package com.example.ilp_submission_2.service;

import com.example.ilp_submission_2.dto.Drone;
import com.example.ilp_submission_2.dto.MedDispatchRec;
import org.springframework.stereotype.Service;

import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class DroneServices {
    private String ILP_ENDPOINT;
    private RestTemplate restTemplate = new RestTemplate();

    public DroneServices() {
        this.ILP_ENDPOINT = System.getenv().getOrDefault(
                "ILP_ENDPOINT",
                "https://ilp-rest-2025-bvh6e9hschfagrgy.ukwest-01.azurewebsites.net"
        );
    }

    private List<Drone> fetchDrones() {
        String url = ILP_ENDPOINT + "/drones";

        Drone[] droneArray = restTemplate.getForObject(url, Drone[].class);

        return List.of(droneArray);
    }

    public Drone getTheDroneWithId(String id){
        List<Drone> thedrones = fetchDrones();
        int size = thedrones.size();
        for(int i = 0; i < size;  i++ ){
            if(id.equals(thedrones.get(i).getId())){
                return thedrones.get(i);
            }
        }
        return null;
    }

    public List<String> getTheIdWithState(boolean state){
        List<String> list = new ArrayList<>();
        List<Drone> thedrones = fetchDrones();
        int size = thedrones.size();
        for(int i = 0; i < size; i++){
            if(state == thedrones.get(i).getCapability().getCooling()){
                list.add(thedrones.get(i).getId());
            }
        }
        return list;
    }

    public List<String> getTheIdWithHeating(boolean heat){
        List<String> list = new ArrayList<>();
        List<Drone> thedrones = fetchDrones();
        int size = thedrones.size();
        for(int i = 0; i < size; i++){
            if(heat == thedrones.get(i).getCapability().getHeating()){
                list.add(thedrones.get(i).getId());
            }
        }
        return list;
    }

    public List<String> getTheIdWithCapacity(double capacity){
        List<String> list = new ArrayList<>();
        List<Drone> thedrones = fetchDrones();
        int size = thedrones.size();
        for (int i = 0; i < size; i++) {
            if (capacity == thedrones.get(i).getCapability().getCapacity()) {
                list.add(thedrones.get(i).getId());
            }
        }
        return list;
    }

    public List<String> getTheIdWithMaxMoves(int maxMoves){
        List<String> list = new ArrayList<>();
        List<Drone> thedrones = fetchDrones();
        int size = thedrones.size();
        for(int i = 0; i < size; i++){
            if(maxMoves == thedrones.get(i).getCapability().getMaxMoves()){
                list.add(thedrones.get(i).getId());
            }
        }
        return list;
    }

    public List<String> getTheIdWithCostPerMove(double costPerMove){
        List<String> list = new ArrayList<>();
        List<Drone> thedrones = fetchDrones();
        int size = thedrones.size();
        for(int i = 0; i < size; i++){
            if(costPerMove == thedrones.get(i).getCapability().getCostPerMove()){
                list.add(thedrones.get(i).getId());
            }
        }
        return list;
    }

    public List<String> getTheIdWithCostInitial(double costInitial){
        List<String> list = new ArrayList<>();
        List<Drone> thedrones = fetchDrones();
        int size = thedrones.size();
        for(int i = 0; i < size; i++){
            if(costInitial == thedrones.get(i).getCapability().getCostInitial()){
                list.add(thedrones.get(i).getId());
            }
        }
        return list;
    }

    public List<String> getTheIdWithCostFinal(double costFinal){
        List<String> list = new ArrayList<>();
        List<Drone> thedrones = fetchDrones();
        int size = thedrones.size();
        for(int i = 0; i < size; i++){
            if(costFinal == thedrones.get(i).getCapability().getCostFinal()){
                list.add(thedrones.get(i).getId());
            }
        }
        return list;
    }

    public List<String> getListOfIdWithCapacityOperator(String operator,
                                                        double value){
        List<String> list = new ArrayList<>();
        List<Drone> thedrones = fetchDrones();
        int size = thedrones.size();
        switch(operator){
            case "<":
                for(int i = 0; i < size; i++){
                    if(thedrones.get(i).getCapability().getCapacity() < value){
                        list.add(thedrones.get(i).getId());
                    }
                }
                break;
            case ">":
                for(int i = 0; i < size; i++){
                    if(thedrones.get(i).getCapability().getCapacity() > value){
                        list.add(thedrones.get(i).getId());
                    }
                }
                break;
            case "=":
                for(int i = 0; i < size; i++){
                    if(thedrones.get(i).getCapability().getCapacity() == value){
                        list.add(thedrones.get(i).getId());
                    }
                }
                break;
            case "!=":
                for(int i = 0; i < size; i++){
                    if(thedrones.get(i).getCapability().getCapacity() != value){
                        list.add(thedrones.get(i).getId());
                    }
                }
                break;
        }
        return list;
    }

    public List<String> getListOfIdWithMaxMovesOperator(String operator,
                                                        int value){
        List<String> list = new ArrayList<>();
        List<Drone> thedrones = fetchDrones();
        int size = thedrones.size();
        switch(operator){
            case "<":
                for(int i = 0; i < size; i++){
                    if(thedrones.get(i).getCapability().getMaxMoves() < value){
                        list.add(thedrones.get(i).getId());
                    }
                }
                break;
            case ">":
                for(int i = 0; i < size; i++){
                    if(thedrones.get(i).getCapability().getMaxMoves() > value){
                        list.add(thedrones.get(i).getId());
                    }
                }
                break;
            case "=":
                for(int i = 0; i < size; i++){
                    if(thedrones.get(i).getCapability().getMaxMoves() == value){
                        list.add(thedrones.get(i).getId());
                    }
                }
                break;
            case "!=":
                for(int i = 0; i < size; i++){
                    if(thedrones.get(i).getCapability().getMaxMoves() != value){
                        list.add(thedrones.get(i).getId());
                    }
                }
                break;
        }
        return list;
    }

    public List<String> getListOfIdWithCostPerMoveOperator(String operator,
                                                           double value){
        List<String> list = new ArrayList<>();
        List<Drone> thedrones = fetchDrones();
        int size = thedrones.size();
        switch(operator){
            case "<":
                for(int i = 0; i < size; i++){
                    if(thedrones.get(i).getCapability().getCostPerMove() < value){
                        list.add(thedrones.get(i).getId());
                    }
                }
                break;
            case ">":
                for(int i = 0; i < size; i++){
                    if(thedrones.get(i).getCapability().getCostPerMove() > value){
                        list.add(thedrones.get(i).getId());
                    }
                }
                break;
            case "=":
                for(int i = 0; i < size; i++){
                    if(thedrones.get(i).getCapability().getCostPerMove() == value){
                        list.add(thedrones.get(i).getId());
                    }
                }
                break;
            case "!=":
                for(int i = 0; i < size; i++){
                    if(thedrones.get(i).getCapability().getCostPerMove() != value){
                        list.add(thedrones.get(i).getId());
                    }
                }
                break;
        }
        return list;
    }

    public List<String> getListOfIdWithCostInitialOperator(String operator,
                                                           double value){
        List<String> list = new ArrayList<>();
        List<Drone> thedrones = fetchDrones();
        int size = thedrones.size();
        switch(operator){
            case "<":
                for(int i = 0; i < size; i++){
                    if(thedrones.get(i).getCapability().getCostInitial() < value){
                        list.add(thedrones.get(i).getId());
                    }
                }
                break;
            case ">":
                for(int i = 0; i < size; i++){
                    if(thedrones.get(i).getCapability().getCostInitial() > value){
                        list.add(thedrones.get(i).getId());
                    }
                }
                break;
            case "=":
                for(int i = 0; i < size; i++){
                    if(thedrones.get(i).getCapability().getCostInitial() == value){
                        list.add(thedrones.get(i).getId());
                    }
                }
                break;
            case "!=":
                for(int i = 0; i < size; i++){
                    if(thedrones.get(i).getCapability().getCostInitial() != value){
                        list.add(thedrones.get(i).getId());
                    }
                }
                break;
        }
        return list;
    }

    public List<String> getListOfIdWithCostFinalOperator(String operator,
                                                         double value){
        List<String> list = new ArrayList<>();
        List<Drone> thedrones = fetchDrones();
        int size = thedrones.size();
        switch(operator){
            case "<":
                for(int i = 0; i < size; i++){
                    if(thedrones.get(i).getCapability().getCostFinal() < value){
                        list.add(thedrones.get(i).getId());
                    }
                }
                break;
            case ">":
                for(int i = 0; i < size; i++){
                    if(thedrones.get(i).getCapability().getCostFinal() > value){
                        list.add(thedrones.get(i).getId());
                    }
                }
                break;
            case "=":
                for(int i = 0; i < size; i++){
                    if(thedrones.get(i).getCapability().getCostFinal() == value){
                        list.add(thedrones.get(i).getId());
                    }
                }
                break;
            case "!=":
                for(int i = 0; i < size; i++){
                    if(thedrones.get(i).getCapability().getCostFinal() != value){
                        list.add(thedrones.get(i).getId());
                    }
                }
                break;
        }
        return list;
    }



    public List<String> getQueryId(List<MedDispatchRec>  medDispatchRec ){
        List<String> listHolder = new ArrayList<>();
        List<Drone> thedrones = fetchDrones();
        int size = medDispatchRec.size();

        for(int i = 0; i < size; i++){
            for(int j = 0; j < thedrones.size(); j ++){
                if(medDispatchRec.get(i).getRequirements().getCapacity() <= thedrones.get(j).getCapability().getCapacity()){
                    if (medDispatchRec.get(i).getRequirements().getCooling()
                                    == thedrones.get(j).getCapability().getCooling()
                                    && medDispatchRec.get(i).getRequirements().getHeating()
                                    == thedrones.get(j).getCapability().getHeating()
                                    && (medDispatchRec.get(i).getRequirements().getMaxCost() == null
                                    || (thedrones.get(j).getCapability().getCostInitial()
                                    + thedrones.get(j).getCapability().getCostFinal())
                                    <= medDispatchRec.get(i).getRequirements().getMaxCost())) {
                        listHolder.add(thedrones.get(j).getId());
                    }

                }

            }
        }

        List<String> finalList = new ArrayList<>();
        for (int j = 0; j < thedrones.size(); j++) {
            String droneId = thedrones.get(j).getId();
            int count = 0;

            for (int i = 0; i < listHolder.size(); i++) {
                if (listHolder.get(i).equals(droneId)) {
                    count++;
                }
            }
            if (count == size) {
                finalList.add(droneId);
            }
        }
        return finalList;
    }

}
