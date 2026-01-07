package com.example.ilp_submission_2.service;

import com.example.ilp_submission_2.dto.*;
import com.example.ilp_submission_2.dto.MedDispatchRec;
import com.example.ilp_submission_2.dto.DronesAvailability;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;


import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

@Service
public class CalcDeliveryPathService {
    private String ILP_ENDPOINT;
    private RestTemplate restTemplate = new RestTemplate();

    public CalcDeliveryPathService() {
        this.ILP_ENDPOINT = System.getenv().getOrDefault(
                "ILP_ENDPOINT",
                "https://ilp-rest-2025-bvh6e9hschfagrgy.ukwest-01.azurewebsites.net"
        );
    }

    public List<RestrictedArea> fetchRestrictedArea() {
        String url = ILP_ENDPOINT + "/restricted-areas";

        RestrictedArea[] restrictedAreasArray = restTemplate.getForObject(url, RestrictedArea[].class);

        return List.of(restrictedAreasArray);
    }

    public List<TheServicePoint> fetchTheServicePoints() {
        String url = ILP_ENDPOINT + "/service-points";
        TheServicePoint[] servicePointArray = restTemplate.getForObject(url, TheServicePoint[].class);

        return List.of(servicePointArray);
    }

    public List<TheDroneForServicePoint> fetchTheDronesServicePoints() {
        String url = ILP_ENDPOINT + "/drones-for-service-points";
        TheDroneForServicePoint [] dronesAvailabilityArray = restTemplate.getForObject(url, TheDroneForServicePoint[] .class);
        return List.of(dronesAvailabilityArray);
    }

    public List<String> getDroneIdsFromAvailability(List<String> droneIds, List<MedDispatchRec> recList) {

        List<TheDroneForServicePoint> dronesAvailability = fetchTheDronesServicePoints();
        List<String> theResultIds = new ArrayList<>();

        int sizeOfDroneIds = droneIds.size();
        int sizeServicePoints = dronesAvailability.size();
        for (int i = 0; i < sizeOfDroneIds; i++) {
            String currentDroneId = droneIds.get(i);
            boolean availableForAll = true;
            int foundServicePointId = -1000;

            for (int r = 0; r < recList.size(); r++) {
                MedDispatchRec rec = recList.get(r);
                DayOfWeek neededDay = LocalDate.parse(rec.getDate()).getDayOfWeek();
                LocalTime neededTime = LocalTime.parse(rec.getTime());
                boolean availableThisRec = false;

                for (int j = 0; j < sizeServicePoints; j++) {
                    int theServicePointId = dronesAvailability.get(j).getservicePointId();
                    List<DronesAvailability> spDrones = dronesAvailability.get(j).getDrones();

                    for (int k = 0; k < spDrones.size(); k++) {
                        DronesAvailability da = spDrones.get(k);
                        if (da.getId().equals(currentDroneId)) {
                            List<Availability> timeList = da.getAvailability();

                            for (int m = 0; m < timeList.size(); m++) {
                                Availability time = timeList.get(m);
                                DayOfWeek droneDay = DayOfWeek.valueOf(time.getDayOfWeek());

                                if (droneDay.equals(neededDay)) {
                                    LocalTime from = LocalTime.of(
                                            time.getFrom().getHour(),
                                            time.getFrom().getMinute(),
                                            time.getFrom().getSecond(),
                                            time.getFrom().getNano()
                                    );

                                    LocalTime until = LocalTime.of(
                                            time.getUntil().getHour(),
                                            time.getUntil().getMinute(),
                                            time.getUntil().getSecond(),
                                            time.getUntil().getNano()
                                    );

                                    if (!neededTime.isBefore(from) && !neededTime.isAfter(until)) {
                                        availableThisRec = true;
                                        foundServicePointId = theServicePointId;
                                        break;
                                    }
                                }
                            }
                        }

                        if (availableThisRec){
                            break;
                        }
                    }

                    if (availableThisRec) {
                        break;
                    }
                }

                if (!availableThisRec) {
                    availableForAll = false;
                    break;
                }
            }

            if (availableForAll) {
                theResultIds.add(currentDroneId);
                theResultIds.add(String.valueOf(foundServicePointId));
            }
        }

        return theResultIds;
    }


    public List<Position> findPath(Position start, Position goal,
                                   List<RestrictedArea> noFlyZones,
                                   int maxMoves, Position before) {

        List<TheServicePoint> servicePoints = fetchTheServicePoints();
        List<Double> theMoveAngles = List.of(
                0.0, 22.5, 45.0, 67.5,
                90.0, 112.5, 135.0, 157.5,
                180.0, 202.5, 225.0, 247.5,
                270.0, 292.5, 315.0, 337.5
        );

        boolean startIsSP = isServicePoint(start, servicePoints);
        List<Position> path = new ArrayList<>();
        GeoService theFirstGeoService = new GeoService(start, goal);
        if ((startIsSP) || (!start.equals(before) && !theFirstGeoService.isItClose(start, goal))){
            path.add(start);
        }
        Position current = start;
        GeoService theSecondGeoService = new GeoService(current, goal);

        for (int moves = 0; moves < maxMoves; moves++) {
            if ((theSecondGeoService.isItClose(theSecondGeoService.getPosition1(), theSecondGeoService.getPosition2()))) {
                for(int i = 0; i<servicePoints.size(); i++) {
                    if(goal.getLng() == servicePoints.get(i).getLocation().getLng() &&
                            goal.getLat() == servicePoints.get(i).getLocation().getLat()) {
                        path.add(goal);
                        return path;
                    }

                }
                path.add(goal);
                path.add(goal);
                return path;
            }

            Position bestNext = null;
            double bestDistance = 900000000;

            for (int a = 0; a < theMoveAngles.size(); a++) {

                Angle angle = new Angle(theMoveAngles.get(a));
                NextPositionService nextPositionService = new NextPositionService(current, angle);
                Position next = nextPositionService.newPosition(nextPositionService.getStart(), nextPositionService.getAngle());


                if (isBlocked(next, noFlyZones)){
                    continue;
                }

                double nextLng = next.getLng();
                double currLng = current.getLng();

                for (int f = 0; f < noFlyZones.size(); f++) {

                    List<LngLatAlt> theRestrictedArea = noFlyZones.get(f).getVertices();
                    if (theRestrictedArea == null || theRestrictedArea.size() < 2){
                        continue;
                    }

                    double minLng = 900000000;
                    double maxLng = -900000000;

                    for (int o = 0; o < theRestrictedArea.size(); o++) {
                        minLng = Math.min(minLng, theRestrictedArea.get(o).getLng());
                        maxLng = Math.max(maxLng, theRestrictedArea.get(o).getLng());
                    }

                }

                Position positionNext = new Position(next.getLng(), next.getLat());
                Position positionGoal = new Position(goal.getLng(), goal.getLat());
                GeoService theThirdGeoService = new GeoService(positionNext, positionGoal);
                if (theThirdGeoService.euclidianDistance(theThirdGeoService.getPosition1(), theThirdGeoService.getPosition2()) < bestDistance) {
                        bestDistance = theThirdGeoService.euclidianDistance(theThirdGeoService.getPosition1(), theThirdGeoService.getPosition2());
                        bestNext = next;
                    }

            }

            if (bestNext == null) {
                return path;
            }
            GeoService finalGeoService = new GeoService(bestNext, goal);
            if (finalGeoService.isItClose(finalGeoService.getPosition1(), finalGeoService.getPosition2())) {
                for(int i = 0; i<servicePoints.size(); i++) {
                    if(goal.getLng() == servicePoints.get(i).getLocation().getLng() &&
                            goal.getLat() == servicePoints.get(i).getLocation().getLat()) {
                        path.add(goal);
                        return path;
                    }

                }
                path.add(goal);
                path.add(goal);
                return path;
            }
            path.add(bestNext);
            current = bestNext;
        }

        return path;
    }


    public List<Position> findPathOfReturn(Position start, Position goal, Position end,
                                   List<RestrictedArea> noFlyZones,
                                   int maxMoves, Position before) {
        List<TheServicePoint> servicePoints = fetchTheServicePoints();
        List<Position> path = new ArrayList<>();
        List<Position> LastDelivery = findPath(start, goal, noFlyZones, maxMoves, start);
        Position beforeReturn = goal;
        List<Position> backToSP = findPath(goal, end, noFlyZones, maxMoves, beforeReturn);
        for(int a = 0; a<LastDelivery.size(); a++){
            path.add(LastDelivery.get(a));
        }

        for(int a = 0; a<backToSP.size(); a++){
            path.add(backToSP.get(a));
        }

        return path;
    }

    private Region convertToRegion(RestrictedArea area) {

        List<Position> vertices = new ArrayList<>();

        for (int i = 0; i < area.getVertices().size(); i++){
            vertices.add(new Position(area.getVertices().get(i).getLng(), area.getVertices().get(i).getLat()));
        }

        Position first = vertices.get(0);
        Position last = vertices.get(vertices.size() - 1);

        if (first.getLng() != last.getLng() || first.getLat() != last.getLat()) {
            vertices.add(new Position(first.getLng(), first.getLat()));
        }

        return new Region(vertices, area.getName());
    }


    private boolean isBlocked(Position pos, List<RestrictedArea> noFlyZones) {

        for (int i = 0; i < noFlyZones.size(); i++) {

            Region region = convertToRegion(noFlyZones.get(i));

            int boundaryResult = region.boundary(region.getVertices(), pos);

            if (boundaryResult == 1) {
                return true;
            }

            if (boundaryResult % 2 == 1) {
                return true;
            }
        }

        return false;
    }

    private boolean isServicePoint(Position p, List<TheServicePoint> servicePoints) {
        for (int s = 0; s < servicePoints.size(); s++) {
            TheServicePoint sp = servicePoints.get(s);
            if(p.getLng() == sp.getLocation().getLng() && p.getLat() == sp.getLocation().getLat()) {
                return true;
            }
        }
        return false;
    }

}
