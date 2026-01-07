package com.example.ilp_submission_2.controller;

import com.example.ilp_submission_2.dto.*;
import com.example.ilp_submission_2.service.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@RestController
@RequestMapping("/api/v1")
public class TheControllers {

    private DroneServices thedroneService;
    private CalcDeliveryPathService calcDeliveryPathService;

    public TheControllers(DroneServices thedroneService, CalcDeliveryPathService calcDeliveryPathService) {
        this.thedroneService = thedroneService;
        this.calcDeliveryPathService = calcDeliveryPathService;
    }

    @GetMapping("/uid")
    public String getId() {
        return "s2569302";
    }

    @PostMapping("/distanceTo")
    public ResponseEntity<?> distanceTo(@RequestBody DistanceRequest pos) {
        try {
            if (pos == null || pos.getPosition1() == null || pos.getPosition2() == null) {
                return ResponseEntity.status(400).body("Missing/null position data");
            }

            Position position1 = pos.getPosition1();
            Position position2 = pos.getPosition2();

            if (invalidLngLat(position1) || invalidLngLat(position2)) {
                return ResponseEntity.status(400).body("Invalid position data");
            }

            GeoService geoService = new GeoService(position1, position2);
            return ResponseEntity.ok(
                    geoService.euclidianDistance(
                            geoService.getPosition1(),
                            geoService.getPosition2()
                    )
            );

        } catch (Exception e) {
            return ResponseEntity.status(400).body("Invalid request format");
        }
    }
    private boolean invalidLngLat(Position p) {
        return p.getLat() < -90 || p.getLat() > 90 ||
                p.getLng() < -180 || p.getLng() > 180;
    }

    @PostMapping("/isCloseTo")
    public ResponseEntity<?> isCloseTo(@RequestBody DistanceRequest pos) {
        try {
            if (pos == null || pos.getPosition1() == null || pos.getPosition2() == null) {
                return ResponseEntity.status(400).body("Missing/null position data");

            }
            Position position1 = pos.getPosition1();
            Position position2 = pos.getPosition2();

            if (invalidLngLat(position1) || invalidLngLat(position2)) {
                return ResponseEntity.status(400).body("Invalid position data");
            }

            GeoService geoService = new GeoService(position1, position2);
            return ResponseEntity.ok(geoService.isItClose(geoService.getPosition1(), geoService.getPosition2()));
        } catch (Exception e) {
            return ResponseEntity.status(400).body("Invalid request format");
        }
    }

    @PostMapping("/nextPosition")
    public ResponseEntity<?> nextPosition(@RequestBody NextPosition pos) {
        try {
            if (pos == null || pos.getPosition() == null || pos.getAngle() == null) {
                return ResponseEntity.status(400).body("Missing/null position/angle data");
            }

            Position start = pos.getPosition();
            Angle angle = pos.getAngle();

            if (invalidLngLat(start)) {
                return ResponseEntity.status(400).body("Invalid position data");
            }

            if (angle.value() < 0 || angle.value() >= 360) {
                return ResponseEntity.status(400).body("Invalid angle");
            }

            NextPositionService nextPositionService = new NextPositionService(start, angle);
            return ResponseEntity.ok(nextPositionService.newPosition(nextPositionService.getStart(), nextPositionService.getAngle()));
        } catch (Exception e) {
            return ResponseEntity.status(400).body("Invalid request format");
        }
    }

    @PostMapping("/isInRegion")
    public ResponseEntity<?> isInRegion(@RequestBody IsInRegion pos) {
        try {
            if (pos == null || pos.getPosition() == null || pos.getRegion() == null) {
                return ResponseEntity.status(400).body("Missing or null position/region data");
            }

            Position position1 = pos.getPosition();
            Region region = pos.getRegion();

            if (region.getVertices() == null || region.getVertices().size() < 4) {
                return ResponseEntity.status(400).body("Invalid, the region is not closed/a vertices is null");
            }


            if (!region.firstEqualLast()) {
                return ResponseEntity.status(400).body("Invalid, the region is not closed");
            }

            RegionService regionService = new RegionService(position1, region);
            return ResponseEntity.ok(regionService.isItIn(regionService.getPosition1(), regionService.getRegion()));
        } catch (Exception e) {
            return ResponseEntity.status(400).body("Invalid request format");
        }
    }

    @GetMapping("/droneDetails/{the_id}")
    public ResponseEntity<?> droneDetails(@PathVariable String the_id) {
        Drone foundTheId = thedroneService.getTheDroneWithId(the_id);

        if (foundTheId == null) {
            return ResponseEntity.status(404).body("Drone not found");
        }

        return ResponseEntity.ok(foundTheId);
    }

    @GetMapping("/dronesWithCooling/{state}")
    public ResponseEntity<?> dronesWithCooling(@PathVariable boolean state) {
        List<String> foundTheState = thedroneService.getTheIdWithState(state);
        return ResponseEntity.ok(foundTheState);
    }

    @GetMapping("/queryAsPath/{attribute_name}/{attribute_value}")
    public ResponseEntity<?> queryAsPath(@PathVariable String attribute_name, @PathVariable String attribute_value) {
        switch (attribute_name) {
            case "cooling":
                if (!attribute_value.equalsIgnoreCase("true") && !attribute_value.equalsIgnoreCase("false")) {
                    return ResponseEntity.ok(Collections.emptyList());
                }
                boolean theValueForCooling = Boolean.parseBoolean(attribute_value);
                return ResponseEntity.ok(thedroneService.getTheIdWithState(theValueForCooling));

            case "heating":
                if (!attribute_value.equalsIgnoreCase("true") && !attribute_value.equalsIgnoreCase("false")) {
                    return ResponseEntity.ok(Collections.emptyList());
                }
                boolean theValueForHeating = Boolean.parseBoolean(attribute_value);
                return ResponseEntity.ok(thedroneService.getTheIdWithHeating(theValueForHeating));

            case "capacity":
                double theValueForCapacity;
                try {
                    theValueForCapacity = Double.parseDouble(attribute_value);
                } catch (NumberFormatException e) {
                    return ResponseEntity.ok(Collections.emptyList());
                }
                return ResponseEntity.ok(thedroneService.getTheIdWithCapacity(theValueForCapacity));

            case "maxMoves":
                int theValueForMaxMoves;
                try {
                    theValueForMaxMoves = Integer.parseInt(attribute_value);
                } catch (NumberFormatException e) {
                    return ResponseEntity.ok(Collections.emptyList());
                }
                return ResponseEntity.ok(thedroneService.getTheIdWithMaxMoves(theValueForMaxMoves));

            case "costPerMove":
                double theValueForCostPerMove;
                try {
                    theValueForCostPerMove = Double.parseDouble(attribute_value);
                } catch (NumberFormatException e) {
                    return ResponseEntity.ok(Collections.emptyList());
                }
                return ResponseEntity.ok(thedroneService.getTheIdWithCostPerMove(theValueForCostPerMove));

            case "costInitial":
                double theValueForCostInitial;
                try {
                    theValueForCostInitial = Double.parseDouble(attribute_value);
                } catch (NumberFormatException e) {
                    return ResponseEntity.ok(Collections.emptyList());
                }
                return ResponseEntity.ok(thedroneService.getTheIdWithCostInitial(theValueForCostInitial));

            case "costFinal":
                double theValueForCostFinal;
                try {
                    theValueForCostFinal = Double.parseDouble(attribute_value);
                } catch (NumberFormatException e) {
                    return ResponseEntity.ok(Collections.emptyList());
                }
                return ResponseEntity.ok(thedroneService.getTheIdWithCostFinal(theValueForCostFinal));

            default:
                return ResponseEntity.ok(Collections.emptyList());
        }
    }


    @PostMapping("/query")
    public ResponseEntity<?> query(@RequestBody List<Query> queries) {
        List<List<String>> listOfId = new ArrayList<>();
        int size = queries.size();
        for (int i = 0; i < size; i++) {
            if ("cooling".equalsIgnoreCase(queries.get(i).getAttribute())) {
                boolean theValue = Boolean.parseBoolean(queries.get(i).getValue());
                listOfId.add(thedroneService.getTheIdWithState(theValue));
            }
            if ("heating".equalsIgnoreCase(queries.get(i).getAttribute())) {
                boolean theValue = Boolean.parseBoolean(queries.get(i).getValue());
                listOfId.add(thedroneService.getTheIdWithHeating(theValue));
            }
            if ("capacity".equalsIgnoreCase(queries.get(i).getAttribute())) {
                int theValue = Integer.parseInt(queries.get(i).getValue());
                listOfId.add(thedroneService.getListOfIdWithCapacityOperator(queries.get(i).getOperator(), theValue));
            }
            if ("maxMoves".equalsIgnoreCase(queries.get(i).getAttribute())) {
                int theValue = Integer.parseInt(queries.get(i).getValue());
                listOfId.add(thedroneService.getListOfIdWithMaxMovesOperator(queries.get(i).getOperator(), theValue));
            }
            if ("costPerMove".equalsIgnoreCase(queries.get(i).getAttribute())) {
                double theValue = Double.parseDouble(queries.get(i).getValue());
                listOfId.add(thedroneService.getListOfIdWithCostPerMoveOperator(queries.get(i).getOperator(), theValue));
            }
            if ("costInitial".equalsIgnoreCase(queries.get(i).getAttribute())) {
                double theValue = Double.parseDouble(queries.get(i).getValue());
                listOfId.add(thedroneService.getListOfIdWithCostInitialOperator(queries.get(i).getOperator(), theValue));
            }
            if ("costFinal".equalsIgnoreCase(queries.get(i).getAttribute())) {
                double theValue = Double.parseDouble(queries.get(i).getValue());
                listOfId.add(thedroneService.getListOfIdWithCostFinalOperator(queries.get(i).getOperator(), theValue));
            }
        }
        HashSet<String> matchingIds = new HashSet<>(listOfId.get(0));
        for (int i = 1; i < listOfId.size(); i++) {
            matchingIds.retainAll(listOfId.get(i));
        }

        ArrayList<String> finalIds = new ArrayList<>(matchingIds);
        return ResponseEntity.ok(finalIds);

    }

    @PostMapping("/queryAvailableDrones")
    public ResponseEntity<?> queryAvailableDrones(@RequestBody List<MedDispatchRec> medDispatchRec) {
        List<String> theIdList = thedroneService.getQueryId(medDispatchRec);
        System.out.println(theIdList+"\n");
        List<String> theCorrectIdList = calcDeliveryPathService.getDroneIdsFromAvailability(theIdList, medDispatchRec);
        System.out.println(theCorrectIdList+"\n");
        List<String> finalIdList = new ArrayList<>();

        for(int i = 0; i<theCorrectIdList.size();i += 2){
            finalIdList.add(theCorrectIdList.get(i));
        }
        return ResponseEntity.ok(finalIdList);
    }

    @PostMapping("/calcDeliveryPath")
    public ResponseEntity<?> calcDeliveryPath(@RequestBody List<MedDispatchRec> medDispatchRec) {
        List<String> theIdList = thedroneService.getQueryId(medDispatchRec);
        List<String> theCorrectIdList = calcDeliveryPathService.getDroneIdsFromAvailability(theIdList, medDispatchRec);
        List<TheServicePoint> servicePoints = calcDeliveryPathService.fetchTheServicePoints();
        List<RestrictedArea> restrictedArea = calcDeliveryPathService.fetchRestrictedArea();
        List<dronePaths> allDronePaths = new ArrayList<>();
        double totalCost = 0;
        int totalMoves = 0;
        int theFinalMoves = 0;
        int totalDeliveries = medDispatchRec.size();
        int deliveryIndex = 0;

        for (int i = 0; i < theCorrectIdList.size(); i += 2) {
            List<deliveries> deliveryList = new ArrayList<>();
            if (deliveryIndex >= totalDeliveries) {
                break;
            }

            String droneId = theCorrectIdList.get(i);
            int servicePointId = Integer.parseInt(theCorrectIdList.get(i + 1));

            Position startPos = null;
            for (int s = 0; s < servicePoints.size(); s++) {
                TheServicePoint sp = servicePoints.get(s);
                if (sp.getservicePointId() == servicePointId) {
                    startPos = new Position(sp.getLocation().getLng(), sp.getLocation().getLat());
                    break;
                }
            }

            if (startPos == null) {
                continue;
            }

            Drone drone = thedroneService.getTheDroneWithId(droneId);
            int maxMoves = drone.getCapability().getMaxMoves();
            double droneCap = drone.getCapability().getCapacity();

            double droneInitialCost = drone.getCapability().getCostInitial();
            double droneFinalCost = drone.getCapability().getCostFinal();
            double droneCostPerMove = drone.getCapability().getCostPerMove();

            double usedCapacity = 0;
            Position currentPos = startPos;
            int movesSoFar = 0;
            int movesForThisDrone = 0;

            while (deliveryIndex < totalDeliveries) {
                MedDispatchRec rec = medDispatchRec.get(deliveryIndex);
                double neededCap = rec.getRequirements().getCapacity();

                if (usedCapacity + neededCap > droneCap) {
                    break;
                }
                usedCapacity = usedCapacity + neededCap;

                Position goalPos = new Position(rec.getDelivery().getLng(), rec.getDelivery().getLat());

                List<Position> pathToCurrent = calcDeliveryPathService.findPath(
                        currentPos, goalPos, restrictedArea, maxMoves, currentPos
                );

                int movesToCurrent = pathToCurrent.size() - 1;
                if (movesSoFar + movesToCurrent > maxMoves) {
                    break;
                }

                int nextIndex = deliveryIndex + 1;
                int nextNextIndex = deliveryIndex + 2;

                List<flightPath> currentFlightPath = new ArrayList<>();
                for (int p = 0; p < pathToCurrent.size(); p++) {
                    currentFlightPath.add(new flightPath(
                            pathToCurrent.get(p).getLng(),
                            pathToCurrent.get(p).getLat()
                    ));
                }

                movesSoFar = movesSoFar + movesToCurrent;
                movesForThisDrone = movesForThisDrone + movesToCurrent;
                theFinalMoves = theFinalMoves + movesToCurrent;
                totalMoves = totalMoves + movesToCurrent;
                currentPos = goalPos;

                if (nextNextIndex < totalDeliveries) {
                    MedDispatchRec nextNextRec = medDispatchRec.get(nextNextIndex);
                    int nextNextId = nextNextRec.getId();
                    Position nextNextGoal = new Position(
                            nextNextRec.getDelivery().getLng(),
                            nextNextRec.getDelivery().getLat()
                    );
                    List<Position> pathToNextNext = calcDeliveryPathService.findPath(
                            goalPos, nextNextGoal, restrictedArea, maxMoves, goalPos
                    );
                    List<Position> pathBackToHome = calcDeliveryPathService.findPath(
                            nextNextGoal, startPos, restrictedArea, maxMoves, nextNextGoal
                    );
                    int movesToNextNext = pathToNextNext.size() - 1;
                    int movesBackFromNextNext = pathBackToHome.size() - 1;
                    if (movesSoFar + movesToNextNext + movesBackFromNextNext > maxMoves) {
                        deliveries deliveryFirst = new deliveries(rec.getId(), currentFlightPath);
                        deliveryList.add(deliveryFirst);
                        List<Position> fullReturnPath = calcDeliveryPathService.findPathOfReturn(
                                currentPos, goalPos, startPos, restrictedArea, maxMoves, currentPos
                        );
                        List<flightPath> returnFp = new ArrayList<>();
                        for (int r = 0; r < fullReturnPath.size(); r++) {
                            returnFp.add(new flightPath(
                                    fullReturnPath.get(r).getLng(),
                                    fullReturnPath.get(r).getLat()
                            ));
                        }
                        deliveries deliverySecond = new deliveries(nextNextId, returnFp);
                        deliveryList.add(deliverySecond);
                        deliveryIndex = nextNextIndex;
                        break;
                    }
                }
                deliveries normalDelivery = new deliveries(rec.getId(), currentFlightPath);
                deliveryList.add(normalDelivery);
                deliveryIndex = deliveryIndex + 1;
            }

            if (deliveryList.size() > 0) {
                deliveries lastDelivery = deliveryList.get(deliveryList.size() - 1);
                int lastDeliveryId = lastDelivery.getDeliveryId();

                List<Position> returnPathPos = calcDeliveryPathService.findPath(
                        currentPos, startPos, restrictedArea, maxMoves, currentPos
                );

                List<flightPath> returnBack = new ArrayList<>();
                for (int r = 0; r < returnPathPos.size(); r++) {
                    returnBack.add(new flightPath(
                            returnPathPos.get(r).getLng(),
                            returnPathPos.get(r).getLat()
                    ));
                }
                lastDelivery.getFlightPath().addAll(returnBack);
            }
            double flightCost = droneInitialCost + droneFinalCost + (movesForThisDrone * droneCostPerMove);
            totalCost = totalCost + flightCost;
            dronePaths dp = new dronePaths(droneId, deliveryList);
            allDronePaths.add(dp);
        }
        calcDeliveryPath theFullCalcPath = new calcDeliveryPath(totalCost, theFinalMoves, allDronePaths);
        return ResponseEntity.ok(theFullCalcPath);
    }

    @PostMapping("/calcDeliveryPathAsGeoJson")
    public ResponseEntity<?> calcDeliveryPathAsGeoJson(@RequestBody List<MedDispatchRec> medDispatchRec) {
        List<String> theIdList = thedroneService.getQueryId(medDispatchRec);
        List<String> theCorrectIdList = calcDeliveryPathService.getDroneIdsFromAvailability(theIdList,medDispatchRec);
        List<TheServicePoint> servicePoints = calcDeliveryPathService.fetchTheServicePoints();
        List<RestrictedArea> restrictedArea = calcDeliveryPathService.fetchRestrictedArea();
        List<List<Double>> emptyCoordinates = new ArrayList<>();
        GeoJsonOutput geoJsonOutput = new GeoJsonOutput("LineString", emptyCoordinates);

        System.out.println(theCorrectIdList);
        for (int i = 0; i < theCorrectIdList.size(); i += 2){
            String droneId = theCorrectIdList.get(i);
            int servicePointId = Integer.parseInt(theCorrectIdList.get(i + 1));

            Drone drone = thedroneService.getTheDroneWithId(droneId);
            int maxMoves = drone.getCapability().getMaxMoves();
            double capacity = drone.getCapability().getCapacity();

            Position startPos = null;
            for (int s = 0; s < servicePoints.size(); s++) {
                TheServicePoint sp = servicePoints.get(s);
                if (sp.getservicePointId() == servicePointId) {
                    startPos = new Position(sp.getLocation().getLng(), sp.getLocation().getLat());
                    break;
                }
            }
            double usedCapacity = 0;
            for (int j = 0; j < medDispatchRec.size(); j++) {
                usedCapacity = usedCapacity + medDispatchRec.get(j).getRequirements().getCapacity();
            }
            if (usedCapacity > capacity) {
                continue;
            }

            boolean canDoAll = true;
            Position current = startPos;
            int totalMoves = 0;

            for(int j = 0; j < medDispatchRec.size(); j++){

                Position goal = new Position(
                        medDispatchRec.get(j).getDelivery().getLng(),
                        medDispatchRec.get(j).getDelivery().getLat()
                );

                List<Position> path = calcDeliveryPathService.findPath(
                        current, goal, restrictedArea, maxMoves, current
                );

                if (path == null || path.size() == 0) {
                    canDoAll = false;
                    break;
                }

                int moves = path.size() - 1;
                totalMoves =  totalMoves + moves;

                if (totalMoves > maxMoves) {
                    canDoAll = false;
                    break;
                }

                current = goal;
            }

            if (canDoAll) {
                List<Position> backPath = calcDeliveryPathService.findPath(
                        current, startPos, restrictedArea, maxMoves, current
                );

                if (backPath == null || backPath.size() == 0) {
                    canDoAll = false;
                } else {
                    int movesBack = backPath.size() - 1;
                    if (totalMoves + movesBack > maxMoves) {
                        canDoAll = false;
                    }
                }
            }

            if (!canDoAll) {
                continue;
            }

            List<Position> fullPath = new ArrayList<>();
            Position prev = startPos;
            for (int j = 0; j < medDispatchRec.size(); j++) {

                Position next = new Position(
                        medDispatchRec.get(j).getDelivery().getLng(),
                        medDispatchRec.get(j).getDelivery().getLat()
                );

                List<Position> segment = calcDeliveryPathService.findPath(
                        prev, next, restrictedArea, maxMoves, prev
                );

                fullPath.addAll(segment);
                prev = next;
            }

            List<Position> backHome = calcDeliveryPathService.findPath(
                    prev, startPos, restrictedArea, maxMoves, prev
            );
            fullPath.addAll(backHome);

            List<List<Double>> theCoordinates = new ArrayList<>();
            for(int h = 0; h < fullPath.size(); h++){
                theCoordinates.add(
                        Arrays.asList(fullPath.get(h).getLng(), fullPath.get(h).getLat())
                );
            }
            GeoJsonOutput geoJson = new GeoJsonOutput("LineString", theCoordinates);
            return ResponseEntity.ok(geoJson);
        }
        return ResponseEntity.ok(geoJsonOutput);
    }
}